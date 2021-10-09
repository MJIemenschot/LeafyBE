package com.example.xedd.exception;

public class FileStorageException extends RuntimeException{

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message,Throwable cause) {
        super(message,cause);
    }
}


//public class FileStorageException extends RuntimeException {
//
//    private static final long serialVersionUID = 1L;
//    private String msg;
//
//    public FileStorageException(String msg) {
//        this.msg = msg;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//}