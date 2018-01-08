package daae.learner.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
public class Training {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "SERIAL")
    @GeneratedValue(generator="training_id_seq")
    @SequenceGenerator(name="training_id_seq",sequenceName="training_id_seq", allocationSize=1)
    private Long id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Basic
    @Column(name = "dataset_code", nullable = false, length = 50)
    private String datasetCode;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "algorithm_id", nullable = false)
    private Algorithm algorithm;
    @Basic
    @Column(name = "granularity_days", nullable = false)
    private Integer granularityDays;
    @Basic
    @Column(name = "anticipation", nullable = false)
    private Integer anticipation;
    @Basic
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<AlgorithmTrainingParameter> parameters;
    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TrainingVariable> variables;


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

    public String getDatasetCode() {
        return datasetCode;
    }

    public void setDatasetCode(String datasetCode) {
        this.datasetCode = datasetCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }


    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }


    public Integer getGranularityDays() {
        return granularityDays;
    }

    public void setGranularityDays(Integer granularityDays) {
        this.granularityDays = granularityDays;
    }


    public Integer getAnticipation() {
        return anticipation;
    }

    public void setAnticipation(Integer anticipation) {
        this.anticipation = anticipation;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<AlgorithmTrainingParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<AlgorithmTrainingParameter> parameters) {
        this.parameters = parameters;
    }

    public List<TrainingVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<TrainingVariable> variables) {
        this.variables = variables;
    }
}
