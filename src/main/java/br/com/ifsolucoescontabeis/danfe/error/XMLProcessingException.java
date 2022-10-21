package br.com.ifsolucoescontabeis.danfe.error;

public class XMLProcessingException extends RuntimeException{
    public XMLProcessingException(String message) {
        super(message);
    }

    public XMLProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
