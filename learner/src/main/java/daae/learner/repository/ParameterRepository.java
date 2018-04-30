package daae.learner.repository;

import daae.learner.models.Parameter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParameterRepository extends CrudRepository<Parameter, Long> {

    List<Parameter> findAllByDomain(String domain);
}
