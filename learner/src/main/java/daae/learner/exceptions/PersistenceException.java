package daae.learner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PersistenceException  extends  Exception{

    public PersistenceException(String s) {
        super(s);
    }
}
