package lubarog13;

import lubarog13.ui.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.*;

public class App {
    public static void main(String[] args){
        //try {
//            System.out.println(ClientServiceManager.selectUserServices("Варлам","Некрасов","Михайлович"));
//            System.out.println(ClientServiceManager.selectClients("Замена ремня привода ГУР"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
       try {
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
           e.printStackTrace();
       }
       changeKeys(new Font("Ubuntu", Font.PLAIN, 12));

        // new ClientTableUI();
        new ClientTableForm();
//        Random random = new Random();
//        int[] list = new int[10];
//        for (int i=0; i<10; i++) {
//            list[i] = random.nextInt();
//            System.out.print(list[i] + " ");
//        }System.out.println();
//        Sort sort = new Sort();
//        sort.sort(list, 0, list.length-1);
//        for (int i=0; i<10; i++) {
//            System.out.print(list[i] + " ");
//        }
        
    }
    
    
    
    public static void changeKeys(Font font){
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()){
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }
    
    
    
    
    
    
    
    
    










    public static void changeAllFonts(Font font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, font);
        }
    }
}
