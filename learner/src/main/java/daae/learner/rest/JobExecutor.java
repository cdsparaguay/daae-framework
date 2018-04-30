package daae.learner.rest;

import daae.learner.service.JobExecutorService;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainerDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Trainer", description = "Train prediction models")
@RequestMapping("/train")
public class JobExecutor {
    private final JobExecutorService sparkService;

    @Autowired
    public JobExecutor(JobExecutorService sparkService) {
        this.sparkService = sparkService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addJob(@RequestBody TrainerDTO trainerDTO){
        System.out.println(sparkService.addJob(trainerDTO));
    }


}
