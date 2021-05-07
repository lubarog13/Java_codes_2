package lubarog13.manager;

import lubarog13.ClientConnection;
import lubarog13.Entetys.Book;
import lubarog13.MySqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientManager {
    public static void getAll() throws SQLException
    {
        try(Connection c = ClientConnection.getConnection())
        {
            String sql = """
            SELECT ClientService.id, service.title, service.cost, client.firstname,  client.lastname, 
            client.patronymic, ClientService.StartTime
            FROM ClientService join client on Client.id = ClientID 
            join Service on Service.id=ServiceID
            """;
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);
            while(resultSet.next()) {
                System.out.println(resultSet.getInt("ClientService.id") + " "  +
                        resultSet.getString("service.title") + " "  +resultSet.getBigDecimal("service.cost")
                        + " "  +resultSet.getString("client.firstname")+ " "  +resultSet.getString("client.lastname")
                        + " "  +resultSet.getString("client.patronymic") + " "  +resultSet.getTimestamp("ClientService.StartTime"));
            }
        }
    }
    public static String insertClientService(String firstname, String lastname, String patronymic, String title, String comment) throws SQLException {
        try (Connection c = ClientConnection.getConnection()) {
            String sql = "Select ID from Client where firstname = ? and  lastname = ? and  patronymic = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, patronymic);
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next()) return "Запись клиента не существует";
            int ClientID = resultSet.getInt("ID");
            sql = "Select ID from Service where title = ?" ;
            ps = c.prepareStatement(sql);
            ps.setString(1, title);
            resultSet = ps.executeQuery();
            if(!resultSet.next()) return "Услуги не существует";
            int ServiceID = resultSet.getInt("ID");
            sql = "INSERT INTO ClientService(ClientID, ServiceID, StartTime, Comment) VALUES (?,?,?,?)";
            ps = c.prepareStatement(sql);
            ps.setInt(1, ClientID);
            ps.setInt(2, ServiceID);
            ps.setTimestamp(3, new Timestamp(new Date().getTime()));
            ps.setString(4, comment);
            ps.executeUpdate();
            return "Запись создана";
        }
    }
}
