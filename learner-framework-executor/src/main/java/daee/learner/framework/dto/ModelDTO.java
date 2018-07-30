package daee.learner.framework.dto;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class ModelDTO implements Serializable{

    private byte[] model;
    private String class_name;
    private Long training_id;
    private List<TrainingVariableDTO> variables;

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

    public ModelDTO(byte[] model, String class_name, Long training_id, List<TrainingVariableDTO> variables) {
        this.model = model;
        this.class_name = class_name;
        this.training_id =training_id;
        this.variables = variables;
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
}
