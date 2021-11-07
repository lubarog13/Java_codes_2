package lubarog13;

import lubarog13.Entetys.Human;
import lubarog13.Entetys.Product;
import lubarog13.manager.ClientServiceManager;
import lubarog13.manager.HumanManager;
import lubarog13.manager.ProductManager;

import java.sql.SQLException;
import java.util.Date;

public class App {
    public static void main(String[] args){
        try {
            System.out.println(ClientServiceManager.selectUserServices("Варлам","Некрасов","Михайлович"));
            System.out.println(ClientServiceManager.selectClients("Замена ремня привода ГУР"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
