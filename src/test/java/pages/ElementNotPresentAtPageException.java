package pages;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElementNotPresentAtPageException extends RuntimeException {
    public ElementNotPresentAtPageException(String message, Throwable cause) {
        log.error("Message: "+message);
        log.error("Cause: "+cause);
    }
}
