package daae.learner.rest;

import com.google.common.collect.Lists;
import daae.learner.enums.AlgorithmType;
import daae.learner.models.Algorithm;
import daae.learner.models.AlgorithmParameter;
import daae.learner.repository.AlgorithmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/algorithms")
public class Algorithms {

    private final AlgorithmRepository repository;

    @Autowired
    public Algorithms(AlgorithmRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Algorithm> getAlgorithms(){
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "type/{type}", params = "type", produces = MediaType.APPLICATION_JSON)
    public List<Algorithm> getAllByType(@PathVariable("type") String type){
        return Lists.newArrayList(repository.findAllByType(type));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Algorithm save(@RequestBody Algorithm algorithm){
        try {
            AlgorithmType.valueOf(algorithm.getType()).name();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid type parameter");
        }
        for(AlgorithmParameter parameter: algorithm.getParameters()) {
            parameter.setAlgorithm(algorithm);
        }
        return repository.save(algorithm);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public Algorithm update(@RequestBody Algorithm algorithm){
        repository.findById(algorithm.getId()).orElseThrow(() -> new ResourceNotFoundException("Algorithm doesn't exists"));
        for(AlgorithmParameter parameter: algorithm.getParameters()) {
            parameter.setAlgorithm(algorithm);
        }
        return repository.save(algorithm);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{algorithmId}")
    public void delete(@PathVariable("algorithmId") Long algorithmId){
        Algorithm algorithm = repository.findById(algorithmId).orElseThrow(() -> new ResourceNotFoundException("Algorithm doesn't exists"));
        for(AlgorithmParameter parameter: algorithm.getParameters()) {
            parameter.setAlgorithm(algorithm);
        }
        repository.delete(algorithm);
    }

}
