
import java.util.Collections;

public class CmdArrive extends RecordedCommand{

    private EquipmentSet Eset;
    private Equipment E;
    @Override
	public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length<2) {
                throw new ExInsufficientCmdArg();
            }
            boolean Equipment_exist = false;
            for (Equipment Eqp: Club.getInstance().getallEquipments()) {
                if (cmdParts[1].equals(Eqp.getEquipmentCode())) {
                    Equipment_exist = true;
                }
            }
            if (!Equipment_exist) {
                throw new ExEquipmentCodeNotFound();
            }
            for (Equipment Eqp: Club.getInstance().getallEquipments()) {
                if (cmdParts[1].equals(Eqp.getEquipmentCode())) {
                    E = Eqp;
                    int allEquipmentSet = Eqp.getallEquipmentSet().size() + 1;
                    Eset = new EquipmentSet(Eqp.getEquipmentCode() + "_" + allEquipmentSet);
                    Eqp.getallEquipmentSet().add(Eset);
                    Collections.sort(Eqp.getallEquipmentSet());
                }
            }

            addUndoCommand(this); 
            clearRedoList(); 
        
            System.out.println("Done.");
            
        } catch (ExInsufficientCmdArg e) {
            System.out.println(e.getMessage());
        } catch (ExEquipmentCodeNotFound e) {
            System.out.println(e.getMessage() + cmdParts[1] + ".  Cannot mark this item arrival.");
        }
    }

    @Override
    public void undoMe() {
        E.getallEquipmentSet().remove(Eset);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        E.getallEquipmentSet().add(Eset);
        Collections.sort(E.getallEquipmentSet());
        addUndoCommand(this);
    }
}
