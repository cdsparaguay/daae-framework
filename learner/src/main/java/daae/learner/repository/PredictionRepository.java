package daae.learner.repository;

import daae.learner.models.Model;
import daae.learner.models.Prediction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PredictionRepository extends CrudRepository<Prediction, Long> {

    List<Prediction> findAllByModel(Model model);
}
