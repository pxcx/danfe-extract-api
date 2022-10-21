package br.com.ifsolucoescontabeis.danfe.error;

public class OperationFailed extends RuntimeException{
    public OperationFailed(String message) {
        super(message);
    }

    public OperationFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
