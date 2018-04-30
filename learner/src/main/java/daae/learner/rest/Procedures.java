package daae.learner.rest;

import com.google.common.collect.Lists;
import daae.learner.enums.AlgorithmType;
import daae.learner.enums.ProcedureTarget;
import daae.learner.models.Algorithm;
import daae.learner.models.Procedure;
import daae.learner.service.ProcedureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/procedures")
@Api(value = "Procedures", description = "Read and create procedures: Normalizations, Validations and Evaluations")
public class Procedures {

    private final ProcedureService service;

    @Autowired
    public Procedures(ProcedureService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON, path = "/type/{type}")
    @ApiOperation(value = "View a list of all procedures by type. ", notes = "The current possible types are: -" +
            "    CLASSIFICATION,\n" +
            "    REGRESSION,\n" +
            "    CLUSTERING,\n" +
            "    CLASSIFICATION_REGRESSION,\n" +
            "    CLASSIFICATION_CLUSTERING,\n" +
            "    REGRESSION_CLUSTERING ")
    public List<Procedure> getAllByType(@PathVariable("type") String type) {
        try {
            return Lists.newArrayList(service.getRepository().findAllByType(AlgorithmType.valueOf(type).name()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid type parameter");
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON, path = "/target/{target}")
    @ApiOperation(value = "View a list of all procedures by target. ", notes = "The current possible targets are: -" +
            " EVALUATION - NORMALIZATION - VALIDATION ")
    public List<Procedure> getAllByTarget(@PathVariable("target") String type) {
        try {
            return Lists.newArrayList(service.getRepository().findAllByTarget(ProcedureTarget.valueOf(type).name()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid type parameter");
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "View a list of trainings made")
    public List<Procedure> getAll() {

        return Lists.newArrayList(service.getRepository().findAll());

    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Procedure save(@RequestBody Procedure procedure){
        return service.getRepository().save(procedure);
    }
}
