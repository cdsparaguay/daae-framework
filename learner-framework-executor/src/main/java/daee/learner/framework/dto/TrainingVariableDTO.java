package daee.learner.framework.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TrainingVariableDTO {

    private String name;
    private Long id;
    private Boolean target;

    @JsonCreator
    public TrainingVariableDTO(String name, Long id, Boolean target) {
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

    public Boolean getTarget() {
        return target;
    }

    public void setTarget(Boolean target) {
        this.target = target;
    }
}
