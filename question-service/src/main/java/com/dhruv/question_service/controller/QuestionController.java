package com.dhruv.question_service.controller;

import com.dhruv.question_service.model.Question;
import com.dhruv.question_service.model.QuestionWrapper;
import com.dhruv.question_service.model.Response;
import com.dhruv.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id, @RequestBody Question updatedQuestion) {
        return questionService.updateQuestion(id, updatedQuestion);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }

    @PostMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions){
        return questionService.getQuestionForQuiz(categoryName,numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
