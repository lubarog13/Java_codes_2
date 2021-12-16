package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.Entetys.Service;
import lubarog13.manager.ServiceManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ServiceTableCreateForm extends BaseUI {
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField imagePathField;
    private JSpinner costSpinner;
    private JSpinner durationSpinner;
    private JSpinner discountSpinner;
    private JButton saveButton;
    private JButton backButton;
    private JPanel mainPanel;

    public ServiceTableCreateForm() {
        super("Services", 800, 600);
        setContentPane(mainPanel);
        saveButton.addActionListener(e -> save());
        backButton.addActionListener(e -> goBack());
        setVisible(true);
    }

    private void save() {
        if(titleField.getText().length()==0 ||titleField.getText().length()>100){
            DialogUtil.showError("Слишком длинное название");
            return;
        }
        if(imagePathField.getText().length()>1000) {
            DialogUtil.showError("Слишком длинный путь к картинке");
            return;
        }
        double cost, discount;
        int duration;
        try {
            cost = Double.parseDouble(costSpinner.getValue().toString());
            discount = Double.parseDouble(discountSpinner.getValue().toString());
            duration = Integer.parseInt(durationSpinner.getValue().toString());
            try{
                ServiceManager.createService(new Service(0, titleField.getText(), cost, duration, descriptionField.getText(), discount, imagePathField.getText()));
            } catch (SQLException e) {
                DialogUtil.showError("Ошибка работы с бд");
                return;
            }
        } catch (Exception e){
            DialogUtil.showError("Ошибка ввода чисел");
            return;
        }
        DialogUtil.showInfo("Запись создана");
        goBack();
    }

    private void goBack() {
        dispose();
        new ServiceTableForm();
    }
}
