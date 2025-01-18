public class ExInsufficientPeriod extends Exception {
    public ExInsufficientPeriod() {super("The number of days must be at least 1.");}
    public ExInsufficientPeriod(String message) {super(message);}
}
