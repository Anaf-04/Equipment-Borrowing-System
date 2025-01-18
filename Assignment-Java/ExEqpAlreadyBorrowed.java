public class ExEqpAlreadyBorrowed extends Exception{
    public ExEqpAlreadyBorrowed() {super("The member is currently borrowing a set of this equipment. He/she cannot borrow one more at the same time.");}
    public ExEqpAlreadyBorrowed(String message) {super(message);}
}
