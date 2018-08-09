package daae.learner.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import daae.learner.models.PredictionSchedule;
import daae.learner.service.PredictionScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final PredictionScheduleService service;

    @Autowired
    public ScheduledTasks(PredictionScheduleService service) {
        this.service = service;
    }


    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {

        service.executeScheduledPredictions();
        log.error("The time is now {}", dateFormat.format(new Date()));
    }
}