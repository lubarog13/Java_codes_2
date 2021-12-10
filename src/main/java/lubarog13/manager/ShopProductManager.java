package lubarog13.manager;

import lubarog13.Entetys.ShopProduct;
import lubarog13.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopProductManager {
    public static void createShopProduct(ShopProduct shopProduct) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "INSERT INTO Product(Title, ProductType, ArticleNumber, Description, Image, ProductionPersonCount, ProductionWorkshopNumber, MinCostForAgent) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, shopProduct.getTitle());
            ps.setString(2, shopProduct.getProductType());
            ps.setString(3, shopProduct.getArticleNumber());
            ps.setString(4, shopProduct.getDescription());
            ps.setString(5, shopProduct.getImagePath());
            ps.setInt(6, shopProduct.getProductionPersonCount());
            ps.setInt(7, shopProduct.getProductionWorkshopNumber());
            ps.setDouble(8, shopProduct.getMinCostForAgent());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                shopProduct.setId(keys.getInt(1));
            }
        }
    }

    public static List<ShopProduct> getAll() throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "select * from Product";
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<ShopProduct> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(new ShopProduct(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8),
                        resultSet.getInt(9)
                ));
            }
            return list;
        }
    }
    public static void updateShopProduct(ShopProduct shopProduct) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "UPDATE Product SET Title=?, ProductType=?, ArticleNumber=?, Description=?, Image=?, ProductionPersonCount=?, ProductionWorkshopNumber=?, MinCostForAgent=? WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, shopProduct.getTitle());
            ps.setString(2, shopProduct.getProductType());
            ps.setString(3, shopProduct.getArticleNumber());
            ps.setString(4, shopProduct.getDescription());
            ps.setString(5, shopProduct.getImagePath());
            ps.setInt(6, shopProduct.getProductionPersonCount());
            ps.setInt(7, shopProduct.getProductionWorkshopNumber());
            ps.setDouble(8, shopProduct.getMinCostForAgent());
            ps.setInt(9, shopProduct.getId());
            ps.executeUpdate();
        }
    }

    public static void deleteProduct(int id) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "DELETE FROM Product where ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static List<ShopProduct> searchProduct(String s) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "select * from Product where Title Like ? or ProductType like ? or Image Like ?";
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, '%' +s + '%');
            statement.setString(2, '%' +s + '%');
            statement.setString(3, '%' +s + '%');
            ResultSet resultSet = statement.executeQuery();
            List<ShopProduct> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(new ShopProduct(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getInt(8),
                        resultSet.getInt(9)
                ));
            }
            return list;
        }
    }
}
