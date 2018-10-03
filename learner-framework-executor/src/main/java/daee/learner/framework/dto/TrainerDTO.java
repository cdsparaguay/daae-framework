package daee.learner.framework.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class TrainerDTO {

    @JsonProperty
    private List<ParamDTO> params;
    @JsonProperty
    private String dataset;
    @JsonProperty
    private List<TrainingVariableDTO> variables;
    @JsonProperty
    private Long trainingId;
    @JsonProperty
    private String dataUrl;
    @JsonProperty
    private String algorithmName;


    @JsonCreator
    public TrainerDTO(@JsonProperty("params") List<ParamDTO> params, @JsonProperty("dataset") String dataset,
                      @JsonProperty("variables") List<TrainingVariableDTO> variables,
                      @JsonProperty("trainingId") Long trainingId, @JsonProperty("dataUrl") String dataUrl,
                      @JsonProperty("algorithmName") String algorithmName) {
        this.params = params;
        this.dataset = dataset;
        this.variables = variables;
        this.trainingId = trainingId;
        this.dataUrl = dataUrl;
        this.algorithmName = algorithmName;
    }

    public List<ParamDTO> getParams() {
        return params;
    }

    public void setParams(List<ParamDTO> params) {
        this.params = params;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public List<TrainingVariableDTO> getVariables() {
        return variables;
    }

    public void setVariables(List<TrainingVariableDTO> variables) {
        this.variables = variables;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    @JsonIgnore
    public String[] getTargetVariablesName() {
        List<String> toRet = new ArrayList<>();
        for (TrainingVariableDTO trainingVariableDTO: this.getVariables()) {
            if(trainingVariableDTO.isTarget()) {
                toRet.add(trainingVariableDTO.getName());
            }
        }
        return toRet.toArray(new String[0]);
    }

    @JsonIgnore
    public String[] getFeatureVariablesName() {
        List<String> toRet = new ArrayList<>();
        for (TrainingVariableDTO trainingVariableDTO: this.getVariables()) {
            if(!trainingVariableDTO.isTarget()) {
                toRet.add(trainingVariableDTO.getName());
            }
        }
        return toRet.toArray(new String[0]);
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
