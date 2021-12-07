package com.example.department.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 7428051251365675318L;

    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
