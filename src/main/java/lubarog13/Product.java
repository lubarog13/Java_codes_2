package lubarog13;
@TableAnnotation(description = "product")
public class Product {
    @TableAnnotation(description = "name", isPrimarykey = true)
    private String name;
    @TableAnnotation(description = "count")
    private int count;
    @TableAnnotation(description = "cost")
    private double cost;

    public Product(String name, int count, double cost) {
        this.name = name;
        this.count = count;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", cost=" + cost +
                '}';
    }
}
