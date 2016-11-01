import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by othscs015 on 9/7/2016.
 */
public class Dates implements Serializable
{
    private int dayNum;
    private int dayWeek;
    private ArrayList<String> events=new ArrayList<>();
    private boolean clicked=false;

    public Dates(int day, int dayWeek)
    {
        this.dayNum=day;
        this.dayWeek=dayWeek;

    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    public int getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(int dayWeek) {
        this.dayWeek = dayWeek;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    @Override
    public String toString() {
        return "Dates{" +
                "dayNum=" + dayNum +
                ", dayWeek=" + dayWeek +
                ", events=" + events +
                ", clicked=" + clicked +
                '}';
    }
    public Dates clone()
    {
        Dates clone=new Dates(getDayNum(),getDayWeek());
        clone.setClicked(false);
        clone.setEvents(getEvents());
        return clone;
        //return a copy so that I can change the dates arraylist without actually changing dates so that dates doesn't save clickeddate
    }
}
