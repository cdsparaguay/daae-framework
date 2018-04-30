package daae.learner.repository;

import daae.learner.models.Model;
import daae.learner.models.PredictionSchedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PredictionScheduleRepository extends CrudRepository<PredictionSchedule, Long> {

    List<PredictionSchedule> findAllByModel(Model model);
}
