package com.hee.controller;

import com.hee.domain.Question;
import com.hee.domain.Result;
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
        if (!HttpSessionUtils.isLoginUser(session))
            return "/user/login";
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session))
            return "/user/login";
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

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUSerFormSession(session);
        if (!question.isSameWriter(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }

    private boolean hasPermission(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUser(session)) {
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
        Question question = questionRepository.findById(id).orElse(null);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElse(null);
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
