package com.surveymanager.principalui.surveyUser.application;

import java.util.List;

import com.surveymanager.principalui.surveyUser.domain.entity.SurveyUserDto;
import com.surveymanager.principalui.surveyUser.domain.service.SurveyUserService;


public class FindSurveysResponsesUseCase {
    private final SurveyUserService surveyUserService;

    public FindSurveysResponsesUseCase(SurveyUserService surveyUserService) {
        this.surveyUserService = surveyUserService;
    }

    public List<SurveyUserDto> execute(int idSurvey, int idChapter) {
        return surveyUserService.findSurveysResponses(idSurvey, idChapter);
    }

}