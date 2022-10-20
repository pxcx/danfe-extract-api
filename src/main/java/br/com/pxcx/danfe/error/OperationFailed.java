package br.com.pxcx.danfe.error;

public class OperationFailed extends RuntimeException{
    public OperationFailed(String message) {
        super(message);
    }

    public OperationFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
