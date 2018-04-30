package daae.learner.service;

import daae.learner.models.Model;
import daae.learner.models.Prediction;
import daae.learner.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionService {

    private final PredictionRepository repository;

    private final ModelService modelService;

    @Autowired
    public PredictionService(PredictionRepository repository, ModelService modelService) {
        this.repository = repository;
        this.modelService = modelService;
    }

    public List<Prediction> getByModel(Long modelId) {
        Model model = modelService.getRepository().findById(modelId).orElse(null);
        if (model == null) {
            return null;
        } else {
            return repository.findAllByModel(model);
        }
    }

    public PredictionRepository getRepository() {
        return repository;
    }
}
