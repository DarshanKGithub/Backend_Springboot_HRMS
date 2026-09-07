package com.hrms.workflow;

import com.hrms.workflow.entity.*;
import com.hrms.workflow.repository.*;
import com.hrms.workflow.dto.*;
import com.hrms.survey.entity.*;
import com.hrms.survey.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WorkflowSurveyDataAccessTest {

    @Autowired private FormTemplateRepository formTemplateRepository;
    @Autowired private WorkflowTemplateRepository workflowTemplateRepository;
    @Autowired private WorkflowInstanceRepository workflowInstanceRepository;
    @Autowired private SurveyRepository surveyRepository;
    @Autowired private SurveyQuestionRepository surveyQuestionRepository;
    @Autowired private SurveyResponseRepository surveyResponseRepository;
    @Autowired private SurveyAnswerRepository surveyAnswerRepository;
    @Autowired private FeedbackRepository feedbackRepository;

    @Test
    void shouldPersistWorkflowAndSurveyEntities() {
        FormTemplate form = new FormTemplate();
        form.setName("Employee onboarding");
        form.setSchemaJson("{\"fields\":[]}");
        FormTemplate savedForm = formTemplateRepository.save(form);

        WorkflowTemplate template = new WorkflowTemplate();
        template.setName("Onboarding workflow");
        template.setTriggerEvent("ON_EMPLOYEE_JOIN");
        template.setStepsJson("[\"collect_documents\",\"approve\"]");
        WorkflowTemplate savedTemplate = workflowTemplateRepository.save(template);

        WorkflowInstance instance = new WorkflowInstance();
        instance.setTemplateId(savedTemplate.getId());
        instance.setTargetId(UUID.randomUUID());
        WorkflowInstance savedInstance = workflowInstanceRepository.save(instance);

        Survey survey = new Survey();
        survey.setTitle("Workplace survey");
        survey.setCreatedBy(UUID.randomUUID());
        Survey savedSurvey = surveyRepository.save(survey);

        SurveyQuestion question = new SurveyQuestion();
        question.setSurveyId(savedSurvey.getId());
        question.setQuestionText("How is your experience?");
        question.setQuestionType("Rating");
        question.setOrderIndex(0);
        SurveyQuestion savedQuestion = surveyQuestionRepository.save(question);

        SurveyResponse response = new SurveyResponse();
        response.setSurveyId(savedSurvey.getId());
        response.setEmployeeId(UUID.randomUUID());
        SurveyResponse savedResponse = surveyResponseRepository.save(response);

        SurveyAnswer answer = new SurveyAnswer();
        answer.setResponseId(savedResponse.getId());
        answer.setQuestionId(savedQuestion.getId());
        answer.setRatingValue(5);
        SurveyAnswer savedAnswer = surveyAnswerRepository.save(answer);

        Feedback feedback = new Feedback();
        feedback.setEmployeeId(UUID.randomUUID());
        feedback.setFeedbackType("Peer");
        feedback.setContent("Strong collaboration");
        Feedback savedFeedback = feedbackRepository.save(feedback);

        assertThat(formTemplateRepository.findById(savedForm.getId())).isPresent();
        assertThat(workflowInstanceRepository.findById(savedInstance.getId())).isPresent();
        assertThat(surveyAnswerRepository.findById(savedAnswer.getId())).isPresent();
        assertThat(feedbackRepository.findById(savedFeedback.getId())).isPresent();
    }
}
