package lubarog13.manager;

import lubarog13.Entetys.Book;
import lubarog13.MySqlConnection;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookManager {
    public static void createBook() throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String title = JOptionPane.showInputDialog("Введите название");
        String author = JOptionPane.showInputDialog("Введите автора");
        int pages = Integer.parseInt(JOptionPane.showInputDialog("Введите количество страниц"));
        Date create_date = null;
        try {
            create_date = format.parse(JOptionPane.showInputDialog("Введите создания дату (yyyy-MM-dd hh:mm:ss): "));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double rating = Double.parseDouble(JOptionPane.showInputDialog("Введите рейтинг"));

        int ageRating = Integer.parseInt(JOptionPane.showInputDialog("Введите возрастное ограничение"));
        Book book = new Book(1, title, author, pages, create_date, rating, ageRating);
        insert(book);
    }
    public static void updateBook(int k) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String title = JOptionPane.showInputDialog("Введите название");
        String author = JOptionPane.showInputDialog("Введите автора");
        int pages = Integer.parseInt(JOptionPane.showInputDialog("Введите количество страниц"));
        Date create_date = null;
        try {
            create_date = format.parse(JOptionPane.showInputDialog("Введите создания дату (yyyy-MM-dd hh:mm:ss): "));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double rating = Double.parseDouble(JOptionPane.showInputDialog("Введите рейтинг"));
        int ageRating = Integer.parseInt(JOptionPane.showInputDialog("Введите возрастное ограничение"));
        List<Book> books = getAll();
        for ( Book b: books ) {
            if(b.getId()==k){
                b.setTitle(title);
                b.setAuthor(author);
                b.setPages(pages);
                b.setRating(rating);
                b.setAgeRating(ageRating);
                update(b);
            }
        }
    }
    public static void createTable() throws SQLException
    {
        try(Connection c = MySqlConnection.getConnection())
        {
            /*String sql = "CREATE TABLE IF NOT EXISTS books (" +
                    "id INT(10) NOT NULL AUTO_INCREMENT," +
                    "title VARCHAR(128) NOT NULL," +
                    "author VARCHAR(128) NOT NULL," +
                    "pages INT(5) NOT NULL," +
                    "PRIMARY KEY(id)" +
                    ")";*/

//            String sql = """
//                    CREATE TABLE IF NOT EXISTS books (
//                        id INT(10) NOT NULL AUTO_INCREMENT,
//                        title VARCHAR(128) NOT NULL,
//                        author VARCHAR(128) NOT NULL,
//                        pages INT(5) NOT NULL,
//                        create_date DATETIME NOT NULL,
//                        rating FLOAT(10) NOT NULL,
//                        age_rating INT(10) NOT NULL,
//                        PRIMARY KEY(id)
//                    );
//                    """;
//
//            Statement s = c.createStatement();
//
//            s.executeUpdate(sql);
        }
    }

    public static void dropTable() throws SQLException
    {
        try(Connection c = MySqlConnection.getConnection())
        {
            String sql = "DROP TABLE IF EXISTS books";
            Statement s = c.createStatement();
            s.executeUpdate(sql);
        }
    }

    public static void insert(Book book) throws SQLException
    {
        try(Connection c = MySqlConnection.getConnection())
        {
            String sql = "INSERT INTO books( title, author, pages, create_date, rating, age_rating) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPages());
            ps.setTimestamp(4, new Timestamp(book.getCreateDate().getTime()));
            ps.setDouble(5, book.getRating());
            ps.setInt(6, book.getAgeRating());

            ps.executeUpdate();
        }
    }
    public static Book getById(int id) throws SQLException
    {
        try(Connection c = MySqlConnection.getConnection())
        {
            String sql = "SELECT * FROM books WHERE id=?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                return new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("pages"),
                        resultSet.getDate("create_date"),
                        resultSet.getDouble("rating"),
                        resultSet.getInt("age_rating")
                );
            }

            return null;
        }
    }

    public static List<Book> getAll() throws SQLException
    {
        try(Connection c = MySqlConnection.getConnection())
        {
            String sql = "SELECT * FROM books";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<Book> list = new ArrayList<>();
            while(resultSet.next()) {
                list.add(new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("pages"),
                        resultSet.getDate("create_date"),
                        resultSet.getDouble("rating"),
                        resultSet.getInt("age_rating")
                ));
            }

            return list;
        }
    }

    public static void update(Book book) throws SQLException
    {
        try(Connection c = MySqlConnection.getConnection())
        {
            String sql = "UPDATE books SET title=?, author=?, pages=?, create_date= ?, rating=?, age_rating=? WHERE id=?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPages());
            ps.setTimestamp(4, new Timestamp(book.getCreateDate().getTime()));
            ps.setDouble(5, book.getRating());
            ps.setInt(6, book.getAgeRating());
            ps.setInt(7, book.getId());

            ps.executeUpdate();
        }
    }

    public static void deleteById(int id) throws SQLException
    {
        try(Connection c = MySqlConnection.getConnection())
        {
            String sql = "DELETE FROM books WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
