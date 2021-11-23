package lubarog13.manager;

import lubarog13.ClientConnection;
import lubarog13.Entetys.Client;
import lubarog13.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientManager1 {
    public static void insertClient(Client client) throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "Insert into Client(FirstName, LastName, Patronymic, Birthday, RegistrationDate, Email, Phone, GenderCode, PhotoPath) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
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
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()) {
                client.setId(keys.getInt(1));
            }
            System.out.println(client);
        }
    }

    public static List<Client> getClients() throws SQLException {
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "select * from Client";
            Statement s = c.createStatement();
            s.executeQuery(sql);
            ResultSet resultSet = s.getResultSet();
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()){
                clients.add(new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Patronymic"),
                        resultSet.getTimestamp("Birthday"),
                        resultSet.getTimestamp("RegistrationDate"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("GenderCode"),
                        resultSet.getString("PhotoPath")
                ));
            }
            return clients;
        }
    }

}
