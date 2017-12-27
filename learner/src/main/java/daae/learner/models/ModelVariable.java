package daae.learner.models;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "model_variable", schema = "public", catalog = "dengue")
public class ModelVariable {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="model_variable_id_seq")
    @SequenceGenerator(name="model_variable_id_seq",sequenceName="model_variable_id_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "training_variable_id", nullable = false)
    private TrainingVariable trainingVariable;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model modelId) {
        this.model = model;
    }

    public TrainingVariable getTrainingVariable() {
        return trainingVariable;
    }

    public void setTrainingVariable(TrainingVariable trainingVariable) {
        this.trainingVariable = trainingVariable;
    }

}
