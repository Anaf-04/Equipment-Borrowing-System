

public class Day implements Cloneable, Comparable<Day>{
	
	private int year;
	private int month;
	private int day;

    private static final String MonthNames="JanFebMarAprMayJunJulAugSepOctNovDec";

	
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}
	
	// check if a given year is a leap year
	static public boolean isLeapYear(int y) {
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d) {
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

    public void set(String sDay) //Set year,month,day based on a string like 01-Mar-2024
    {
		String[] sDayParts = sDay.split("-");
		this.day =  Integer.parseInt(sDayParts[0]); //Apply Integer.parseInt for sDayParts[0];
		this.year = Integer.parseInt(sDayParts[2]);
		this.month = MonthNames.indexOf(sDayParts[1])/3+1; // Mar
    } 

	public int String_date_to_int(String sDay) {
		String[] sDayParts = sDay.split("-");
		String date = null;
		if (sDayParts[0].length() < 2) {
			sDayParts[0] = "0" + sDayParts[0];
		}
		if ((MonthNames.indexOf(sDayParts[1])/3+1)<10) {
			date = sDayParts[2] + "0" + (MonthNames.indexOf(sDayParts[1])/3+1) + sDayParts[0];
		}
		else {
			date = sDayParts[2] + (MonthNames.indexOf(sDayParts[1])/3+1) + sDayParts[0];
		}
		return Integer.parseInt(date);
	}

	public Day int_to_day(int d) {
		Day thisDay = new Day(d/10000, (d/100)%100 , d%100);
		return thisDay;
	}
    
    public Day(String sDay) {
        set(sDay);
    }

	public String getMonthName() {return MonthNames;}

	public Day advanceDay(int period) {
		Day startDay = this.clone();
		Day endDay = null;
		int temp_day = String_date_to_int(startDay.toString());

		for (int i=0; i<period; i++) {
			temp_day += 1;
			endDay = int_to_day(temp_day);
			if (!valid(endDay.year, endDay.month, endDay.day) && endDay.month<12) {
				endDay.month += 1;
				endDay.day = 1;
				temp_day = String_date_to_int(endDay.toString());
			}
			if (!valid(endDay.year, endDay.month, endDay.day) && endDay.month==12) {
				endDay.year += 1;
				endDay.month = 1;
				endDay.day = 1;
				temp_day = String_date_to_int(endDay.toString());
			}
		}
		return endDay;
	}

	// Return a string for the day like dd MMM yyyy
    @Override
	public String toString() {		
        return day+"-"+ MonthNames.substring((month-1)*3,(month)*3) + "-"+ year;
	}

    @Override
    public Day clone()
    {
        Day copy=null;
        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    } 

	@Override
    public int compareTo(Day another) {
		int thisDay = String_date_to_int(this.toString());
		int anotherDay = String_date_to_int(another.toString());
        if (thisDay == anotherDay) return 0;
        else if (thisDay > anotherDay)return 1;
        else return -1;
    }
}

