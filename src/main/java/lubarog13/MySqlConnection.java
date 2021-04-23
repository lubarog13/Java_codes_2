package lubarog13;

import lubarog13.Entetys.Book;
import lubarog13.manager.BookManager;

import javax.swing.*;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.*;
import java.util.List;

public class MySqlConnection
{
    /*
    Book
    - int id (primary key)
    - String author
    - String title
    - int pages
    - double rating
    - int ageRating
    создать класс-сущности и набор запросов к базе для нее
    - создание и удаление таблицы
    - добавление записи
     */

    public static void main(String[] args)
    {
        try {
            BookManager.createTable();
            Scanner scanner = new Scanner(System.in);
            int k= 0, c=0;
            while (c!=6){
            System.out.println("Выберете действие:\n1.добавить книгу\n2.редактировать книгу\n3.получить книгу\n4.получить все книги\n5.удалить книгу\n6.выход");
            c = scanner.nextInt();
            switch (c){
                case 1: BookManager.createBook(); break;
                case 2: System.out.println("Введите id: ");
                        k = scanner.nextInt();
                        BookManager.updateBook(k);
                        break;
                case 3: System.out.println("Введите id: ");
                        k = scanner.nextInt();
                        Book book = BookManager.getById(k);

                        System.out.println(book);
                        break;
                case 4: List<Book> books = BookManager.getAll();
                        for(Book b: books) System.out.println(b);
                        break;
                case 5: System.out.println("Введите id: ");
                        k = scanner.nextInt();
                        BookManager.deleteById(k);
                        break;
                default: break;

            }}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/urok",
                "root",
                "1234"
        );
    }
}