package com.hee.web;

import com.hee.domain.User;
import com.hee.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users") // 중복 url 제거
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            System.out.println("Login Failure");
            return "redirect:/users/loginForm";
        }
        if (!user.matchPassword(password)) {
            System.out.println("Login Failure");
            return "redirect:/users/loginForm";
        }
        System.out.println("Login Success");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

        return "redirect:/";
    }

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @PostMapping("")
    public String create(User user) {
        System.out.println("user: " + user);
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "/user/list";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (HttpSessionUtils.isLoginUSer(session)) { // 로그인되어 있지 않음
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUSerFormSession(session);

        /* [방법 1] */
        if (!sessionedUser.matchId(id)) {
            throw new IllegalStateException("You can't update the another user");
        }
        User user = userRepository.findById(id).orElse(null);

        /* [방법 2]: 간단, 로그인되어 세션에 저장된 사용자의 Id를 기반으로 DB에서 조회 */
//        User user = userRepository.findById(sessionedUser.getId()).orElse(null);

        model.addAttribute("user", user);
//        model.addAttribute("user", userRepository.findOne(id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        if (HttpSessionUtils.isLoginUSer(session)) { // 로그인되어 있지 않음
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUSerFormSession(session);

        /* [방법 1] */
        if (!sessionedUser.matchId(id)) {
            throw new IllegalStateException("You can't update the another user");
        }
        User user = userRepository.findById(id).orElse(null);

        user.update(updatedUser);
        userRepository.save(user);

        return "redirect:/users";
    }
}
