package com.hrms.survey.entity;


import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "survey_answers")
public class SurveyAnswer {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "response_id", nullable = false) private UUID responseId;
    @Column(name = "question_id", nullable = false) private UUID questionId;
    @Column(name = "answer_text", length = 2000) private String answerText;
    @Column(name = "rating_value") private Integer ratingValue;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getResponseId() { return responseId; }
    public void setResponseId(UUID responseId) { this.responseId = responseId; }
    public UUID getQuestionId() { return questionId; }
    public void setQuestionId(UUID questionId) { this.questionId = questionId; }
    public String getAnswerText() { return answerText; }
    public void setAnswerText(String answerText) { this.answerText = answerText; }
    public Integer getRatingValue() { return ratingValue; }
    public void setRatingValue(Integer ratingValue) { this.ratingValue = ratingValue; }
}
