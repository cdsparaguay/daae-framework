package daee.learner.framework.dto;

import java.util.List;
import java.util.Map;

public class TrainerDTO {

    private List<ParamDTO> params;
    private String dataset;
    private List<TrainingVariableDTO> variables;
    private Long traniningId;

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

    public Long getTraniningId() {
        return traniningId;
    }

    public void setTraniningId(Long traniningId) {
        this.traniningId = traniningId;
    }
}
