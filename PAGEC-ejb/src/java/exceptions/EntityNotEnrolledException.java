package exceptions;

public class EntityNotEnrolledException extends Exception {

    public EntityNotEnrolledException() {
    }

    public EntityNotEnrolledException(String msg) {
        super(msg);
    }
}
