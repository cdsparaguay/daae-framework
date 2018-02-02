package daae.learner.models;

import javax.persistence.*;
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
    private Training training;

    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER)
    private List<ModelVariable> modelVariables;


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
}
