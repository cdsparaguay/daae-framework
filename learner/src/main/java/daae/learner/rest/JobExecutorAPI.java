package daae.learner.rest;

import daae.learner.service.JobExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobExecutorAPI {
    private final JobExecutorService sparkService;

    @Autowired
    public JobExecutorAPI(JobExecutorService sparkService) {
        this.sparkService = sparkService;
    }

    @RequestMapping(value = "/addJob", method = RequestMethod.GET)
    public void getWordsCount(){
        System.out.println(sparkService.addJob());
    }


}
