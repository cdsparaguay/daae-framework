package daae.learner.rest;

import com.google.common.collect.Lists;
import daae.learner.models.Algorithm;
import daae.learner.repository.AlgorithmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/algorithms")
public class Algorithms {

    private final AlgorithmRepository repository;

    @Autowired
    public Algorithms(AlgorithmRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Algorithm> getAlgorithms(){
        return Lists.newArrayList(repository.findAll());
    }

}
