package interpreter;

public class ForthException extends Exception {
    private static final long serialVersionUID = -2024112755657253171L;

    public ForthException(String message) {
        super(message);
    }
}
