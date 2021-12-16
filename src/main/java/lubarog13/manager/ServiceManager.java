package lubarog13.manager;

import lubarog13.Entetys.Service;
import lubarog13.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//ID, Title, Cost, DurationInSeconds, Description, Discount, MainImagePath
public class ServiceManager {
    public static void createService(Service service) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql="insert into Service(Title, Cost, DurationInSeconds, Description, Discount, MainImagePath) values (?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getTitle());
            ps.setDouble(2, service.getCost());
            ps.setInt(3, service.getDuration());
            ps.setString(4, service.getDescription());
            ps.setDouble(5, service.getDiscount());
            ps.setString(6, service.getImagePath());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if(resultSet.next()){
                service.setId(resultSet.getInt(1));
            }
        }
    }

    public static List<Service> getAll() throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "select * from Service";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);
            List<Service> services = new ArrayList<>();
            while (resultSet.next()){
                services.add(new Service(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7)
                ));
            }
            return services;
        }
    }

    public static void update(Service service)  throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "update Service set Title=?, Cost=?, DurationInSeconds=?, Description=?, Discount=?, MainImagePath=? where ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, service.getTitle());
            ps.setDouble(2, service.getCost());
            ps.setInt(3, service.getDuration());
            ps.setString(4, service.getDescription());
            ps.setDouble(5, service.getDiscount());
            ps.setString(6, service.getImagePath());
            ps.setInt(7, service.getId());
            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql ="delete from Service where ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
