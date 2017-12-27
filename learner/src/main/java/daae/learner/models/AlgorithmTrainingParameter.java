package daae.learner.models;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "algorithm_training_parameter", schema = "public", catalog = "dengue")
public class AlgorithmTrainingParameter {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="algorithm_training_parameter_id_seq")
    @SequenceGenerator(name="algorithm_training_parameter_id_seq",sequenceName="algorithm_training_parameter_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name = "value", nullable = false, length = 50)
    private String value;

    @ManyToOne
    @JoinColumn(name="training_id")
    private Training training;
    @Basic
    @Column(name = "default_value", length = 50)
    private String defaultValue;
    @Basic
    @Column(name = "range", length = 50)
    private String range;
    @Basic
    @Column(name = "data_type", nullable = false, length = 50)
    private String dataType;
    @Basic
    @Column(name = "field_name", nullable = false, length = 50)
    private String fieldName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }


    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }


    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
