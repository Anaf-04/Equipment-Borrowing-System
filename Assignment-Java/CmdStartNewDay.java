
public class CmdStartNewDay extends RecordedCommand{

    String date;
    @Override
	public void execute(String[] cmdParts)
	{
        try {
            if (cmdParts.length<2) {
                throw new ExInsufficientCmdArg();
            }
            else {
                int prev_registered_date = SystemDate.getInstance().String_date_to_int(Club.getInstance().getallDates().get(Club.getInstance().getallDates().size()-1));

                String[] sDayParts = cmdParts[1].split("-");
                if (sDayParts.length < 3) {
                    throw new ExInvalidDate();
                }
                if (!SystemDate.getInstance().getMonthName().contains(sDayParts[1])) {
                    throw new ExInvalidDate();
                }
                if (!SystemDate.getInstance().valid(Integer.parseInt(sDayParts[2]), (SystemDate.getInstance().getMonthName().indexOf(sDayParts[1])/3+1), Integer.parseInt(sDayParts[0])))
				    throw new ExInvalidDate(); 
  
                int recent_registered_date = SystemDate.getInstance().String_date_to_int(cmdParts[1]);

                if (prev_registered_date > recent_registered_date) {
                    throw new ExInvalidDateFormat();
                }
                SystemDate.getInstance().set(cmdParts[1]);
                date = cmdParts[1];
                Club.getInstance().getallDates().add(cmdParts[1]);
                addUndoCommand(this); 
                clearRedoList(); 

                System.out.println("Done.");
            }
            
            
        } catch (ExInsufficientCmdArg e) {
            System.out.println(e.getMessage());
        } catch (ExInvalidDateFormat e) {
            System.out.print(e.getMessage());
            System.out.print(SystemDate.getInstance().int_to_day(SystemDate.getInstance().String_date_to_int(Club.getInstance().getallDates().get(Club.getInstance().getallDates().size()-1))) + ".");
            System.out.println();
        } catch (ExInvalidDate e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid date.");
        }
	}

    @Override
    public void undoMe() {
        Club.getInstance().getallDates().remove(date);
        SystemDate.getInstance().set(Club.getInstance().getallDates().get(Club.getInstance().getallDates().size()-1));
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Club.getInstance().getallDates().add(date);
        SystemDate.getInstance().set(date);
        addUndoCommand(this);
    }
}
