package daae.learner.repository;

import daae.learner.models.Training;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainingRepository extends CrudRepository<Training, Long> {
    List<Training> findAllByStatus(String status);
}
