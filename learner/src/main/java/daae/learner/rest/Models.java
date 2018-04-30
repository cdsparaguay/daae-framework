package daae.learner.rest;

import com.google.common.collect.Lists;
import daae.learner.models.Algorithm;
import daae.learner.models.Model;
import daae.learner.service.ModelService;
import daee.learner.framework.dto.ModelDTO;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/models")
public class Models {

    private final ModelService service;


    @Autowired
    public Models(ModelService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Model add(@RequestBody ModelDTO modelDTO) {
        return service.saveDTOtoModel(modelDTO);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Model> getModels(){
        return Lists.newArrayList(service.getRepository().findAll());
    }
}
