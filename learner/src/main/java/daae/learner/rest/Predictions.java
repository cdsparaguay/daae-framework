package daae.learner.rest;

import com.google.common.collect.Lists;
import daae.learner.models.Prediction;
import daae.learner.service.PredictionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/predictions")
@Api(value = "Predictions", description = "Read and create predictions")
public class Predictions {


    private final PredictionService service;

    @Autowired
    public Predictions(PredictionService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Prediction> getAll(){
        return Lists.newArrayList(service.getRepository().findAll());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON, value="/model/{modelId}")
    public List<Prediction> getByModel(@PathVariable(value = "modelId") Long modelId) {
        List<Prediction> predictions = service.getByModel(modelId);
        if (predictions == null || predictions.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return predictions;
    }
}
