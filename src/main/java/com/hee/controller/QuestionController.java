package com.hee.controller;

import com.hee.domain.Question;
import com.hee.domain.User;
import com.hee.repository.QuestionRepository;
import com.hee.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUSer(session))
            return "/users/loginForm";
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUSer(session))
            return "/users/loginForm";
        User sessionUser = HttpSessionUtils.getUSerFormSession(session);

        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).orElse(null));

        return "/qna/show";
    }

    private boolean hasPermission(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUSer(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUSerFormSession(session);
        if (!question.isSameWriter(loginUser)) {
            throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return true;
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            Question question = questionRepository.findById(id).orElse(null);
            hasPermission(session, question);

            model.addAttribute("question", question);
            return "/qna/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        try {
            Question question = questionRepository.findById(id).orElse(null);
            hasPermission(session, question);

            question.update(title, contents);
            questionRepository.save(question);
            return String.format("redirect:/questions/%d", id);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session) {
        try {
            Question question = questionRepository.findById(id).orElse(null);
            hasPermission(session, question);

            questionRepository.deleteById(id);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }
}
