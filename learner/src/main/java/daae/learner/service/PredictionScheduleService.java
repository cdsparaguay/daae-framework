package daae.learner.service;

import daae.learner.exceptions.PersistenceException;
import daae.learner.models.Model;
import daae.learner.models.PredictionSchedule;
import daae.learner.repository.PredictionScheduleRepository;
import daee.learner.framework.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PredictionScheduleService {

    private final PredictionScheduleRepository repository;

    private final ModelService modelService;

    private final JobExecutorService executorService;

    @Autowired
    public PredictionScheduleService(PredictionScheduleRepository repository,
                                     ModelService modelService, JobExecutorService executorService) {
        this.repository = repository;
        this.modelService = modelService;
        this.executorService = executorService;
    }

    public List<PredictionSchedule> getByModel(Long modelId) {
        Model model = modelService.getRepository().findById(modelId).orElse(null);
        if (model == null) {
            return null;
        } else {
            return repository.findAllByModel(model);
        }
    }

    public void executeScheduledPredictions() {

        List<PredictionSchedule> scheduleList = repository.findAllToExecute();
        Logger.getLogger(PredictionScheduleService.class.getName()).info("Found "
                + scheduleList.size() + " schedules to run");
        for(PredictionSchedule predictionSchedule: scheduleList) {

            try {
                executorService.predict(predictionSchedule.getModel().toModelDTO(), )
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public PredictionSchedule save (PredictionSchedule predictionSchedule) throws PersistenceException {

        if(predictionSchedule.getModel() == null) {
            throw new ResourceNotFoundException("Model cannot be null");
        }

        Model model = modelService.getRepository().findById(predictionSchedule.getModel().getId()).
                orElseThrow(() -> new ResourceNotFoundException("Model doesn't exists"));

        if (predictionSchedule.getEndDate().before(predictionSchedule.getStartDate())) {
            throw new PersistenceException("The end date must be after the start date");
        }
        predictionSchedule.setModel(model);
        return repository.save(predictionSchedule);
    }

    public PredictionScheduleRepository getRepository() {
        return repository;
    }
}
