package lubarog13.ui;

import lubarog13.BaseSubUI;
import lubarog13.BaseUI;
import lubarog13.Entetys.Client;
import lubarog13.manager.ClientManager1;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ClientUI extends BaseUI {
    private JTextField textFieldFName;
    private JTextField textFieldLName;
    private JTextField textFieldPatronymic;
    private JTextField textFieldEmail;
    private JTextField textFieldPhone;
    private JTextField textFieldGender;
    private JTextField textFieldPhoto;
    private JButton saveButton;
    private JPanel mainPanel;
    private JButton backButton;
    private JComboBox<Integer> dayBox;
    private JComboBox<String> monthBox;
    private JComboBox<Integer> yearBox;
    private JComboBox<Integer> dayRegBox;
    private JComboBox<String> monthRegBox;
    private JComboBox<Integer> yearRegBox;

    public ClientUI() {
        super("My application", 600, 500);
        setContentPane(mainPanel);
        initBoxes();
        saveButton.addActionListener(l -> {
            String first_name = textFieldFName.getText();
            String last_name = textFieldLName.getText();
            String patronymic = textFieldPatronymic.getText();
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(Calendar.YEAR, (int)yearBox.getSelectedItem());
            calendar.set(Calendar.MONTH, monthBox.getSelectedIndex());
            int days = (int)dayBox.getSelectedItem();
            if(days > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    DialogUtil.showError(this, "В этом месяце нет такого количества дней");
                    return;
                }
                calendar.set(Calendar.DAY_OF_MONTH, days);
            Date b_date = new Date();
            if(calendar.getTime().compareTo(b_date)>0) {
                DialogUtil.showError(this, "Дата рождения должна быть меньше текущей даты");
                return;
            }
             else   b_date = calendar.getTime();
            Date r_date = new Date();
            calendar.set(Calendar.YEAR, (int)yearRegBox.getSelectedItem());
            calendar.set(Calendar.MONTH, monthRegBox.getSelectedIndex());
            days = (int)dayRegBox.getSelectedItem();
            if(days > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                DialogUtil.showError(this, "В этом месяце нет такого количества дней");
                return;
            }
            calendar.set(Calendar.DAY_OF_MONTH, days);
            GregorianCalendar calendar1 = (GregorianCalendar) calendar.clone();
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
            if(calendar1.getTime().compareTo(r_date)>0) {
                DialogUtil.showError(this, "Дата регистрации должна быть меньше текущей даты");
                return;
            }
            else r_date = calendar.getTime();
            String email = textFieldEmail.getText();
            String phone = textFieldPhone.getText();
            String gender = textFieldGender.getText();
            if(!(gender.equals("Ж") || gender.equals("М"))){
                System.out.println("пол введен неверно");
                return;
            }

            String photoPath = textFieldPhoto.getText();
            try {
                ClientManager1.insertClient(new Client(0, first_name, last_name, patronymic, b_date, r_date, email, phone, gender, photoPath));
                dispose();
                new ClientTableUI();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        backButton.addActionListener(e -> {
            dispose();
            new ClientTableUI();
        });
        setVisible(true);
    }


    private void initBoxes() {
        for(int i=1; i<=31; i++) {
            dayBox.addItem(i);
            dayRegBox.addItem(i);
        }
        for(int i=1940; i<=2021; i++) {
            yearBox.addItem(i);
            yearRegBox.addItem(i);
        }

    }

}
