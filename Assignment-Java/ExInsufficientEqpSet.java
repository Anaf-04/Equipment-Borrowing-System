public class ExInsufficientEqpSet extends Exception{
    public ExInsufficientEqpSet() { super("There is no available set of this equipment for the command.");}
    public ExInsufficientEqpSet(String message) {super(message);}
}
