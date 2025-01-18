
import java.util.Collections;

public class CmdCreate extends RecordedCommand{

    Equipment E;
    @Override
	public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length<3) {
                throw new ExInsufficientCmdArg();
            }
            else {
                for (Equipment Eqp: Club.getInstance().getallEquipments()) {
                    if (cmdParts[1].equals(Eqp.getEquipmentCode())) {
                        throw new ExEquipmentCodeInUse();
                    }
                }
                E = new Equipment(cmdParts[1], cmdParts[2]);

                addUndoCommand(this); 
                clearRedoList(); 
        
                System.out.println("Done.");
            }
        } catch (ExInsufficientCmdArg e) {
            System.out.println(e.getMessage());
        } catch(ExEquipmentCodeInUse e) {
            System.out.print(e.getMessage());
            for (Equipment Eqp: Club.getInstance().getallEquipments()) {
                if (cmdParts[1].equals(Eqp.getEquipmentCode())) {
                    System.out.print(Eqp.getEquipmentCode() + " " + Eqp.getEquipmentName());
                }
            }
            System.out.println();
        }
    }

    @Override
    public void undoMe() {
        Club.getInstance().removeEquipment(E);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Club.getInstance().addEquipment(E);
        Collections.sort(Club.getInstance().getallEquipments());
        addUndoCommand(this);
    }
}
