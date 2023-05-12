package com.company.dao;

public class FilePersistenceException extends Exception {
    public FilePersistenceException(String message){
        super(message);
    }

    public FilePersistenceException(String message, Throwable cause){
        super(message, cause);
    }
}
