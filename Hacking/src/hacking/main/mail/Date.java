package hacking.main.mail;

public class Date{
    private int month, day, year;
    
    public Date(int m, int d, int y){
	this.month = m;
	this.day = d;
	this.year = y;
    }

    public int getMonth(){
        return month;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public int getDay(){
        return day;
    }

    public void setDay(int day){
        this.day = day;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }
    
    @Override
    public String toString(){
	return String.format("%2i/%2i/%2i", month, day, year);
    }
}
