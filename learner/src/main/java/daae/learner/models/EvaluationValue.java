package daae.learner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "evaluation_value", schema = "public", catalog = "dengue")
public class EvaluationValue {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="evaluation_value_id_seq")
    @SequenceGenerator(name="evaluation_value_id_seq",sequenceName="evaluation_value_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "value", nullable = false, length = 50)
    private String value;

    @ManyToOne
    @JoinColumn(name="model_id")
    @JsonIgnore
    private Model model;

    @ManyToOne
    @JoinColumn(name="procedure_id")
    private Procedure procedure;


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

    public Model getModel() {
        return model;
    }

    public void setModel(Model modelId) {
        this.model = model;
    }

    public Procedure getProcedureId() {
        return procedure;
    }

    public void setProcedureId(Procedure procedureId) {
        this.procedure = procedure;
    }

}
