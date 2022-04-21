package pdp.uz.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    private final String resourceName;  //lovozim

    private final String resourceField; //name

    private final Object object; // User, Admin,1 ,5


}
