package daae.learner.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "normalization_parameter_value", schema = "public", catalog = "dengue")
public class NormalizationParameterValue {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="normalization_parameter_value_id_seq")
    @SequenceGenerator(name="normalization_parameter_value_id_seq",sequenceName="normalization_parameter_value_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "value", nullable = false, precision = 0)
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "normalization_parameter_id", nullable = false)
    private NormalizationParameter normalizationParameter;
    @ManyToOne
    @JoinColumn(name = "training_variable_id", nullable = false)
    private TrainingVariable trainingVariable;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public NormalizationParameter getNormalizationParameter() {
        return normalizationParameter;
    }

    public void setNormalizationParameter(NormalizationParameter normalizationParameter) {
        this.normalizationParameter = normalizationParameter;
    }

    public TrainingVariable getTrainingVariable() {
        return trainingVariable;
    }

    public void setTrainingVariable(TrainingVariable trainingVariable) {
        this.trainingVariable = trainingVariable;
    }

}
