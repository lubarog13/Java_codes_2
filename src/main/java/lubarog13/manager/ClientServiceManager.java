package lubarog13.manager;

import lubarog13.Entetys.Client;
import lubarog13.Entetys.Service;
import lubarog13.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientServiceManager {
    public static List<Service> selectUserServices(String firstName, String lastName, String patronymic) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = " select Service.* from Client left join ClientService on Client.ID=ClientID left join Service on Service.ID=ServiceID where FirstName=? and LastName=? and Patronymic=? ";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, patronymic);
            ResultSet rs = ps.executeQuery();
            List<Service> services = new ArrayList<>();
            while (rs.next()){
                services.add(new Service(rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getDouble("Cost"),
                        rs.getInt("DurationInSeconds"),
                        rs.getString("Description"),
                        rs.getDouble("Discount"),
                        rs.getString("MainImagePath")));
            }
            sql = "select sum(Cost) from Client left join ClientService on Client.ID=ClientID left join Service on Service.ID=ServiceID where FirstName=? and LastName=? and Patronymic=? ";
            ps = c.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, patronymic);
            rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("Общая сумма: " + rs.getDouble("sum(Cost)"));
            }

            return services;
        }
    }

    public static List<Client> selectClients(String serviceName) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = " Select Client.* from Service left join ClientService on Service.ID=ServiceID left join Client on Client.ID=ClientID where Service.Title=? ";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, serviceName);
            ResultSet rs = ps.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (rs.next()){
                clients.add(
                        new Client(
                                rs.getInt("ID"),
                                rs.getString("FirstName"),
                                rs.getString("LastName"),
                                rs.getString("Patronymic"),
                                rs.getTimestamp("Birthday"),
                                rs.getTimestamp("RegistrationDate"),
                                rs.getString("Email"),
                                rs.getString("Phone"),
                                rs.getString("GenderCode"),
                                rs.getString("PhotoPath")
                        )
                );
            }
            return clients;
        }
    }
}
