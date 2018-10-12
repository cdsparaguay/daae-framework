package daae.learner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainingVariableDTO;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import scala.tools.cmd.gen.AnyVals;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Model {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="model_id_seq")
    @SequenceGenerator(name="model_id_seq",sequenceName="model_id_seq", allocationSize=1)
    private Long id;

    @Column(name = "model", nullable = false)
    private byte[] model;

    @Column(name = "class_name", nullable = false)
    private String className;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    @JsonIgnore
    private Training training;

    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ModelVariable> modelVariables;

    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EvaluationValue> evaluationValues;

    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Prediction> predictions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getModel() {
        return model;
    }

    public void setModel(byte[] model) {
        this.model = model;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ModelVariable> getModelVariables() {
        return modelVariables;
    }

    public void setModelVariables(List<ModelVariable> modelVariables) {
        this.modelVariables = modelVariables;
    }

    public List<EvaluationValue> getEvaluationValues() {
        return evaluationValues;
    }

    public void setEvaluationValues(List<EvaluationValue> evaluationValues) {
        this.evaluationValues = evaluationValues;
    }

    public ModelDTO toModelDTO() {

        ModelDTO modelDTO = new ModelDTO();

        modelDTO.setModel(this.model);
        modelDTO.setClass_name(this.className);
        modelDTO.setTraining_id(this.training.getId());
        modelDTO.setVariables(new ArrayList<>());
        for(ModelVariable variable: this.getModelVariables()) {
            TrainingVariableDTO variableDTO = new TrainingVariableDTO(variable.getTrainingVariable().getName(),
                    variable.getId(), variable.getTrainingVariable().getTarget());
            modelDTO.getVariables().add(variableDTO);
        }

        return modelDTO;

    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }
}
