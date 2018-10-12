package daae.learner.repository;

import daae.learner.models.Model;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModelRepository extends CrudRepository<Model, Long> {

    List<Model> findAllByTrainingId(Long trainingId);
}
