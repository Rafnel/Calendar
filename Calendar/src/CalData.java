import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by othscs015 on 9/21/2016.
 */
public class CalData implements Serializable
{
    private ArrayList<Dates> dates;

    public CalData(ArrayList<Dates> dates) {
        this.dates = dates;
    }

    public ArrayList<Dates> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Dates> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "CalData{" +
                "dates=" + dates +
                '}';
    }
}
