package daae.learner.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

@Entity
public class Model {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="model_id_seq")
    @SequenceGenerator(name="model_id_seq",sequenceName="model_id_seq", allocationSize=1)
    private Long id;

    @Type(type = "JsonDataUserType")
    private Map<String, String> model;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getModel() {
        return model;
    }

    public void setModel(Map<String, String> model) {
        this.model = model;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training trainingId) {
        this.training = trainingId;
    }

}
