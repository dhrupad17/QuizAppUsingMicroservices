package com.dhruv.question_service.service;

import com.dhruv.question_service.dao.QuestionDao;
import com.dhruv.question_service.model.Question;
import com.dhruv.question_service.model.QuestionWrapper;
import com.dhruv.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Added New Question Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to Add Question", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question updatedQuestion) {
        try {
            if (questionDao.existsById(id)) {
                updatedQuestion.setId(id);
                questionDao.save(updatedQuestion);
                return new ResponseEntity<>("Question Updated Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Question Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to Update Question", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            if (questionDao.existsById(id)) {
                questionDao.deleteById(id);
                return new ResponseEntity<>("Question Deleted Successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Question Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to Delete Question", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(String categoryName, Integer numQuesions){
        List<Integer> questions=questionDao.findRandomQuestionsByCategory(categoryName,numQuesions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds){
       List<QuestionWrapper> wrappers=new ArrayList<>();
       List<Question> questions=new ArrayList<>();

       for(Integer id: questionIds){
           questions.add(questionDao.findById(id).get());
       }

       for(Question question: questions){
           QuestionWrapper wrapper=new QuestionWrapper();
           wrapper.setId((question.getId()));
           wrapper.setQuestiontitle(question.getQuestiontitle());
           wrapper.setOption1(question.getOption1());
           wrapper.setOption2(question.getOption2());
           wrapper.setOption3(question.getOption3());
           wrapper.setOption4(question.getOption4());
           wrappers.add(wrapper);
       }
       return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses){

        int right=0;
        for(Response response:responses){
            Question question=questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightanswer()))
                right++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }


}
