public class ExOverlapPeriod extends Exception{
    public ExOverlapPeriod() {super("The period overlaps with a current period that the member borrows / requests the equipment.");}
    public ExOverlapPeriod(String message) {super(message);}
}
