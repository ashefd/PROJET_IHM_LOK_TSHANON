package Exception;

public class LocationDoesntExistException extends Exception{
    String message;

    public LocationDoesntExistException(){
        message = "The location you have given doesn't exist !";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
