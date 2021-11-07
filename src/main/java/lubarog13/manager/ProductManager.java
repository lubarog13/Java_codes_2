package lubarog13.manager;


import lubarog13.Entetys.Product;
import lubarog13.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {

    public static Product insertProduct(Product product) throws SQLException {
        try (Connection c = MySqlConnection.getConnection()){
            String sql = "insert into products(title, cost, countInStock, man_date) values (?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getTitle());
            ps.setDouble(2, product.getCost());
            ps.setInt(3, product.getCountInStock());
            ps.setTimestamp(4, new Timestamp(product.getManufactureDateTime().getTime()));
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                product.setId(keys.getInt(1));
                return product;
            }
            else return null;
        }
    }

    public static List<Product> getAll() throws SQLException {
        try (Connection c = MySqlConnection.getConnection()){
            String sql = "select * from products";
            Statement s = c.createStatement();
            List<Product> products = new ArrayList<>();
            ResultSet resultSet = s.executeQuery(sql);
            while (resultSet.next()){
                products.add(
                        new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("title"),
                                resultSet.getDouble("cost"),
                                resultSet.getInt("countInStock"),
                                resultSet.getTimestamp("man_date")
                                )
                );
            }
            return products;
        }
    }
    public static void deleteProduct(int id) throws SQLException {
        try (Connection c = MySqlConnection.getConnection()){
            String sql = "delete from products where id=?";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static void updateProduct(Product product) throws SQLException{
        try (Connection c = MySqlConnection.getConnection()){
            String sql = "update products set title=?, cost=?, countInStock=?, man_date=? where id=?";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getTitle());
            ps.setDouble(2, product.getCost());
            ps.setInt(3, product.getCountInStock());
            ps.setTimestamp(4, new Timestamp(product.getManufactureDateTime().getTime()));
            ps.setInt(5, product.getId());
            ps.executeUpdate();
        }
    }
}
