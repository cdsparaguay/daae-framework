package daae.learner.rest;

import daae.learner.service.JobExecutorService;
import daee.learner.framework.dto.TrainerDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "Trainer", description = "Train prediction models")
@RequestMapping("/train")
public class JobExecutor {
    private final JobExecutorService sparkService;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobExecutor(JobExecutorService sparkService, @Qualifier("dataSource") DataSource dataSource) {
        this.sparkService = sparkService;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    public void addJob(@RequestBody TrainerDTO trainerDTO){
        System.out.println(sparkService.train(trainerDTO));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> getDataTMP() {
       return jdbcTemplate.queryForList("SELECT * FROM train_dt");
    }



}
