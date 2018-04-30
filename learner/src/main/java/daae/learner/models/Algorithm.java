package daae.learner.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "algorithm")
public class Algorithm {

    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="algorithm_id_seq")
    @SequenceGenerator(name="algorithm_id_seq",sequenceName="algorithm_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Basic
    @Column(name = "class_name", nullable = false, length = 50)
    private String className;
    @Basic
    @Column(name = "description", nullable = false, length = 200)
    private String description;
    @Basic
    @Column(name = "viewer", nullable = false)
    private Boolean viewer;
    @Basic
    @Column(name = "norm", nullable = false)
    private Boolean norm;
    @Basic
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Basic
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @OneToMany(mappedBy = "algorithm", fetch = FetchType.EAGER)
    private List<AlgorithmParameter> parameters;


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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean getViewer() {
        return viewer;
    }

    public void setViewer(Boolean viewer) {
        this.viewer = viewer;
    }


    public Boolean getNorm() {
        return norm;
    }

    public void setNorm(Boolean norm) {
        this.norm = norm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AlgorithmParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<AlgorithmParameter> parameters) {
        this.parameters = parameters;
    }
}
