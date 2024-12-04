package com.codingshuttle.springboot.archetech.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class APIError {
    public HttpStatus httpStatus;
    public String message;
}
