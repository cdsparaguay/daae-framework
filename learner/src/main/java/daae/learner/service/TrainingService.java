package daae.learner.service;

import daae.learner.enums.TrainingStatus;
import daae.learner.models.Training;
import daae.learner.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TrainingService {
    private final TrainingRepository repository;



    @Autowired
    public TrainingService(TrainingRepository repository) {
        this.repository = repository;
    }

    public List<Training> getByStatus(TrainingStatus status) {
        return repository.findAllByStatus(status.name());
    }

    public Training completeTraining(Training training) {
        training.setStatus(TrainingStatus.COMPLETED.name());
        training.setEndDate(new Date());
        return repository.save(training);
    }

    public TrainingRepository getRepository() {
        return repository;
    }
}
