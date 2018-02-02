package daae.learner.service;

import daae.learner.models.Model;
import daae.learner.models.ModelVariable;
import daae.learner.models.Training;
import daae.learner.models.TrainingVariable;
import daae.learner.repository.ModelRepository;
import daee.learner.framework.dto.ModelDTO;
import daee.learner.framework.dto.TrainingVariableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ModelService {

    private final ModelRepository repository;

    @Autowired
    public ModelService(ModelRepository repository) {
        this.repository = repository;
    }

    public Model saveDTOtoModel(ModelDTO toSave) {

        Model newModel = new Model();
        newModel.setModel(toSave.getModel());
        newModel.setClassName(toSave.getClass_name());
        Training training = new Training();
        training.setId(toSave.getTraining_id());
        newModel.setTraining(training);
        newModel.setModelVariables(new ArrayList<>());
        for (TrainingVariableDTO variableDTO: toSave.getVariables()) {
            TrainingVariable variable = new TrainingVariable();
            variable.setId(variableDTO.getId());
            newModel.getModelVariables().add(new ModelVariable(newModel, variable));
        }

        return repository.save(newModel);

    }
}
