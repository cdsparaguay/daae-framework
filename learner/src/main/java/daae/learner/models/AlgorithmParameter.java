package daae.learner.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "algorithm_parameter", schema = "public", catalog = "dengue")
public class AlgorithmParameter {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="algorithm_parameter_id_seq")
    @SequenceGenerator(name="algorithm_parameter_id_seq",sequenceName="algorithm_parameter_id_seq", allocationSize=1)
    private Long id;

    @ManyToOne
    @JoinColumn(name="algorithm_id")
    @JsonIgnore
    private Algorithm algorithm;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;
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

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
