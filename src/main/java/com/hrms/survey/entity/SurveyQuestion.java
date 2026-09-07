package com.hrms.survey.entity;


import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "survey_questions")
public class SurveyQuestion {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "survey_id", nullable = false) private UUID surveyId;
    @Column(name = "question_text", nullable = false, length = 500) private String questionText;
    @Column(name = "question_type", nullable = false, length = 30) private String questionType;
    @Column(length = 1000) private String options;
    @Column(name = "is_required", nullable = false) private boolean required = true;
    @Column(name = "order_index", nullable = false) private int orderIndex;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getSurveyId() { return surveyId; }
    public void setSurveyId(UUID surveyId) { this.surveyId = surveyId; }
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public String getQuestionType() { return questionType; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }
    public String getOptions() { return options; }
    public void setOptions(String options) { this.options = options; }
    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }
    public int getOrderIndex() { return orderIndex; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }
}
