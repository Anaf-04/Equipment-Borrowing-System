import java.util.ArrayList;
import java.util.Collections;


public class Club {
    private ArrayList<Member> allMembers;
    private ArrayList<Equipment> allEquipments;
    private ArrayList<String> allDates;

    private static Club instance = new Club();


    private Club() {
         allMembers = new ArrayList<>(); 
         allEquipments = new ArrayList<>();
         allDates = new ArrayList<>();
        }

    public static Club getInstance() { return instance; }

    //Dates
    public ArrayList<String> getallDates() {
        return allDates;
    }

    //Members
    public void addMember(Member m) {
        allMembers.add(m);
        Collections.sort(allMembers);
    }

    public void removeMember(Member m) {
        allMembers.remove(m);
    }
    
    public void listClubMembers() {
        Member.list(this.allMembers);
    }
    public void listClubMembersStatus() {
        Member.list_member_status(this.allMembers);
    }
    public ArrayList<Member> getallMembers() {
        return allMembers;
    }

    //Equipemts
    public void addEquipment(Equipment Eqp) {
        allEquipments.add(Eqp);
        Collections.sort(allEquipments);
    }

    public void removeEquipment(Equipment Eqp) {
        allEquipments.remove(Eqp);
    }
    public void listEquipments() {
        Equipment.list(this.allEquipments);
    }
    public void listEquipmentStatus() {
        Equipment.list_eqp_status(this.allEquipments);
    }
    public ArrayList<Equipment> getallEquipments() {
        return allEquipments;
    }
    



}
