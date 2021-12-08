package lubarog13.manager;

import lubarog13.Entetys.Material;
import lubarog13.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialManager {

    public static void createMaterial(Material material) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "INSERT INTO Material(Title, CountInPack, Unit, CountInStock, MinCount, Description, Cost, Image, MaterialType) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, material.getTitle());
            ps.setInt(2, material.getCountInPack());
            ps.setString(3, material.getUnit());
            ps.setDouble(4, material.getCountInStock());
            ps.setDouble(5, material.getMinCount());
            ps.setString(6, material.getDescription());
            ps.setDouble(7, material.getCost());
            ps.setString(8, material.getImagePath());
            ps.setString(9, material.getMaterialType());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if(resultSet.next()){
                material.setId(resultSet.getInt(1));
            }
        }
    }



    public static List<Material> getMaterials() throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "SELECT * FROM Material";
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<Material> materials = new ArrayList<>();
            while (rs.next()){
                materials.add(new Material(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getInt("CountInPack"),
                        rs.getString("Unit"),
                        rs.getInt("CountInStock"),
                        rs.getInt("MinCount"),
                        rs.getString("Description"),
                        rs.getDouble("Cost"),
                        rs.getString("Image"),
                        rs.getString("MaterialType")
                ));
            }
            return materials;
        }
    }

    public static Material getMaterialById(int id) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "SELECT * FROM Material WHERE ID=?";
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                return new Material(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getInt("CountInPack"),
                        rs.getString("Unit"),
                        rs.getInt("CountInStock"),
                        rs.getInt("MinCount"),
                        rs.getString("Description"),
                        rs.getDouble("Cost"),
                        rs.getString("Image"),
                        rs.getString("MaterialType")
                );
            }
            return null;
        }
    }

    public static void updateMaterial(Material material) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "UPDATE Material SET Title=?, CountInPack=?, Unit=?, CountInStock=?, MinCount=?, Description=?, Cost=?, Image=?, MaterialType=? WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, material.getTitle());
            ps.setInt(2, material.getCountInPack());
            ps.setString(3, material.getUnit());
            ps.setDouble(4, material.getCountInStock());
            ps.setDouble(5, material.getMinCount());
            ps.setString(6, material.getDescription());
            ps.setDouble(7, material.getCost());
            ps.setString(8, material.getImagePath());
            ps.setString(9, material.getMaterialType());
            ps.setInt(10, material.getId());
            ps.executeUpdate();
        }
    }

    public static void deleteMaterial(int id) throws SQLException{
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "DELETE FROM Material WHERE ID=?";
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

}
