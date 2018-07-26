package daae.learner.rest;

import com.google.common.collect.Lists;
import daae.learner.enums.AlgorithmType;
import daae.learner.exceptions.PersistenceException;
import daae.learner.models.Algorithm;
import daae.learner.models.AlgorithmParameter;
import daae.learner.repository.AlgorithmRepository;
import daae.learner.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/algorithms")
public class Algorithms {

    private final AlgorithmService service;

    @Autowired
    public Algorithms(AlgorithmService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Algorithm> getAlgorithms(){
        return Lists.newArrayList(service.getRepository().findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "type/{type}", params = "type", produces = MediaType.APPLICATION_JSON)
    public List<Algorithm> getAllByType(@PathVariable("type") String type){
        return Lists.newArrayList(service.getRepository().findAllByType(type));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Algorithm save(@RequestBody Algorithm algorithm) throws PersistenceException {

        return service.save(algorithm);

    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public Algorithm update(@RequestBody Algorithm algorithm){
        service.getRepository().findById(algorithm.getId()).orElseThrow(() -> new ResourceNotFoundException("Algorithm doesn't exists"));
        for(AlgorithmParameter parameter: algorithm.getParameters()) {
            parameter.setAlgorithm(algorithm);
        }
        return service.getRepository().save(algorithm);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{algorithmId}")
    public void delete(@PathVariable("algorithmId") Long algorithmId){
        Algorithm algorithm = service.getRepository().findById(algorithmId).orElseThrow(() -> new ResourceNotFoundException("Algorithm doesn't exists"));
        for(AlgorithmParameter parameter: algorithm.getParameters()) {
            parameter.setAlgorithm(algorithm);
        }
        service.getRepository().delete(algorithm);
    }

}
