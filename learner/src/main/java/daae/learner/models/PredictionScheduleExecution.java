package daae.learner.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "prediction_schedule_execution", schema = "public", catalog = "dengue")
public class PredictionScheduleExecution {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="prediction_schedule_id_seq")
    @SequenceGenerator(name="prediction_schedule_id_seq",sequenceName="prediction_schedule_id_seq", allocationSize=1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Basic
    @Column(name = "status", nullable = false, length = 50)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;
    @ManyToOne
    @JoinColumn(name = "prediction_schedule_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PredictionSchedule predictionSchedule;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PredictionSchedule getPredictionSchedule() {
        return predictionSchedule;
    }

    public void setPredictionSchedule(PredictionSchedule predictionSchedule) {
        this.predictionSchedule = predictionSchedule;
    }
}
