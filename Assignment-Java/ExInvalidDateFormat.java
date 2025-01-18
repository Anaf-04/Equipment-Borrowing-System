public class ExInvalidDateFormat extends Exception {
    public ExInvalidDateFormat() {super("Invalid new day.  The new day has to be later than the current date ");}
    public ExInvalidDateFormat(String message) {super(message);}
}
