package daae.learner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "validation_value", schema = "public", catalog = "dengue")
public class ValidationValue {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="validation_value_id_seq")
    @SequenceGenerator(name="validation_value_id_seq",sequenceName="validation_value_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "k", nullable = false)
    private Integer k;
    @Basic
    @Column(name = "train")
    private Double train;
    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    @JsonIgnore
    private Training training;
    @ManyToOne
    @JoinColumn(name = "procedure_id", nullable = false)
    private Procedure procedure;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Double getTrain() {
        return train;
    }

    public void setTrain(Double train) {
        this.train = train;
    }

    public Training getTrainingId() {
        return training;
    }

    public void setTrainingId(Training training) {
        this.training = training;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

}
