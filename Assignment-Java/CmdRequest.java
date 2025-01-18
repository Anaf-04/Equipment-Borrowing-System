
import java.util.Collections;



public class CmdRequest extends RecordedCommand{

    private Member mem;
    private Equipment Eqp;
    private EquipmentSet Eset;
    private Day day;
    private int advanceday_int;
    private EquipmentSet temp_Eset;
    private Member temp_Mem;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 5) {
                throw new ExInsufficientCmdArg();
            }
            boolean MemberFound = false;
            for (Member m: Club.getInstance().getallMembers()) {
                if (m.getMemberID().equals(cmdParts[1])) {
                    MemberFound = true;
                    mem = m;
                }
            }
            if (!MemberFound) {
                throw new ExMemberNotFound();
            }
            boolean EquipmentFound = false;
            for (Equipment E: Club.getInstance().getallEquipments()) {
                if (E.getEquipmentCode().equals(cmdParts[2])) {
                    EquipmentFound = true;
                    Eqp = E;
                    if (E.getallEquipmentSet().isEmpty()) {
                        throw new ExInsufficientEqpSet();
                    }
                }
            }
            if (!EquipmentFound) {
                throw new ExEquipmentCodeNotFound("Equipment record not found.");
            }
            String[] sDayParts = cmdParts[3].split("-");
            if (sDayParts.length < 3) {
                throw new ExInvalidDate();
            }
            if (!SystemDate.getInstance().getMonthName().contains(sDayParts[1])) {
                throw new ExInvalidDate();
            }
            if (!SystemDate.getInstance().valid(Integer.parseInt(sDayParts[2]), (SystemDate.getInstance().getMonthName().indexOf(sDayParts[1])/3+1), Integer.parseInt(sDayParts[0])))
                throw new ExInvalidDate(); 

            if (SystemDate.getInstance().compareTo(new Day(cmdParts[3])) >0) {
                throw new ExInvalidDate();
            }

            char[] charArray = cmdParts[4].toCharArray();
            for (char c: charArray) {
                if (c<42 || c>57) {
                    throw new ExInvalidDate("Please provide an integer for the number of days.");
                }
            }

            if (Integer.parseInt(cmdParts[4])<1) {
                throw new ExInsufficientPeriod();
            }

            boolean EquipmentSet_taken = false;
            for (EquipmentSet Es: Eqp.getallEquipmentSet()) {
                EquipmentSet_taken = false;
                boolean do_the_computation = false;

                if (Es.getMember_Who_Borrowed() == null) {
                    if (Es.getAllMembersWhoRequested().isEmpty()) {
                        Eset = Es;
                        mem.addRequestedEqpSet(Eset, Eqp.getEquipmentName(),cmdParts[3], Integer.parseInt(cmdParts[4]));
                        if (mem.getRequestStartDate() != null) {
                            mem = mem.clone();
                        }
                        mem.set_Request_Dates(cmdParts[3], Integer.parseInt(cmdParts[4]));
                        Eset.getAllMembersWhoRequested().add(mem);
                        Eset.getAllStartDatesOfRequest().add(new Day(cmdParts[3]));
                        day = new Day(cmdParts[3]);
                        advanceday_int = Integer.parseInt(cmdParts[4]);
                        break;
                    }
                    else {
                        do_the_computation = true;
                    }
                }
                else if (Es.getMember_Who_Borrowed().getBorrowDate().compareTo(new Day(cmdParts[3])) == 0 || 
                Es.getMember_Who_Borrowed().getReturnDate().compareTo(new Day(cmdParts[3])) == 0 || 
                Es.getMember_Who_Borrowed().getBorrowDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == 0 ||
                Es.getMember_Who_Borrowed().getReturnDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == 0) {
                    if (mem.getMemberName().equals(Es.getMember_Who_Borrowed().getMemberName())){
                        throw new ExOverlapPeriod();
                    }
                    EquipmentSet_taken = true;
                    continue;
                }
                else if (Es.getMember_Who_Borrowed().getBorrowDate().compareTo(new Day(cmdParts[3])) == -1 &&
                    Es.getMember_Who_Borrowed().getReturnDate().compareTo(new Day(cmdParts[3])) == 1) {
                    if (mem.getMemberName().equals(Es.getMember_Who_Borrowed().getMemberName())) {
                        throw new ExOverlapPeriod();
                    }
                    EquipmentSet_taken = true;
                    continue;
                }
                else if ((Es.getMember_Who_Borrowed().getBorrowDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == -1 && 
                Es.getMember_Who_Borrowed().getReturnDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == 1)) {
                    if (mem.getMemberName().equals(Es.getMember_Who_Borrowed().getMemberName())) {
                        throw new ExOverlapPeriod();
                    }
                    EquipmentSet_taken = true;
                    continue;
                }
                else if (Es.getMember_Who_Borrowed().getBorrowDate().compareTo(new Day(cmdParts[3])) == 1 &&
                Es.getMember_Who_Borrowed().getReturnDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == -1) {
                    if (mem.getMemberName().equals(Es.getMember_Who_Borrowed().getMemberName())) {
                        throw new ExOverlapPeriod();
                    }
                    EquipmentSet_taken = true;
                    continue;
                }
                else if (Es.getAllMembersWhoRequested().isEmpty()) {
                    Eset = Es;
                    mem.addRequestedEqpSet(Eset, Eqp.getEquipmentName(),cmdParts[3], Integer.parseInt(cmdParts[4]));
                    if (mem.getRequestStartDate() != null) {
                        mem = mem.clone();
                    }
                    mem.set_Request_Dates(cmdParts[3], Integer.parseInt(cmdParts[4]));
                    Eset.getAllMembersWhoRequested().add(mem);
                    Eset.getAllStartDatesOfRequest().add(new Day(cmdParts[3]));
                    day = new Day(cmdParts[3]);
                    advanceday_int = Integer.parseInt(cmdParts[4]);
                    break;
                }
                else {
                    do_the_computation = true;
                }

                if (do_the_computation) {
                    boolean can_add_members_for_request = false;
                    
                    for (Member m: Es.getAllMembersWhoRequested()) {
                        
                        if (m.getRequestStartDate().compareTo(new Day(cmdParts[3])) == 0 || m.getRequestReturnDate().compareTo(new Day(cmdParts[3])) == 0 || 
                        m.getRequestStartDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == 0 || 
                        m.getRequestReturnDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == 0) {
                            if (mem.getMemberName().equals(m.getMemberName())){
                                throw new ExOverlapPeriod();
                            }
                            EquipmentSet_taken = true;
                            break;
                        }
                        else if (m.getRequestStartDate().compareTo(new Day(cmdParts[3])) == -1 && 
                        m.getRequestReturnDate().compareTo(new Day(cmdParts[3])) == 1) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod();
                            }
                            EquipmentSet_taken = true;
                            break;
                        }
                        else if (m.getRequestStartDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == -1 && 
                        m.getRequestReturnDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == 1) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod();
                            }
                            EquipmentSet_taken = true;
                            break;
                        }
                        else if (m.getRequestStartDate().compareTo(new Day(cmdParts[3])) == 1 && 
                        m.getRequestReturnDate().compareTo(new Day(cmdParts[3]).advanceDay(Integer.parseInt(cmdParts[4]))) == -1) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod();
                            }
                            EquipmentSet_taken = true;
                            break;
                        } 
                        else {                  
                            can_add_members_for_request = true;
                        }
                    }
                    if (EquipmentSet_taken) {
                        continue;
                    }

                    Eset = Es;
                    for (EquipmentSet Eset1: mem.getallRequestedEqpSet()) {
                        if (Eset1.equals(Es)) {
                            temp_Eset = Es.clone();
                            break;
                        }
                    }
                    if (mem.getallRequestedEqpSet().isEmpty()) {
                        temp_Eset = Es.clone();
                    }
                    
                    if (can_add_members_for_request) {
                        // mem = mem.clone();
                        if (temp_Eset == null) {
                            mem.addRequestedEqpSet(Eset , Eqp.getEquipmentName(),cmdParts[3], Integer.parseInt(cmdParts[4]));
                        }
                        else {
                            mem.addRequestedEqpSet(temp_Eset , Eqp.getEquipmentName(),cmdParts[3], Integer.parseInt(cmdParts[4]));
                        }

                        if (mem.getRequestStartDate() != null) {
                            temp_Mem = mem.clone();
                            temp_Mem.set_Request_Dates(cmdParts[3], Integer.parseInt(cmdParts[4]));
                            Eset.getAllMembersWhoRequested().add(temp_Mem);
                        }
                        else {
                            mem = mem.clone();
                            mem.set_Request_Dates(cmdParts[3], Integer.parseInt(cmdParts[4]));
                            Eset.getAllMembersWhoRequested().add(mem);
                        }
                        
                        Eset.getAllStartDatesOfRequest().add(new Day(cmdParts[3]));
                        Collections.sort(Eset.getAllStartDatesOfRequest());
                        day = new Day(cmdParts[3]);
                        advanceday_int = Integer.parseInt(cmdParts[4]);
                        break;
                    } 
                }
            }
            if (EquipmentSet_taken) {
                throw new ExOverlapPeriod("There is no available set of this equipment for the command.");
            }

            addUndoCommand(this); 
            clearRedoList(); 

            System.out.printf("%s %s requests %s (%s) for %s to %s\n", mem.getMemberID(),
             mem.getMemberName(), Eset.getlabel(), Eqp.getEquipmentName(), day.toString(),
              day.advanceDay(advanceday_int).toString());

            System.out.println("Done.");

            


        } catch (ExInsufficientCmdArg e) {
            System.out.println(e.getMessage());
        } catch (ExMemberNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientEqpSet e) {
            System.out.println(e.getMessage());
        } catch(ExEquipmentCodeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExInvalidDate e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid date.");
        } catch (ExInsufficientPeriod e) {
            System.out.println(e.getMessage());
        } catch (ExOverlapPeriod e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Eset.getAllMembersWhoRequested().remove(Eset.getAllMembersWhoRequested().size()-1);
        mem.getallRequestedEqpSet().remove(mem.getallRequestedEqpSet().size()-1);
        Eset.getAllStartDatesOfRequest().remove(Eset.getAllStartDatesOfRequest().size()-1);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        if (temp_Eset == null) {
            mem.addRequestedEqpSet(Eset , Eqp.getEquipmentName(),day.toString(), advanceday_int); 
        }
        else {
            mem.addRequestedEqpSet(temp_Eset , Eqp.getEquipmentName(),day.toString(), advanceday_int);
        }
        if (mem.getRequestStartDate() != null) {
            temp_Mem = mem.clone();
            temp_Mem.set_Request_Dates(day.toString(), advanceday_int);
            Eset.getAllMembersWhoRequested().add(temp_Mem);
        }
        else {
            mem = mem.clone();
            mem.set_Request_Dates(day.toString(), advanceday_int);
            Eset.getAllMembersWhoRequested().add(mem);
        }

        Eset.getAllStartDatesOfRequest().add(day);
        Collections.sort(Eset.getAllStartDatesOfRequest());
        addUndoCommand(this);
    }
}
