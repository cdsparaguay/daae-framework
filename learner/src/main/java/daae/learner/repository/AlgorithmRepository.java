package daae.learner.repository;

import daae.learner.models.Algorithm;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlgorithmRepository extends CrudRepository<Algorithm, Long> {

    List<Algorithm> findAllByType(String type);
}
