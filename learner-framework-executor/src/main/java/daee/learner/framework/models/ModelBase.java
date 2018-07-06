package daee.learner.framework.models;

import daee.learner.framework.dto.ModelDTO;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

/**
 * Clase base a extender para crear un Modelo de Predicción
 * @param <T>
 */
public abstract class ModelBase<T> {
    Logger logger = Logger.getLogger(Model.class);

    T bytesToSparkModel(ModelDTO modelDTO) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(modelDTO.getModel());
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            return (T) Class.forName(getClass().getName()).cast(in.readObject());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }
}
