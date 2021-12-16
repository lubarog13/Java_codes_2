package lubarog13.Entetys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@NoArgsConstructor
public class Service {
    private int id;
    private String title;
    private double cost;
    private int duration;
    private String description;
    private double discount;
    private String imagePath;
    private ImageIcon image;

    public Service(int id, String title, double cost, int duration, String description, double discount, String imagePath) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.duration = duration;
        this.description = description;
        this.discount = discount;
        this.imagePath = imagePath;
        try{
            this.image = new ImageIcon(Service.class.getClassLoader().getResource("book.jpg"));
        } catch (Exception e) {

        }
    }
}
