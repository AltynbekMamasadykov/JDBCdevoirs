package peaksoft.exceptions;

public class SomethingWentWrongException extends RuntimeException{
    public SomethingWentWrongException(){

    }

    public SomethingWentWrongException(String message){
        super(message);
    }

}
