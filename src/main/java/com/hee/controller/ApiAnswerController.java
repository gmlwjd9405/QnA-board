package com.hee.controller;

import com.hee.domain.Answer;
import com.hee.domain.Question;
import com.hee.domain.Result;
import com.hee.domain.User;
import com.hee.repository.AnswerRepository;
import com.hee.repository.QuestionRepository;
import com.hee.web.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUSer(session)) {
            return null;
        }
        User loginUser = HttpSessionUtils.getUSerFormSession(session);
        Question question = questionRepository.findById(questionId).orElse(null);
        Answer answer = new Answer(loginUser, question, contents);
        return answerRepository.save(answer);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if(!HttpSessionUtils.isLoginUSer(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        Answer answer = answerRepository.findById(id).orElse(null);
        User loginUser = HttpSessionUtils.getUSerFormSession(session);
        if(!answer.isSameWriter(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }
        answerRepository.deleteById(id);
        return Result.ok();
    }
}
