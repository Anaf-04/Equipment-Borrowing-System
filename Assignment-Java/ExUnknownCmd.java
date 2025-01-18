public class ExUnknownCmd extends Exception{
    public ExUnknownCmd() {super("Unknown command - ignored.");}
    public ExUnknownCmd(String message) {super(message);}
}
