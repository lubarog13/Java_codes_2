package for_exam;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {
    public static void showError(Component component, String message){
        JOptionPane.showMessageDialog(component, message,"Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(String message) {
        showError(null, message);
    }
    public static void showWarning(Component component, String message){
        JOptionPane.showMessageDialog(component, message,"Предупреждение", JOptionPane.WARNING_MESSAGE);
    }

    public static void showWarning(String message) {
        showWarning(null, message);
    }

    public static void showInfo(Component component, String message){
        JOptionPane.showMessageDialog(component, message,"Информация", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showInfo(String message) {
        showInfo(null, message);
    }

}
