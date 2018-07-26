package daae.learner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "training_variable", schema = "public", catalog = "dengue")
public class TrainingVariable {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="training_variable_id_seq")
    @SequenceGenerator(name="training_variable_id_seq",sequenceName="training_variable_id_seq", allocationSize=1)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "training_id", nullable = false)
    @JsonIgnore
    private Training training;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "target", nullable = false)
    private Boolean target;
    @Basic
    @Column(name = "data_type", nullable = false, length = 50)
    private String dataType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTarget() {
        return target;
    }

    public void setTarget(Boolean target) {
        this.target = target;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

}
