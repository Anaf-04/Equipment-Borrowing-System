


public class CmdBorrow extends RecordedCommand{

    private Member mem;
    private Equipment Eqp;
    private EquipmentSet Eqpset;
    private String[] temp_cmdparts;
    @Override
    public void execute(String[] cmdParts) {
        try {
            temp_cmdparts = cmdParts;
            if (cmdParts.length<3) {
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

            for (Equipment E: mem.getAllBorrowedEqp()) {
                if (E.getEquipmentCode().equals(Eqp.getEquipmentCode())) {
                    throw new ExEqpAlreadyBorrowed();
                }
            }
            if (cmdParts.length == 4) {
                if (Integer.parseInt(cmdParts[3]) < 1) {
                    throw new ExInsufficientPeriod();
                }
                
                boolean all_equipment_set_taken = false;
                for (EquipmentSet Es: Eqp.getallEquipmentSet()) {
                    all_equipment_set_taken = false;
                    for (Member m: Es.getAllMembersWhoRequested()) {
                        if (m.getRequestStartDate().compareTo(SystemDate.getInstance()) == 0 || m.getRequestReturnDate().compareTo(SystemDate.getInstance()) == 0 ||
                        m.getRequestStartDate().compareTo(SystemDate.getInstance().advanceDay(Integer.parseInt(cmdParts[3]))) == 0 ||
                        m.getRequestReturnDate().compareTo(SystemDate.getInstance().advanceDay(Integer.parseInt(cmdParts[3]))) == 0) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod("The period overlaps with a current period that the member requests the equipment.");
                            }
                            all_equipment_set_taken = true;
                            break;
                        }
                        if (m.getRequestStartDate().compareTo(SystemDate.getInstance()) == -1 && 
                        m.getRequestReturnDate().compareTo(SystemDate.getInstance()) == 1) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod("The period overlaps with a current period that the member requests the equipment.");
                            }
                            all_equipment_set_taken = true;
                            break;
                        }
                        if (m.getRequestStartDate().compareTo(SystemDate.getInstance().advanceDay(Integer.parseInt(cmdParts[3]))) == -1 && 
                        m.getRequestReturnDate().compareTo(SystemDate.getInstance().advanceDay(Integer.parseInt(cmdParts[3]))) == 1) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod("The period overlaps with a current period that the member requests the equipment.");
                            }
                            all_equipment_set_taken = true;
                            break;
                        }
                        if (m.getRequestStartDate().compareTo(SystemDate.getInstance()) == 1 && 
                        m.getRequestReturnDate().compareTo(SystemDate.getInstance().advanceDay(Integer.parseInt(cmdParts[3]))) == -1) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod("The period overlaps with a current period that the member requests the equipment.");
                            }
                            all_equipment_set_taken = true;
                            break;
                        }
                    }
                    if (all_equipment_set_taken) {
                        continue;
                    }
                }
                if (all_equipment_set_taken) {
                    throw new ExInsufficientEqpSet();
                }
                boolean EqpSet_available = false;
                for (EquipmentSet Eset: Eqp.getallEquipmentSet()) {
                    if (Eset.getavailability()) {
                        if(mem.getBorrowDate()!=null) {
                            mem = mem.clone();
                        }
                        Eset.setMember_Who_Borrowed(mem);
                        mem.addBorrowedEqp(Eqp, Integer.parseInt(cmdParts[3]));
                        Eqpset = Eset;
                        Eset.setavailability(false);
                        EqpSet_available = true;
                        break;
                    }
                }
                if (!EqpSet_available) {
                    throw new ExInsufficientEqpSet();
                }
            }
            else {
                boolean all_equipment_set_taken = false;
                for (EquipmentSet Es: Eqp.getallEquipmentSet()) {
                    all_equipment_set_taken = false;
                    for (Member m: Es.getAllMembersWhoRequested()) {
                        if (m.getRequestStartDate().compareTo(SystemDate.getInstance()) == 0 || m.getRequestReturnDate().compareTo(SystemDate.getInstance()) == 0 ||
                        m.getRequestStartDate().compareTo(SystemDate.getInstance().advanceDay(7)) == 0 ||
                        m.getRequestReturnDate().compareTo(SystemDate.getInstance().advanceDay(7)) == 0) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod("The period overlaps with a current period that the member requests the equipment.");
                            }
                            all_equipment_set_taken = true;
                            break;
                        }
                        if (m.getRequestStartDate().compareTo(SystemDate.getInstance()) == -1 && 
                        m.getRequestReturnDate().compareTo(SystemDate.getInstance()) == 1) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod("The period overlaps with a current period that the member requests the equipment.");
                            }
                            all_equipment_set_taken = true;
                            break;
                        }
                        if (m.getRequestStartDate().compareTo(SystemDate.getInstance().advanceDay(7)) == -1 && 
                        m.getRequestReturnDate().compareTo(SystemDate.getInstance().advanceDay(7)) == 1) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod("The period overlaps with a current period that the member requests the equipment.");
                            }
                            all_equipment_set_taken = true;
                            break;
                        }
                        if (m.getRequestStartDate().compareTo(SystemDate.getInstance()) == 1 && 
                        m.getRequestReturnDate().compareTo(SystemDate.getInstance().advanceDay(7)) == -1) {
                            if (mem.getMemberName().equals(m.getMemberName())) {
                                throw new ExOverlapPeriod("The period overlaps with a current period that the member requests the equipment.");
                            }
                            all_equipment_set_taken = true;
                            break;
                        }
                    }
                    if (all_equipment_set_taken) {
                        continue;
                    }
                }
                if (all_equipment_set_taken) {
                    throw new ExInsufficientEqpSet();
                }
                boolean EqpSet_available = false;
                for (EquipmentSet Eset: Eqp.getallEquipmentSet()) {
                    if (Eset.getavailability()) {
                        if(mem.getBorrowDate()!=null) {
                            mem = mem.clone();
                        }
                        Eset.setMember_Who_Borrowed(mem);
                        mem.addBorrowedEqp(Eqp, 7);
                        Eqpset = Eset;
                        Eset.setavailability(false);
                        EqpSet_available = true;
                        break;
                    }
                }
                if (!EqpSet_available) {
                    throw new ExInsufficientEqpSet();
                }
            }
            
            


            addUndoCommand(this); 
            clearRedoList(); 
    
            System.out.println(mem.getMemberID() +" "+ mem.getMemberName() +" borrows " 
            + Eqpset.getlabel() +" ("+ Eqp.getEquipmentName() + ") for " + mem.getBorrowDate()
             + " to " + mem.getReturnDate());

            System.out.println("Done.");
            

        } catch (ExInsufficientCmdArg e) {
            System.out.println(e.getMessage());
        } catch (ExMemberNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEquipmentCodeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExEqpAlreadyBorrowed e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientEqpSet e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientPeriod e) {
            System.out.println(e.getMessage());
        } catch (ExOverlapPeriod e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        mem.removeBorrowedEqp(Eqp);
        Eqpset.setavailability(true);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Eqpset.setMember_Who_Borrowed(mem);
        if (temp_cmdparts.length == 4) {
            mem.addBorrowedEqp(Eqp, Integer.parseInt(temp_cmdparts[3]));
        }
        else {
            mem.addBorrowedEqp(Eqp, 7);
        }
        Eqpset.setavailability(false);
        addUndoCommand(this);
    }
}
