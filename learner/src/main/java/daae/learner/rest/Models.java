package daae.learner.rest;

import daae.learner.models.Model;
import daae.learner.service.ModelService;
import daee.learner.framework.dto.ModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/models")
public class Models {

    private final ModelService service;

    @Autowired
    public Models(ModelService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public Model add(@RequestBody ModelDTO modelDTO) {
        return service.saveDTOtoModel(modelDTO);
    }
}
