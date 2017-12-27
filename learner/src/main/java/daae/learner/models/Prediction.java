package daae.learner.models;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Prediction {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="prediction_id_seq")
    @SequenceGenerator(name="prediction_id_seq",sequenceName="prediction_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "predicted_value", nullable = false, length = 50)
    private String predictedValue;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;
    @Basic
    @Column(name = "for_date", nullable = false)
    private Timestamp forDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPredictedValue() {
        return predictedValue;
    }

    public void setPredictedValue(String predictedValue) {
        this.predictedValue = predictedValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Timestamp getForDate() {
        return forDate;
    }

    public void setForDate(Timestamp forDate) {
        this.forDate = forDate;
    }

}
