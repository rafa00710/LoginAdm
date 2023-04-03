package com.api.microservice.services.execptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice

public class UserNotFoundException extends  RuntimeException{
}
