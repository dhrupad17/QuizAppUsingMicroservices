package com.dhruv.quiz_service.service;

import com.dhruv.quiz_service.dao.QuizDao;
import com.dhruv.quiz_service.feign.QuizInterface;
import com.dhruv.quiz_service.model.QuestionWrapper;
import com.dhruv.quiz_service.model.Quiz;
import com.dhruv.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category,int numQ, String title){
        List<Integer> questions=quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Quiz has been created Successfully", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id){
        Quiz quiz=quizDao.findById(id).get();
        List<Integer> questionIds=quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions=quizInterface.getQuesionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses){
        ResponseEntity<Integer> score=quizInterface.getScore(responses);
        return score;
    }



}


