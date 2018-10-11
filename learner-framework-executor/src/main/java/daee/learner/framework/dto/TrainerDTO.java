package daee.learner.framework.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
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
    @JsonProperty
    private String evaluatorName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date initialDate;


    @JsonCreator
    public TrainerDTO(@JsonProperty("params") List<ParamDTO> params, @JsonProperty("dataset") String dataset,
                      @JsonProperty("variables") List<TrainingVariableDTO> variables,
                      @JsonProperty("trainingId") Long trainingId, @JsonProperty("dataUrl") String dataUrl,
                      @JsonProperty("algorithmName") String algorithmName,
                      @JsonProperty("evaluatorName") String evaluatorName,
                      @JsonProperty("initialDate") Date initialDate) {
        this.params = params;
        this.dataset = dataset;
        this.variables = variables;
        this.trainingId = trainingId;
        this.dataUrl = dataUrl;
        this.algorithmName = algorithmName;
        this.evaluatorName = evaluatorName;
        this.initialDate = initialDate;
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

    public String getEvaluatorName() {
        return evaluatorName;
    }

    public void setEvaluatorName(String evaluatorName) {
        this.evaluatorName = evaluatorName;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }
}
