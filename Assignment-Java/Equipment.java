import java.util.*;

public class Equipment implements Comparable<Equipment>{
    private String Equipment_code;
    private String Equipment_name;
    private ArrayList<EquipmentSet> allEquipmentSet;

    public Equipment(String code, String name) {
        Equipment_code = code;
        Equipment_name = name;
        allEquipmentSet = new ArrayList<>();
        Club.getInstance().addEquipment(this);
    }

    public String getEquipmentCode() {return Equipment_code;}

    public String getEquipmentName() {return Equipment_name;}

    public ArrayList<EquipmentSet> getallEquipmentSet() {
        return allEquipmentSet;
    }


    public static void list(ArrayList<Equipment> allEquipments) {
        //Learn: "-" means left-aligned
        System.out.printf("%-5s%-20s%5s\n", "Code", "Name",
        "#sets");
        for (Equipment E: allEquipments) {
            System.out.printf("%-5s%-20s%3s", E.getEquipmentCode(), E.getEquipmentName(), E.getallEquipmentSet().size());

            boolean borrow_to_print = false;
            //checks if borrowed and then prints
            for (int i=0; i< E.getallEquipmentSet().size(); i++) {
                if (E.getallEquipmentSet().get(i).getavailability()==false && i==0) {
                    System.out.printf("  (Borrowed set(s): %s(%s)" , E.getallEquipmentSet().get(i).getlabel() ,
                     E.getallEquipmentSet().get(i).getMember_Who_Borrowed().getMemberID());
                    borrow_to_print = true;
                }
                if (E.getallEquipmentSet().get(i).getavailability()==false && i>0) {
                    System.out.printf(", %s(%s)" , E.getallEquipmentSet().get(i).getlabel() ,
                     E.getallEquipmentSet().get(i).getMember_Who_Borrowed().getMemberID());
                }
            }
            if (borrow_to_print) {
                System.out.print(")");
            }
            System.out.println();
        }
    }

    public static void list_eqp_status(ArrayList<Equipment> allEquipments) {
        for (Equipment Eqp: allEquipments) {
            System.out.printf("[%s %s]\n", Eqp.getEquipmentCode(), Eqp.getEquipmentName());
            if (Eqp.getallEquipmentSet().isEmpty()) {
                System.out.println("  We do not have any sets for this equipment.");
            }
            else {
                for (EquipmentSet Eset: Eqp.getallEquipmentSet()) {
                    System.out.printf("  %s\n", Eset.getlabel());
                    if (Eset.getavailability()) {
                        System.out.println("    Current status: Available");
                    }
                    if (!Eset.getavailability()) {
                        System.out.printf("    Current status: %s %s borrows for %s to %s\n",
                         Eset.getMember_Who_Borrowed().getMemberID(), Eset.getMember_Who_Borrowed().getMemberName(),
                          Eset.getMember_Who_Borrowed().getBorrowDate(), Eset.getMember_Who_Borrowed().getReturnDate());
                    }
                    if (!Eset.getAllStartDatesOfRequest().isEmpty()) {
                        System.out.print("    Requested period(s):");
                        for (int i=0; i<Eset.getAllStartDatesOfRequest().size(); i++) {
                            // System.out.println("/"+Eset.getAllStartDatesOfRequest().get(i)+"// "+i);                                
                            for (Member m: Eset.getAllMembersWhoRequested()) {
                                
                                // System.out.println("/"+Eset.getAllStartDatesOfRequest().get(i)+" "+m.getRequestStartDate()+"/"+i);                                

                                if ((i== Eset.getAllStartDatesOfRequest().size()-1) && Eset.getAllStartDatesOfRequest().get(i).compareTo(m.getRequestStartDate()) == 0) {
                                    System.out.printf(" %s to %s", m.getRequestStartDate(), m.getRequestReturnDate());
                                }
                                if ((i < Eset.getAllStartDatesOfRequest().size()-1) && Eset.getAllStartDatesOfRequest().get(i).compareTo(m.getRequestStartDate()) == 0) {
                                    System.out.printf(" %s to %s,", m.getRequestStartDate(), m.getRequestReturnDate());
                                }
                            }
                        }
                        System.out.println();
                    }
                }
            }
            System.out.println();
        }
    }

    @Override
    public int compareTo(Equipment another) {
        if (this.Equipment_code.equals(another.Equipment_code)) return 0;
        else if (this.Equipment_code.compareTo(another.Equipment_code)>0)return 1;
        else return -1;
    }
}