
import java.util.ArrayList;

public class EquipmentSet implements Cloneable, Comparable<EquipmentSet>{
    private boolean available;
    private String label;
    private Member Member_Who_Borrowed;
    private String Equipment_name;
    private ArrayList<Member> allMembers_who_requested;
    private ArrayList<Day> all_start_dates_request;

    private Day Eset_Request_Start_Date;
    private Day Eset_Request_Return_Date;

    public EquipmentSet(String label) {
        available = true;
        this.label = label;
        Member_Who_Borrowed = null;
        Equipment_name = null;
        Eset_Request_Start_Date = null;
        Eset_Request_Return_Date = null;
        all_start_dates_request = new ArrayList<>();
        allMembers_who_requested = new ArrayList<>();
    }

    public String getlabel() {return label;}
    public boolean getavailability() {return available;}
    public Member getMember_Who_Borrowed() {return Member_Who_Borrowed;}
    public String getEquipmentName() {return Equipment_name;}
    public Day get_Eset_Request_Start_Date() {return Eset_Request_Start_Date;}
    public Day get_Eset_Request_Return_Date() {return Eset_Request_Return_Date;}

    public void setEquipmentName(String name) {
        Equipment_name = name;
    }
    public void set_Eset_Request_Return_Date(Day d) {Eset_Request_Return_Date = d;}
    public void set_Eset_Request_Start_Date(Day d) {Eset_Request_Start_Date = d;}

    public void setMember_Who_Borrowed(Member mem) {
        Member_Who_Borrowed = mem;
    }
    public void setavailability(boolean availability) {
        available = availability;
    }
    public ArrayList<Day> getAllStartDatesOfRequest() {
        return all_start_dates_request;
    }
    public ArrayList<Member> getAllMembersWhoRequested() {
        return allMembers_who_requested;
    }

    @Override
    public int compareTo(EquipmentSet another) {
        if (this.label.equals(another.label)) return 0;
        else if (this.label.compareTo(another.label)>0)return 1;
        else return -1;
    }

    @Override
    public EquipmentSet clone()
    {
        EquipmentSet copy=null;
        try {
            copy = (EquipmentSet) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    } 

}
