package daee.learner.framework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Predicted {

    @JsonProperty
    private String real;
    @JsonProperty
    private String predicted;

    public Predicted(@JsonProperty("real") String real, @JsonProperty("predicted")  String predicted) {
        this.real = real;
        this.predicted = predicted;
    }

    public String getReal() {
        return real;
    }

    public void setReal(String real) {
        this.real = real;
    }

    public String getPredicted() {
        return predicted;
    }

    public void setPredicted(String predicted) {
        this.predicted = predicted;
    }
}
