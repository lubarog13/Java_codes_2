package lubarog13.ui;

import lubarog13.Entetys.Client;
import lubarog13.base.BaseForm;
import lubarog13.base.BaseFormTrain;
import lubarog13.manager.ClientManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class ClientUpdateForm extends BaseFormTrain {
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
    private JTextField idField;
    private JButton deleteButton;
    private Date regDate;

    public ClientUpdateForm(Client client) {
        super(800, 600);
        setContentPane(mainPanel);
        initBoxes();
        initFields(client);
        saveButton.addActionListener(actionEvent -> save());
        backButton.addActionListener(actionEvent -> goBack());
        deleteButton.addActionListener(actionEvent -> delete());
        setVisible(true);
    }

    private void initFields(Client client){
        idField.setText(String.valueOf(client.getId()));
        idField.setEditable(false);
        fnameField.setText(client.getFirstName());
        lnameField.setText(client.getLastName());
        patronynmicField.setText(client.getPatronymic());
        emailField.setText(client.getEmail());
        phoneField.setText(client.getPhone());
        this.regDate = client.getRegistrationDate();
        genderBox.setSelectedItem(client.getGenderCode());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(client.getBirthday());
        birthDayBox.setSelectedItem(calendar.get(Calendar.DAY_OF_MONTH));
        birthMonthBox.setSelectedIndex(calendar.get(Calendar.MONTH));
        birthYearField.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        imageField.setText(client.getPhotoPath());
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
            ClientManager.updateClient(new Client(Integer.parseInt(idField.getText()), fnameField.getText(), lnameField.getText(),
                    patronynmicField.getText(), calendar.getTime(), regDate, emailField.getText(), phoneField.getText(), genderBox.getItemAt(genderBox.getSelectedIndex()), imageField.getText()));
        } catch (SQLException e) {
            DialogUtil.showError("Ошибка обновления клиента");
            e.printStackTrace();
            return;
        }
        DialogUtil.showInfo("Успешно обновлено!");
        goBack();
    }

    private void delete() {
        if(JOptionPane.showConfirmDialog(this, "Вы точно хотите удалить эту запись?", "Подтверждение", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            try {
                ClientManager.deleteClient(Integer.parseInt(idField.getText()));
            } catch (SQLException e) {
                DialogUtil.showError("Ошибка удаления клиента");
                e.printStackTrace();
                return;
            }
            DialogUtil.showInfo("Успешно удален клиент");
            goBack();
        }
    }
    private void goBack() {
        dispose();
        new ClientTableForm();
    }
}
