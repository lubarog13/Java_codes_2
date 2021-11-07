package lubarog13.Entetys;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String title;
    private double cost;
    private  int countInStock;
    private Date manufactureDateTime;
}
