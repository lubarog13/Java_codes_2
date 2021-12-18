package lubarog13;

import lubarog13.manager.ClientManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientConnection {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/urok",
                "root",
                "1234"
        );
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        /*System.out.println("Введите Имя, Фамилию, Отчество");
        String firstname = scanner.next();
        String lastname = scanner.next();
        String patronymic = scanner.next();
        System.out.println("Введите услугу");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Добавьте комментарий");
        String comment = scanner.nextLine();
        System.out.println(title);*/
        String firstname = "Михаил";
        String lastname = "Баранов";
        String patronymic = "Романович";
        String title = "Замена свечей";
        String comment = "Как можно скорее";
//        try {
//            System.out.println(ClientManager.insertClientService(firstname, lastname, patronymic, title, comment));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        try {
//            ClientManager.getAll();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }
}
