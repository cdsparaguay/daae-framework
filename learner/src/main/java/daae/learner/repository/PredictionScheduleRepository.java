package daae.learner.repository;

import daae.learner.models.Model;
import daae.learner.models.PredictionSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PredictionScheduleRepository extends CrudRepository<PredictionSchedule, Long> {

    List<PredictionSchedule> findAllByModel(Model model);


    @Query(
            value = "SELECT pu.* FROM prediction_schedule pu join prediction_schedule_execution pse ON pu.id = pse.prediction_schedule_id\n" +
                    "                    and pse.date <= now() - interval '1' day * pu.period_days\n" +
                    "  WHERE pu.start_date <= now() and pu.end_date >= now() and pu.status = 'ACTIVE'\n" +
                    "UNION\n" +
                    "  SELECT pu.* FROM prediction_schedule pu where pu.id not in (select prediction_schedule_id from prediction_schedule_execution)\n" +
                    "  and pu.start_date <= now() and pu.end_date >= now() and pu.status = 'ACTIVE'\n",
            nativeQuery = true)
    List<PredictionSchedule> findAllToExecute();
}
