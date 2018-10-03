package daee.learner.framework.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TrainingVariableDTO {

    private String name;
    private Long id;
    private boolean target;

    @JsonCreator
    public TrainingVariableDTO(@JsonProperty("name") String name, @JsonProperty("id") Long id,
                               @JsonProperty("target") boolean target) {
        this.name = name;
        this.id = id;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isTarget() {
        return target;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }
}
