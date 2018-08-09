package daae.learner.rest;


import com.google.common.collect.Lists;
import daae.learner.exceptions.PersistenceException;
import daae.learner.models.PredictionSchedule;
import daae.learner.service.PredictionScheduleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/predictions_schedules")
@Api(value = "Predictions Schedules", description = "Read and create predictions schedules")
public class PredictionSchedules {
    private final PredictionScheduleService service;

    @Autowired
    public PredictionSchedules(PredictionScheduleService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<PredictionSchedule> getPredictionSchedules(){
        return Lists.newArrayList(service.getRepository().findAll());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON, value="/model/{modelId}")
    public List<PredictionSchedule> getByModel(@PathVariable(value = "modelId") Long modelId) {
        List<PredictionSchedule> predictions = service.getByModel(modelId);
        if (predictions == null || predictions.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return predictions;
    }

    @RequestMapping(method = RequestMethod.POST)
    public PredictionSchedule add(@RequestBody PredictionSchedule predictionSchedule) throws PersistenceException {
        try {
            return service.save(predictionSchedule);
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{predictionScheduleId}")
    public void delete(@PathVariable("predictionScheduleId") Long predictionScheduleId){
        PredictionSchedule pd = service.getRepository().findById(predictionScheduleId).orElseThrow(() ->
                new ResourceNotFoundException("Schedule doesn't exists"));
        service.getRepository().delete(pd);
    }
}
