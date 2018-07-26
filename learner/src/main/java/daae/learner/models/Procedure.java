package daae.learner.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
public class Procedure {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="procedure_id_seq")
    @SequenceGenerator(name="procedure_id_seq",sequenceName="procedure_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name = "description", nullable = false, length = 200)
    private String description;
    @Basic
    @Column(name = "class_name", nullable = false, length = 50)
    private String className;
    @Basic
    @Column(name = "target", nullable = false, length = 50)
    private String target;
    @Basic
    @Column(name = "type", nullable = false, length = 50)
    private String type;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
