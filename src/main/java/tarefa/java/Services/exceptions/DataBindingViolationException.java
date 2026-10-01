package tarefa.java.Services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataBindingViolationException extends RuntimeException{
            public DataBindingViolationException(String menssage){
                super(menssage);
            }
}
