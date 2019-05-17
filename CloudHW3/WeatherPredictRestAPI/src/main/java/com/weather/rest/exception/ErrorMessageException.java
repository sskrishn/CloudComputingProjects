package com.weather.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ErrorMessageException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Text;
    

    public ErrorMessageException( String Text) {
        super(String.format(""+Text));
        this.Text = Text;
       
    }

    public String getText() {
        return Text;
    }

   
}
