public class ExInsufficientCmdArg extends Exception{
    public ExInsufficientCmdArg() {super("Insufficient command arguments.");}
    public ExInsufficientCmdArg(String message) {super(message);}
}
