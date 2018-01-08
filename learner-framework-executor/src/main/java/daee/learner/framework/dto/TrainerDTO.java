package daee.learner.framework.dto;

import java.util.List;
import java.util.Map;

public class TrainerDTO {

    private List<ParamDTO> params;
    private String dataset;
    private List<String> covariables;
    private String target;

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

    public List<String> getCovariables() {
        return covariables;
    }

    public void setCovariables(List<String> covariables) {
        this.covariables = covariables;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
