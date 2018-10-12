package daae.learner.repository;

import daae.learner.models.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProcedureRepository extends CrudRepository<Procedure, Long>  {

    List<Procedure> findAllByType(String type);
    List<Procedure> findAllByTarget(String target);
    Procedure findByClassName(String className);
}
