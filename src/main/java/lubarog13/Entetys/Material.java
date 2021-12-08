package lubarog13.Entetys;

import lombok.Data;

import javax.swing.*;

@Data
public class Material {
    private int id;
    private String title;
    private int countInPack;
    private String unit;
    private double countInStock;
    private double minCount;
    private String description;
    private double cost;
    private String imagePath;
    private String materialType;
    private ImageIcon image;

    public Material(int id, String title, int countInPack, String unit, double countInStock, double minCount, String description, double cost, String imagePath, String materialType) {
        this.id = id;
        this.title = title;
        this.countInPack = countInPack;
        this.unit = unit;
        this.countInStock = countInStock;
        this.minCount = minCount;
        this.description = description;
        this.cost = cost;
        this.imagePath = imagePath;
        this.materialType = materialType;
        try {
            this.image = new ImageIcon(Material.class.getClassLoader().getResource("book.jpg"));
        } catch (Exception e) {

        }
    }
}
