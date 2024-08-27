package com.surveymanager.principalui.surveyUser.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.surveymanager.categories_catalog.application.FindAllCategorie_catalogUseCase;
import com.surveymanager.categories_catalog.domain.Categorie_catalog;
import com.surveymanager.categories_catalog.domain.Categorie_catalogService;
import com.surveymanager.categories_catalog.infrastructure.Categorie_catalogRepository;
import com.surveymanager.chapter.domain.entity.Chapter;
import com.surveymanager.principalui.surveyUser.application.CreateSurveyUserUseCase;
import com.surveymanager.principalui.surveyUser.application.FindAllSubresponseUseCase;
import com.surveymanager.principalui.surveyUser.application.FindChapterUseCase;
import com.surveymanager.principalui.surveyUser.application.FindQuestionUseCase;
import com.surveymanager.principalui.surveyUser.application.FindResponseUseCase;
import com.surveymanager.principalui.surveyUser.application.FindSurveysResponsesUseCase;
import com.surveymanager.principalui.surveyUser.domain.entity.SurveyUser;
import com.surveymanager.principalui.surveyUser.domain.entity.SurveyUserDto;
import com.surveymanager.principalui.surveyUser.domain.service.SurveyUserService;
import com.surveymanager.question.domain.entity.Question;
import com.surveymanager.response.application.FindByIdResponseUseCase;
import com.surveymanager.response.domain.entity.Response;
import com.surveymanager.response.domain.service.ResponseService;
import com.surveymanager.response.infrastructure.ResponseRepository;
import com.surveymanager.subresponse.domain.Subresponse;
import com.surveymanager.survey.application.FindAllSurveyUseCase;
import com.surveymanager.survey.domain.entity.Survey;
import com.surveymanager.survey.domain.service.SurveyService;
import com.surveymanager.survey.infrastructure.SurveyRepository;

public class SurveyUserUi {
    private SurveyUserService surveyUserService;

    private SurveyService surveyService;
    private FindAllSurveyUseCase findAllSurveyUseCase;

    private Categorie_catalogService categorie_catalogService;
    private FindAllCategorie_catalogUseCase findAllCategories;

    private ResponseService responseService;
    private FindByIdResponseUseCase findByIdResponseUseCase;

    private FindChapterUseCase findChapterUseCase;
    private FindQuestionUseCase findQuestionUseCase;
    private FindResponseUseCase findResponseUseCase;
    private FindAllSubresponseUseCase findAllSubresponseUseCase;
    private CreateSurveyUserUseCase createSurveyUserUseCase;

    private FindSurveysResponsesUseCase findSurveysResponsesUseCase;

    public SurveyUserUi() {
        this.surveyUserService = new SurveyUserRepository();
        this.responseService = new ResponseRepository();
        this.surveyService = new SurveyRepository();
        this.findChapterUseCase = new FindChapterUseCase(surveyUserService);
        this.findAllSurveyUseCase = new FindAllSurveyUseCase(surveyService);
        this.categorie_catalogService = new Categorie_catalogRepository();
        this.findAllCategories = new FindAllCategorie_catalogUseCase(categorie_catalogService);
        this.findQuestionUseCase = new FindQuestionUseCase(surveyUserService);
        this.findResponseUseCase = new FindResponseUseCase(surveyUserService);
        this.findAllSubresponseUseCase = new FindAllSubresponseUseCase(surveyUserService);
        this.findByIdResponseUseCase = new FindByIdResponseUseCase(responseService);
        this.createSurveyUserUseCase = new CreateSurveyUserUseCase(surveyUserService);
        this.findSurveysResponsesUseCase = new FindSurveysResponsesUseCase(surveyUserService);
    }

    public void start() {
        int opt = 0;
        String opts = "1. Contestar Encuesta\n2. Mostrar Encuesta\n3. Volver";
        do {
            try {
                opt = Integer.parseInt(JOptionPane.showInputDialog(null, opts));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en el valor ingresado");
                continue;
            }
            switch (opt) {
                case 1:
                    SurveyUser surveyUser = new SurveyUser();
                    int idSurvey = showSurveys(findAllSurveyUseCase.execute());
                    int idChapter = showChapters(findChapterUseCase.execute(idSurvey));
                    int idcat = showCategories(findAllCategories.execute());
                    int idQue = showQuestions(findQuestionUseCase.execute(idChapter, idcat));
                    Response response = showResponseOpt(findResponseUseCase.execute(idQue));
                    int idSub = showSubResponseOpt(findAllSubresponseUseCase.execute(response.getId()));

                    surveyUser.setResponse_id(response.getId());

                    Integer subresponseId = (idSub == 0) ? null : idSub;
                    surveyUser.setSubresponse_id(subresponseId);

                    surveyUser.setResponsetext(response.getOptionText());
                    createSurveyUserUseCase.execute(surveyUser);

                    break;
                case 2:
                    findSurveysResponses();
                    break;
                case 3:
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Error en la opcion ingresada");
                    break;
            }
        } while (opt != 3);
    }

