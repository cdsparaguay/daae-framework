package daee.learner.framework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ModelDTO implements Serializable{

    @JsonProperty
    private byte[] model;
    @JsonProperty
    private String class_name;
    @JsonProperty
    private Long training_id;
    @JsonProperty
    private List<TrainingVariableDTO> variables;
    @JsonProperty
    private String evaluationResult;
    @JsonProperty
    private String evaluationName;
    @JsonProperty
    private List<Predicted> predicteds;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date initialDate;


    public byte[] getModel() {
        return model;
    }

    public void setModel(byte[] model) {
        this.model = model;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String className) {
        this.class_name = className;
    }

    public ModelDTO(@JsonProperty("model") byte[] model, @JsonProperty("class_name") String class_name,
                    @JsonProperty("training_id") Long training_id,
                    @JsonProperty("variables") List<TrainingVariableDTO> variables,
                    @JsonProperty("evaluationResult") String evaluationResult,
                    @JsonProperty("evaluationName") String evaluationName,
                    @JsonProperty("predicteds") List<Predicted> predicteds,
                    @JsonProperty("initialDate") Date initialDate) {
        this.model = model;
        this.class_name = class_name;
        this.training_id =training_id;
        this.variables = variables;
        this.evaluationResult = evaluationResult;
        this.evaluationName = evaluationName;
        this.predicteds = predicteds;
        this.initialDate = initialDate;
    }

    public ModelDTO() {
    }

    public Long getTraining_id() {
        return training_id;
    }

    public void setTraining_id(Long training_id) {
        this.training_id = training_id;
    }

    public List<TrainingVariableDTO> getVariables() {
        return variables;
    }

    public void setVariables(List<TrainingVariableDTO> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getEvaluationResult() {
        return evaluationResult;
    }

    public void setEvaluationResult(String evaluationResult) {
        this.evaluationResult = evaluationResult;
    }

    public String getEvaluationName() {
        return evaluationName;
    }

    public void setEvaluationName(String evaluationName) {
        this.evaluationName = evaluationName;
    }

    public List<Predicted> getPredicteds() {
        return predicteds;
    }

    public void setPredicteds(List<Predicted> predicteds) {
        this.predicteds = predicteds;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }
}
