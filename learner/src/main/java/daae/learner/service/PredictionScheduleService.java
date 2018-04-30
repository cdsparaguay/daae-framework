package daae.learner.service;

import daae.learner.models.Model;
import daae.learner.models.Prediction;
import daae.learner.models.PredictionSchedule;
import daae.learner.repository.PredictionRepository;
import daae.learner.repository.PredictionScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public class PredictionScheduleService {

    private final PredictionScheduleRepository repository;

    private final ModelService modelService;

    @Autowired
    public PredictionScheduleService(PredictionScheduleRepository repository, ModelService modelService) {
        this.repository = repository;
        this.modelService = modelService;
    }

    public List<PredictionSchedule> getByModel(Long modelId) {
        Model model = modelService.getRepository().findById(modelId).orElse(null);
        if (model == null) {
            return null;
        } else {
            return repository.findAllByModel(model);
        }
    }

    public PredictionSchedule save(PredictionSchedule predictionSchedule) throws ValidationException {

        if(predictionSchedule.getModel() == null) {
            throw new ResourceNotFoundException("Model cannot be null");
        }

        Model model = modelService.getRepository().findById(predictionSchedule.getModel().getId()).
                orElseThrow(() -> new ResourceNotFoundException("Model doesn't exists"));

        if (predictionSchedule.getEndDate().before(predictionSchedule.getStartDate())) {
            throw new ValidationException("The end date must be after the start date");
        }
        predictionSchedule.setModel(model);
        return repository.save(predictionSchedule);
    }

    public PredictionScheduleRepository getRepository() {
        return repository;
    }
}
