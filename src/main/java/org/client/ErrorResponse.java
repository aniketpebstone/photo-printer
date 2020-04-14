package org.client;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final Date timestamp;
    private String message;
    private List<String> details;
    public ErrorResponse(String message, List<String> details,Date timestamp) {        
		super();
		this.timestamp=timestamp;
        this.message = message;
        this.details = details;
    }
}
