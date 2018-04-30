package daae.learner.rest;

import com.google.common.collect.Lists;
import daae.learner.enums.TrainingStatus;
import daae.learner.models.Algorithm;
import daae.learner.models.Training;
import daae.learner.service.TrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/trainings")
@Api(value = "Models prediction training", description = "View and create models prediction trainings")
public class Trainings {

    private final TrainingService service;

    @Autowired
    public Trainings(TrainingService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "View a list of trainings made")
    public List<Training> getAll() {

        return Lists.newArrayList(service.getRepository().findAll());

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON, path = "/domain/{domain}")
    @ApiOperation(value = "View a list of all trainings by status", notes = "The current possible status are NEW - CANCELED - COMPLETED - TRAINING ")
    public List<Training> getAllByStatus(@PathVariable("status") String status) {
        try {
            return service.getByStatus(TrainingStatus.valueOf(status));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status parameter");
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Training save(@RequestBody Training training){
        training.setStatus(TrainingStatus.NEW.name());
        return service.getRepository().save(training);
    }

}
