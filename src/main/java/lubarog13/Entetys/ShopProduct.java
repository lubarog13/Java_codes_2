package lubarog13.Entetys;

import lombok.Data;

import javax.swing.*;

@Data
public class ShopProduct {
    private int id;
    private String title;
    private String productType;
    private String articleNumber;
    private String description;
    private String imagePath;
    private int productionPersonCount;
    private int productionWorkshopNumber;
    private double minCostForAgent;
    private ImageIcon image;

    public ShopProduct(int id, String title, String productType, String articleNumber, String description, String imagePath, int productionPersonCount, int productionWorkshopNumber, double minCostForAgent) {
        this.id = id;
        this.title = title;
        this.productType = productType;
        this.articleNumber = articleNumber;
        this.description = description;
        this.imagePath = imagePath;
        this.productionPersonCount = productionPersonCount;
        this.productionWorkshopNumber = productionWorkshopNumber;
        this.minCostForAgent = minCostForAgent;
        try {
            image = new ImageIcon(ShopProduct.class.getClassLoader().getResource("book.jpg"));
        } catch (Exception e){

        }
    }
}
