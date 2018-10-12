package daae.learner.service;

import daae.learner.enums.ProcedureTarget;
import daae.learner.models.Procedure;
import daae.learner.repository.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcedureService {
    private final ProcedureRepository repository;

    @Autowired
    public ProcedureService(ProcedureRepository repository) {
        this.repository = repository;
    }

    public Procedure save(Procedure procedure) {
        try {
            ProcedureTarget.valueOf(procedure.getType()).name();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid type parameter");
        }
        return repository.save(procedure);
    }


    public ProcedureRepository getRepository() {
        return repository;
    }
}
