package daae.learner.service;

import daae.learner.enums.AlgorithmType;
import daae.learner.exceptions.PersistenceException;
import daae.learner.models.Algorithm;
import daae.learner.models.AlgorithmParameter;
import daae.learner.repository.AlgorithmRepository;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmService {

    private final AlgorithmRepository repository;

    public AlgorithmService(AlgorithmRepository repository) {
        this.repository = repository;
    }

    public AlgorithmRepository getRepository() {
        return repository;
    }

    public Algorithm save (Algorithm algorithm) throws PersistenceException {

        try {
            AlgorithmType.valueOf(algorithm.getType()).name();
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Invalid type parameter");
        }
        if(algorithm.getParameters().isEmpty()) {
            throw new PersistenceException("Algorithms cannot have empty parameters");
        }
        for(AlgorithmParameter parameter: algorithm.getParameters()) {
            parameter.setAlgorithm(algorithm);
        }

        return repository.save(algorithm);
    }
}
