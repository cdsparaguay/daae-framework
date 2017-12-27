package daae.learner.models;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class Parameter {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="parameter_id_seq")
    @SequenceGenerator(name="parameter_id_seq",sequenceName="parameter_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "value", nullable = false, length = 50)
    private String value;
    @Basic
    @Column(name = "code", nullable = false, length = 50)
    private String code;
    @Basic
    @Column(name = "domain", nullable = false, length = 50)
    private String domain;
    @Basic
    @Column(name = "order", nullable = true)
    private Integer order;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
