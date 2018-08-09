package daae.learner.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "prediction_schedule", schema = "public", catalog = "dengue")
public class PredictionSchedule {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="prediction_schedule_id_seq")
    @SequenceGenerator(name="prediction_schedule_id_seq",sequenceName="prediction_schedule_id_seq", allocationSize=1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
    @Basic
    @Column(name = "status", nullable = false, length = 50)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;
    @Basic
    @Column(name = "period_days", nullable = false)
    private Integer periodDays;
    @Basic
    @Column(name = "time", nullable = false)
    private Date time;
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Model model;

    @OneToMany(mappedBy = "predictionSchedule", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PredictionScheduleExecution> executions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(Integer periodDays) {
        this.periodDays = periodDays;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public List<PredictionScheduleExecution> getExecutions() {
        return executions;
    }

    public void setExecutions(List<PredictionScheduleExecution> executions) {
        this.executions = executions;
    }
}
