package com.company.dao;

// Main Exception thrown in the whole project
public class FilePersistenceException extends Exception {
    public FilePersistenceException(String message){
        super(message);
    }

    public FilePersistenceException(String message, Throwable cause){
        super(message, cause);
    }
}
