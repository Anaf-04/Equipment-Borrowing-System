public class ExEquipmentCodeNotFound extends Exception{
    public ExEquipmentCodeNotFound() {super("Missing record for Equipment ");}
    public ExEquipmentCodeNotFound(String message) {super(message);}
}
