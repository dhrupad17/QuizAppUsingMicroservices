package com.dhruv.quiz_service.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionWrapper {

    private Integer id;
    @Column(name="questionTitle")
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

}