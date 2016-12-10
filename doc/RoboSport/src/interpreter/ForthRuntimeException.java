package interpreter;

public class ForthRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 6490192688474021024L;
    
    public ForthRuntimeException(String mess) {
        super(mess);
    }
}
