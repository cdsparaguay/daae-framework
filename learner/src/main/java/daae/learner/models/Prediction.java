package daae.learner.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import daee.learner.framework.dto.Predicted;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    @Column(name = "real_value")
    private String realValue;
    @Basic
    @Column(name = "dataset_code", nullable = false)
    private String dataSetCode;
    @Basic
    @Column(name = "date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    @JsonIgnore
    private Model model;
    @Basic
    @Column(name = "for_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forDate;

    @OneToMany(mappedBy = "prediction", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PredictionVariableValue> variableValues;


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

    public Date getForDate() {
        return forDate;
    }

    public void setForDate(Date forDate) {
        this.forDate = forDate;
    }

    public List<PredictionVariableValue> getVariableValues() {
        return variableValues;
    }

    public void setVariableValues(List<PredictionVariableValue> variableValues) {
        this.variableValues = variableValues;
    }

    public String getDataSetCode() {
        return dataSetCode;
    }

    public void setDataSetCode(String dataSetCode) {
        this.dataSetCode = dataSetCode;
    }

    public String getRealValue() {
        return realValue;
    }

    public void setRealValue(String realValue) {
        this.realValue = realValue;
    }

    public static List<Prediction> toPrediction(List<Predicted> predicted, Training training, Model model,
                                                Date initialDate) {

        List<Prediction> predictions = new ArrayList<>();
        Date predictionDate = new Date();
        int days  = training.getAnticipation();
        for(Predicted pred: predicted) {
            Prediction p = new Prediction();
            initialDate = addDays(initialDate, days);
            p.setDataSetCode(training.getDatasetCode());
            p.setDate(predictionDate);
            p.setForDate(initialDate);
            p.setModel(model);
            p.setPredictedValue(pred.getPredicted());
            p.setRealValue(pred.getReal());
            predictions.add(p);
        }

        return predictions;


    }
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

}
