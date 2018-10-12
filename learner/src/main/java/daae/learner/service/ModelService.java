package daae.learner.service;

import daae.learner.models.*;
import daae.learner.repository.ModelRepository;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainingVariableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;

@Service
public class ModelService {

    private final ModelRepository repository;
    private final TrainingService trainingService;
    private final ProcedureService procedureService;

    @Autowired
    public ModelService(ModelRepository repository, TrainingService trainingService, ProcedureService procedureService) {
        this.repository = repository;
        this.trainingService = trainingService;
        this.procedureService = procedureService;
    }

    /**
     * Convert the {@link ModelDTO} to a {@link Model}, save it and update his corresponding {@link Training} with the
     * TrainingStatus.COMPLETE status and endDate.
     * @param toSave
     * @return
     */
    @Transactional
    public Model saveDTOtoModel(ModelDTO toSave) {

        if(toSave.getVariables() == null || toSave.getVariables().isEmpty() || toSave.getClass_name() == null
                || toSave.getClass_name().isEmpty() || toSave.getTraining_id() == null || toSave.getModel() == null) {
            throw new BadRequestException("None field can be null or empty");
        }
        Model newModel = new Model();
        newModel.setModel(toSave.getModel());
        newModel.setClassName(toSave.getClass_name());
        Training training = trainingService.getRepository().findById(toSave.getTraining_id())
                .orElseThrow(() -> new ResourceNotFoundException("Training doesn't exists"));
        trainingService.completeTraining(training);
        newModel.setTraining(training);
        newModel.setPredictions(Prediction.toPrediction(toSave.getPredicteds(), training, newModel,
                toSave.getInitialDate()));
        newModel.setModelVariables(new ArrayList<>());
        for (TrainingVariableDTO variableDTO: toSave.getVariables()) {
            TrainingVariable variable = new TrainingVariable();
            variable.setId(variableDTO.getId());
            newModel.getModelVariables().add(new ModelVariable(newModel, variable));
        }

        EvaluationValue evaluationValue = new EvaluationValue();
        evaluationValue.setModel(newModel);
        evaluationValue.setProcedure(procedureService.getRepository()
                .findByClassName(toSave.getEvaluationName()));
        evaluationValue.setValue(toSave.getEvaluationResult());

        newModel.setEvaluationValues(new ArrayList<>());
        newModel.getEvaluationValues().add(evaluationValue);

        return repository.save(newModel);

    }

    public ModelRepository getRepository() {
        return repository;
    }
}
