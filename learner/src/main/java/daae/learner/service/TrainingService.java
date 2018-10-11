package daae.learner.service;

import daae.learner.enums.TrainingStatus;
import daae.learner.exceptions.PersistenceException;
import daae.learner.models.*;
import daae.learner.repository.TrainingRepository;
import daee.learner.framework.dto.TrainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TrainingService {

    private final TrainingRepository repository;

    private final AlgorithmService algorithmService;

    private final JobExecutorService jobExecutorService;

    @Autowired
    public TrainingService(TrainingRepository repository, AlgorithmService algorithmService,
                           JobExecutorService jobExecutorService) {
        this.repository = repository;
        this.algorithmService = algorithmService;
        this.jobExecutorService = jobExecutorService;
    }

    public List<Training> getByStatus(TrainingStatus status) {
        return repository.findAllByStatus(status.name());
    }

    public Training setErrorsAndStatus(Long trainingId, String errors) {

        Training training = repository.findById(trainingId).get();
        if(errors == null) {
            training.setStatus(TrainingStatus.COMPLETED.name());
        } else {
            training.setStatus(TrainingStatus.FAILED.name());
            training.setErrors(errors);

        }
        training.setEndDate(new Date());
        return repository.save(training);
    }

    public Training completeTraining(Training training) {
        training.setStatus(TrainingStatus.COMPLETED.name());
        training.setEndDate(new Date());
        return repository.save(training);
    }


    public Training save (Training training) throws PersistenceException {

        boolean hasTarget = false;
        training.setStatus(TrainingStatus.NEW.name());
        if(training.getAlgorithm() == null) {
            throw new PersistenceException("You must specify an algorithm to use");
        }
        Algorithm algorithm = algorithmService.getRepository().findById(training.getAlgorithm().getId())
                .orElseThrow(() -> new PersistenceException("You must specify an algorithm to use"));
        if(training.getParameters().isEmpty()) {
            throw new PersistenceException("Training cannot have empty parameters");
        }

        for(AlgorithmTrainingParameter parameter: training.getParameters()) {
            parameter.setTraining(training);
        }

        if (training.getVariables().size() < 2) {
            throw new PersistenceException("Training must have at least two variables");
        }

        for (TrainingVariable trainingVariable: training.getVariables()) {
            trainingVariable.setTraining(training);
            if(trainingVariable.getTarget()) {
                hasTarget= true;
            }
        }


        if(!hasTarget) {
            throw new PersistenceException("Training must have at least one target variables");
        }

        Training trainingSave = repository.save(training);

        TrainerDTO trainerDTO = trainingSave.toTrainingDTO();
        trainerDTO.setAlgorithmName(algorithm.getClassName());
        jobExecutorService.train(trainerDTO);

        return trainingSave;
    }


    public TrainingRepository getRepository() {
        return repository;
    }
}
