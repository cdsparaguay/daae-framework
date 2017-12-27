package daae.learner.models;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "prediction_variable_value", schema = "public", catalog = "dengue")
public class PredictionVariableValue {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="prediction_variable_value_id_seq")
    @SequenceGenerator(name="prediction_variable_value_id_seq",sequenceName="prediction_variable_value_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "value", nullable = false, length = 50)
    private String value;
    @ManyToOne
    @JoinColumn(name = "model_variable_id", nullable = false)
    private ModelVariable modelVariable;
    @ManyToOne
    @JoinColumn(name = "prediction_id", nullable = false)
    private Prediction prediction;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ModelVariable getModelVariable() {
        return modelVariable;
    }

    public void setModelVariable(ModelVariable modelVariable) {
        this.modelVariable = modelVariable;
    }

    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }

}
