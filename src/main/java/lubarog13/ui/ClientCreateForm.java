package lubarog13.ui;

import lubarog13.Entetys.Client;
import lubarog13.base.BaseForm;
import lubarog13.manager.ClientManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class ClientCreateForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField lnameField;
    private JTextField fnameField;
    private JTextField patronynmicField;
    private JTextField emailField;
    private JComboBox<Integer> birthDayBox;
    private JComboBox<String> birthMonthBox;
    private JTextField birthYearField;
    private JTextField phoneField;
    private JComboBox<String> genderBox;
    private JTextField imageField;
    private JButton saveButton;
    private JButton backButton;

    public ClientCreateForm() {
        super(800, 600);
        setContentPane(mainPanel);
        initBoxes();
        saveButton.addActionListener(actionEvent -> save());
        backButton.addActionListener(actionEvent -> goBack());
        setVisible(true);
    }

    private void initBoxes() {
        for(int i=1; i<=31; i++){
            birthDayBox.addItem(i);
        }
        birthMonthBox.addItem("января");
        birthMonthBox.addItem("февраля");
        birthMonthBox.addItem("марта");
        birthMonthBox.addItem("апреля");
        birthMonthBox.addItem("мая");
        birthMonthBox.addItem("июня");
        birthMonthBox.addItem("июла");
        birthMonthBox.addItem("августа");
        birthMonthBox.addItem("сентября");
        birthMonthBox.addItem("октября");
        birthMonthBox.addItem("ноября");
        birthMonthBox.addItem("декабря");
        genderBox.addItem("ж");
        genderBox.addItem("м");
    }


    private void save() {
        if(fnameField.getText().length()==0 || lnameField.getText().length()==0 || patronynmicField.getText().length()==0 ||
        birthYearField.getText().length()==0 || emailField.getText().length()==0 || phoneField.getText().length()==0) {
            DialogUtil.showError("Заполните все поля");
            return;
        }

        if(fnameField.getText().length()>50 || lnameField.getText().length()>50 || patronynmicField.getText().length()>50){
            DialogUtil.showError("Длина полей имени - не более 50 символов");
        }

        int year = 0;
        try {
            year = Integer.parseInt(birthYearField.getText());
        } catch (NumberFormatException e) {
            DialogUtil.showError("Неверно введен год");
            return;
        }

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.set(year, birthMonthBox.getSelectedIndex(), birthDayBox.getItemAt(birthDayBox.getSelectedIndex()));
        } catch (Exception e){
            DialogUtil.showError("Неверная дата");
            return;
        }
        try {
            ClientManager.createClient(new Client(0, fnameField.getText(), lnameField.getText(),
                    patronynmicField.getText(), calendar.getTime(), new Date(), emailField.getText(), phoneField.getText(), genderBox.getItemAt(genderBox.getSelectedIndex()), imageField.getText()));
        } catch (SQLException e) {
            DialogUtil.showError("Ошибка сохранения клиента");
            e.printStackTrace();
            return;
        }
        DialogUtil.showInfo("Успешно создано!");
        goBack();
    }
    private void goBack() {
        dispose();
        new ClientTableForm();
    }
}
