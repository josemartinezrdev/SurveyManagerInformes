package com.surveymanager.principalui.surveyUser.domain.entity;

public class SurveyUserDto {
    private Integer id;
    private String option_text;
    private String subresponse_text;
    private String question_text;

    private Integer response_id;
    private Integer subresponse_id;
    private Integer question_id;

    public SurveyUserDto() {
    }

    public SurveyUserDto(Integer id, String option_text, String subresponse_text, String question_text, Integer response_id,
            Integer subresponse_id, Integer question_id) {
        this.id = id;
        this.option_text = option_text;
        this.subresponse_text = subresponse_text;
        this.question_text = question_text;
        this.response_id = response_id;
        this.subresponse_id = subresponse_id;
        this.question_id = question_id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOption_text() {
        return this.option_text;
    }

    public void setOption_text(String option_text) {
        this.option_text = option_text;
    }

    public String getSubresponse_text() {
        return this.subresponse_text;
    }

    public void setSubresponse_text(String subresponse_text) {
        this.subresponse_text = subresponse_text;
    }

    public String getQuestion_text() {
        return this.question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public Integer getResponse_id() {
        return this.response_id;
    }

    public void setResponse_id(Integer response_id) {
        this.response_id = response_id;
    }

    public Integer getSubresponse_id() {
        return this.subresponse_id;
    }

    public void setSubresponse_id(Integer subresponse_id) {
        this.subresponse_id = subresponse_id;
    }

    public Integer getQuestion_id() {
        return this.question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

}
