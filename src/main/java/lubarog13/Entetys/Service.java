package lubarog13.Entetys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    private int id;
    private String title;
    private double cost;
    private int duration;
    private String description;
    private double discount;
    private String image;
}
