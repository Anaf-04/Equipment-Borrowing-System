
import java.util.Collections;

public class CmdRegister extends RecordedCommand{

    private Member m;
    @Override
	public void execute(String[] cmdParts)
	{
        try {
            if (cmdParts.length<3) {
                throw new ExInsufficientCmdArg();
            }
            else {
                for (Member m: Club.getInstance().getallMembers()) {
                    if (m.getMemberID().equals(cmdParts[1])) {
                        throw new ExMemberIdInUse();
                    }
                }
                m = new Member(cmdParts[1], cmdParts[2]);
                
                addUndoCommand(this); 
                clearRedoList(); 
        
                System.out.println("Done.");
            }
        } catch (ExInsufficientCmdArg e) {
            System.out.println(e.getMessage());
        } catch (ExMemberIdInUse e) {
            System.out.print(e.getMessage());
            for (Member m: Club.getInstance().getallMembers()) {
                if (m.getMemberID().equals(cmdParts[1])) {
                    System.out.print(m.getMemberID()+" " + m.getMemberName());
                }
            }
            System.out.println();
        }
	}

    @Override
    public void undoMe() {
        Club.getInstance().removeMember(m);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Club.getInstance().addMember(m);
        Collections.sort(Club.getInstance().getallMembers());
        addUndoCommand(this);
    }
}
