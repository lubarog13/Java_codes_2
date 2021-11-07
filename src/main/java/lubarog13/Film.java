package lubarog13;

import java.util.Date;

public class Film {
    @TableAnnotation(description = "title", isPrimarykey = true)
    String title;
    Date date;
    boolean hasSeats;
    double cost;

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", hasSeats=" + hasSeats +
                ", cost=" + cost +
                '}';
    }

    public Film(String title, Date date, boolean hasSeats, double cost) {
        this.title = title;
        this.date = date;
        this.hasSeats = hasSeats;
        this.cost = cost;
    }
    public Film(){}
}
