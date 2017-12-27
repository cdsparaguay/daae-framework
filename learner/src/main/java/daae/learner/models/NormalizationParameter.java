package daae.learner.models;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "normalization_parameter", schema = "public", catalog = "dengue")
public class NormalizationParameter {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="normalization_parameter_id_seq")
    @SequenceGenerator(name="normalization_parameter_id_seq",sequenceName="normalization_parameter_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic
    @Column(name = "field_name", nullable = false, length = 100)
    private String fieldName;
    @ManyToOne
    @JoinColumn(name = "procedure_id", nullable = false)
    private Procedure procedure;


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

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedureId) {
        this.procedure = procedure;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