    public int showSurveys(List<Survey> surveys) {
        Map<String, Integer> map = new HashMap<>();
        JComboBox<String> dropDown = new JComboBox<>();
        surveys.forEach(survey -> {
            String row = survey.getId() + " - " + survey.getName();
            dropDown.addItem(row);
            map.put(row, survey.getId());
        });
        int result = JOptionPane.showConfirmDialog(null, dropDown, "Seleccione la encuesta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String text = (String) dropDown.getSelectedItem();
            int id = map.get(text);
            return id;
        }
        return 0;
    }

    public int showChapters(List<Chapter> chapters) {
        Map<String, Integer> map = new HashMap<>();
        JComboBox<String> dropDown = new JComboBox<>();
        chapters.forEach(chapter -> {
            String row = chapter.getId() + " - " + chapter.getChapter_title();
            dropDown.addItem(row);
            map.put(row, chapter.getId());
        });
        int result = JOptionPane.showConfirmDialog(null, dropDown, "Seleccione la capitulos",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String text = (String) dropDown.getSelectedItem();
            int idcha = map.get(text);
            return idcha;
        }
        return 0;
    }

    public int showCategories(List<Categorie_catalog> categories) {
        Map<String, Integer> map = new HashMap<>();
        JComboBox<String> dropDown = new JComboBox<>();
        categories.forEach(category -> {
            String row = category.getId() + " - " + category.getName();
            dropDown.addItem(row);
            map.put(row, category.getId());
        });
        int result = JOptionPane.showConfirmDialog(null, dropDown, "Seleccione la categoria",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String text = (String) dropDown.getSelectedItem();
            int id = map.get(text);
            return id;
        }
        return 0;
    }

    public int showQuestions(List<Question> questions) {
        Map<String, Integer> map = new HashMap<>();
        JComboBox<String> dropDown = new JComboBox<>();
        questions.forEach(question -> {
            String row = question.getId() + " - " + question.getQuestion_text();
            dropDown.addItem(row);
            map.put(row, question.getId());
        });
        int result = JOptionPane.showConfirmDialog(null, dropDown, "Seleccione la pregunta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String text = (String) dropDown.getSelectedItem();
            int id = map.get(text);
            return id;
        }
        return 0;
    }

    public Response showResponseOpt(List<Response> responses) {

        Map<String, Integer> map = new HashMap<>();
        JComboBox<String> dropDown = new JComboBox<>();
        responses.forEach(response -> {
            String row = response.getId() + " - " + response.getOptionText();
            dropDown.addItem(row);
            map.put(row, response.getId());
        });
        int result = JOptionPane.showConfirmDialog(null, dropDown, "Seleccione la respuesta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String text = (String) dropDown.getSelectedItem();
            int id = map.get(text);

            Response response = findByIdResponseUseCase.execute(id);
            return response;
        }
        return null;
    }

    public int showSubResponseOpt(List<Subresponse> subresponses) {
        if (subresponses.isEmpty()) {
            return 0;
        }

        Map<String, Integer> map = new HashMap<>();
        JComboBox<String> dropDown = new JComboBox<>();
        subresponses.forEach(subresponse -> {
            String row = subresponse.getId() + " - " + subresponse.getSubresponse_text();
            dropDown.addItem(row);
            map.put(row, subresponse.getId());
        });
        int result = JOptionPane.showConfirmDialog(null, dropDown, "Seleccione la subrespuesta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String text = (String) dropDown.getSelectedItem();
            int id = map.get(text);
            return id;
        }
        return 0;
    }

    public void showSurveysResponse(List<SurveyUserDto> surveys) {
        if (surveys == null || surveys.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay respuestas disponibles.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder detailsBuilder = new StringBuilder();

        for (SurveyUserDto survey : surveys) {
            detailsBuilder.append("ID: ").append(survey.getId())
                    .append("\nResponse ID: ").append(survey.getResponse_id())
                    .append("\nSubresponse ID: ")
                    .append(survey.getSubresponse_id() != null ? survey.getSubresponse_id() : "null")
                    .append("\nQuestion ID: ").append(survey.getQuestion_id())
                    .append("\nOption Text: ")
                    .append(survey.getOption_text() != null ? survey.getOption_text() : "null")
                    .append("\nSubresponse Text: ")
                    .append(survey.getSubresponse_text() != null ? survey.getSubresponse_text() : "null")
                    .append("\nQuestion Text: ")
                    .append(survey.getQuestion_text() != null ? survey.getQuestion_text() : "null")
                    .append("\n---------------------------------------\n");
        }

        JTextArea textArea = new JTextArea(detailsBuilder.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Detalles de las Respuestas", JOptionPane.INFORMATION_MESSAGE);
    }

    public void findSurveysResponses() {
        int idSurvey = showSurveys(findAllSurveyUseCase.execute());
        int idChapter = showChapters(findChapterUseCase.execute(idSurvey));
        showSurveysResponse(findSurveysResponsesUseCase.execute(idSurvey, idChapter));
    }

}
