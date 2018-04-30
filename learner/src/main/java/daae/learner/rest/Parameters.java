package daae.learner.rest;

import com.google.common.collect.Lists;
import daae.learner.enums.ParameterType;
import daae.learner.models.Parameter;
import daae.learner.repository.ParameterRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/parameters")
@Api(value = "Parameters", description = "Read operations about the parameters of the system")
public class Parameters {
    private final ParameterRepository repository;

    @Autowired
    public Parameters(ParameterRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON, path = "/domain/{domain}")
    @ApiOperation(value = "View a list of all params by domain. ", notes = "The current possible domains are: -" +
            " ALGORITHM_TYPE - ALGORITHM_STATUS - PROCEDURE_TARGET - DATA_TYPE - TRAINING_STATUS ")
    public List<Parameter> getAllByDomain(@PathVariable("domain") String domain) {
        try {
            return Lists.newArrayList(repository.findAllByDomain(ParameterType.valueOf(domain).name()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid domain parameter");
        }
    }

}
