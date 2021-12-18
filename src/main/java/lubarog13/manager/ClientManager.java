package lubarog13.manager;

import lubarog13.ClientConnection;
import lubarog13.Entetys.Book;
import lubarog13.Entetys.Client;
import lubarog13.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//ID, FirstName, LastName, Patronymic, Birthday, RegistrationDate, Email, Phone, GenderCode, PhotoPath
public class ClientManager {
    public static void createClient(Client client) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "insert into Client(FirstName, LastName, Patronymic, Birthday, RegistrationDate, Email, Phone, GenderCode, PhotoPath) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getPatronymic());
            ps.setTimestamp(4, new Timestamp(client.getBirthday().getTime()));
            ps.setTimestamp(5, new Timestamp(client.getRegistrationDate().getTime()));
            ps.setString(6, client.getEmail());
            ps.setString(7, client.getPhone());
            ps.setString(8, client.getGenderCode());
            ps.setString(9, client.getPhotoPath());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) client.setId(rs.getInt(1));
        }
    }

    public static List<Client> getClients() throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql ="select * from Client";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()){
                clients.add(new Client(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10)
                ));
            }
            return clients;
        }
    }

    public static void updateClient(Client client) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "update Client set FirstName=?, LastName=?, Patronymic=?, Birthday=?, RegistrationDate=?, Email=?, Phone=?, GenderCode=?, PhotoPath=? where ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getPatronymic());
            ps.setTimestamp(4, new Timestamp(client.getBirthday().getTime()));
            ps.setTimestamp(5, new Timestamp(client.getRegistrationDate().getTime()));
            ps.setString(6, client.getEmail());
            ps.setString(7, client.getPhone());
            ps.setString(8, client.getGenderCode());
            ps.setString(9, client.getPhotoPath());
            ps.setInt(10, client.getId());
            ps.executeUpdate();
        }
    }

    public static void deleteClient(int id) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "delete from Client where ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
