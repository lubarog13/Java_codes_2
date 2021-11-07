package lubarog13.manager;

import lubarog13.Entetys.Book;
import lubarog13.Entetys.Human;
import lubarog13.MySqlConnection;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class HumanManager {
    public static void createHuman() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        String fio = scanner.nextLine();
        int year = Integer.parseInt(scanner.nextLine());
        String genderString = scanner.nextLine();
        double rating = Double.parseDouble(scanner.nextLine());
        Human human = new Human(id, fio, year, genderString.charAt(genderString.length()-1), rating);
        insertHuman(human);
    }
    private static void insertHuman(Human human) throws SQLException{
        try(Connection c = MySqlConnection.getConnection()) {
            String sql = "INSERT INTO humans(fio, year_of_birth, gender, rating) VALUES (?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, human.getFio());
            ps.setInt(2, human.getYearOfBirth());
            System.out.println(String.valueOf(human.getGender()));
            ps.setString(3, String.valueOf(human.getGender()));
            ps.setDouble(4, human.getRating());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                human.setId(keys.getInt(1));
                return;
            }
            throw new SQLException("Запись не создана");
        }
    }

    public static Human selectById(int id) throws SQLException{
        try(Connection c= MySqlConnection.getConnection()){
            String sql = "SELECT * FROM humans where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                return new Human(
                        resultSet.getInt("id"),
                        resultSet.getString("fio"),
                        resultSet.getInt("year_of_birth"),
                        resultSet.getString("gender").charAt(0),
                        resultSet.getDouble("rating")
                );
            }
            return null;
        }
    }
    public static List<Human> getAll() throws SQLException{
        try(Connection c= MySqlConnection.getConnection()){
            String sql = "SELECT * FROM humans";
            Statement ps = c.createStatement();
            List<Human> humans = new ArrayList<>();
            ResultSet resultSet = ps.executeQuery(sql);
            while (resultSet.next()){
                 humans.add(new Human(
                        resultSet.getInt("id"),
                        resultSet.getString("fio"),
                        resultSet.getInt("year_of_birth"),
                        resultSet.getString("gender").charAt(0),
                        resultSet.getDouble("rating")
                ));
            }
            return humans;
        }
    }
    public static List<Human> selectByDate(int date) throws SQLException{
        try(Connection c= MySqlConnection.getConnection()) {
            String sql = "SELECT * FROM humans where year_of_birth=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, date);
            ResultSet resultSet = ps.executeQuery();
            List<Human> humans = new ArrayList<>();
            while (resultSet.next()){
                humans.add(new Human(
                        resultSet.getInt("id"),
                        resultSet.getString("fio"),
                        resultSet.getInt("year_of_birth"),
                        resultSet.getString("gender").charAt(0),
                        resultSet.getDouble("rating")
                ));
            }
            return humans;
        }
    }

    public static void update(Human human) throws SQLException{
        try (Connection c = MySqlConnection.getConnection()){
            String sql = "Update humans set fio=?, year_of_birth=?,gender=?,rating=? where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, human.getFio());
            ps.setInt(2, human.getYearOfBirth());
            ps.setString(3, String.valueOf(human.getGender()));
            ps.setDouble(4, human.getGender());
            ps.setInt(5, human.getId());
            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException{
        try(Connection c=MySqlConnection.getConnection()){
            String sql = "Delete from humans where id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }
}
