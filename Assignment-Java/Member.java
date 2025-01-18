import java.util.*;

public class Member implements Cloneable, Comparable<Member>{
    private String id;
    private String name;
    private Day joinDate;
    private ArrayList<Equipment> Borrowed_Eqp;
    private ArrayList<EquipmentSet> Requested_Eqp_sets;

    private Day Borrow_Date;
    private Day Return_Date;

    private Day Request_Start_Date;
    private Day Request_Return_Date;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.joinDate = SystemDate.getInstance().clone();
        Club.getInstance().addMember(this);
        Borrowed_Eqp = new ArrayList<>();
        Requested_Eqp_sets = new ArrayList<>();
        }

    public static void list(ArrayList<Member> allMembers) {
        //Learn: "-" means left-aligned
        System.out.printf("%-5s%-9s%11s%11s%13s\n", "ID", "Name",
        "Join Date ", "#Borrowed", "#Requested");
        for (Member m: allMembers) {
        System.out.printf("%-5s%-9s%11s%7d%13d\n", m.id, m.name,
        m.joinDate, m.Borrowed_Eqp.size(), m.Requested_Eqp_sets.size());
        }
    }
    public static void list_member_status(ArrayList<Member> allMembers) {
        for (Member m: allMembers) {
            System.out.printf("[%s %s]\n", m.id, m.name);
            if (m.getAllBorrowedEqp().isEmpty() && m.getallRequestedEqpSet().isEmpty()) {
                System.out.println("No record.\n");
            }
            else {
                for (Equipment Eqp: m.getAllBorrowedEqp()) {
                    for (EquipmentSet Eset: Eqp.getallEquipmentSet()) {
                        if (Eset.getMember_Who_Borrowed() == null) {
                            continue;
                        }
                        if (Eset.getMember_Who_Borrowed().getMemberName().equals(m.getMemberName())) {
                            System.out.printf("- borrows %s (%s) for %s to %s\n", Eset.getlabel(), 
                            Eqp.getEquipmentName(), Eset.getMember_Who_Borrowed().getBorrowDate(), Eset.getMember_Who_Borrowed().getReturnDate());
                        }
                    }
                } 
                for (EquipmentSet Eset: m.getallRequestedEqpSet()) {
                    System.out.printf("- requests %s (%s) for %s to %s\n", Eset.getlabel(), Eset.getEquipmentName(), Eset.get_Eset_Request_Start_Date(), Eset.get_Eset_Request_Return_Date());
                }
                System.out.println();
            }
        }
    }

    public String getMemberID() {
        return id;
    }
    public String getMemberName() {
        return name;
    }
    public ArrayList<Equipment> getAllBorrowedEqp() {
        return Borrowed_Eqp;
    }
    public ArrayList<EquipmentSet> getallRequestedEqpSet() {
        return Requested_Eqp_sets;
    }
    public void set_Request_Dates( String start_date,  int period) {
        Request_Start_Date = new Day(start_date);
        Request_Return_Date = Request_Start_Date.clone().advanceDay(period);
    }

    public Day getBorrowDate() {return Borrow_Date;}
    public Day getReturnDate() {return Return_Date;}
    public Day getRequestStartDate() {return Request_Start_Date;}
    public Day getRequestReturnDate() {return Request_Return_Date;}
    public Day getjoinDate() {return joinDate;}

    public void addBorrowedEqp(Equipment E, int period) {
        Borrowed_Eqp.add(E);
        Collections.sort(Borrowed_Eqp);
        Borrow_Date = SystemDate.getInstance().clone();
        Return_Date = SystemDate.getInstance().clone().advanceDay(period);
    }
    public void removeBorrowedEqp(Equipment E) {
        Borrowed_Eqp.remove(E);
    }

    public void addRequestedEqpSet(EquipmentSet Eset, String Eqp_name, String start_date, int period) {
        Eset.setEquipmentName(Eqp_name);
        Eset.set_Eset_Request_Start_Date(new Day(start_date));
        Eset.set_Eset_Request_Return_Date(Eset.get_Eset_Request_Start_Date().clone().advanceDay(period));
        Requested_Eqp_sets.add(Eset);
        Collections.sort(Requested_Eqp_sets);
    }

    @Override
    public int compareTo(Member another) {
        if (this.id.equals(another.id)) return 0;
        else if (this.id.compareTo(another.id)>0)return 1;
        else return -1;
    }

    @Override
    public Member clone()
    {
        Member copy=null;
        try {
            copy = (Member) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    } 
}
