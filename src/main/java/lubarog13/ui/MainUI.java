package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.Entetys.Client;
import lubarog13.manager.ClientManager1;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class MainUI extends BaseUI {
    private JPanel mainPanel;
    private JButton allClientsButton;
    private JButton createClientsButton;
    private JButton editButton;

    public MainUI() {
        super("My application", 600, 400);
        setContentPane(mainPanel);
        allClientsButton.addActionListener(e -> showClients());
        createClientsButton.addActionListener(e -> createClient());
        editButton.addActionListener(e -> editClient());
        setVisible(true);
    }

    private void showClients() {
        new AllClientUI(this);
    }

    private void createClient() {
        new ClientUI();
    }

    private void editClient() {
        int id = -1;
        try {
            id = Integer.parseInt(JOptionPane.showInputDialog(this, "Введите id", "Ввод", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            DialogUtil.showError(this, "Id не введен или введен неверно");
            return;
        }

        Client client = null;
        try {
            client = ClientManager1.getClientById(id);
        } catch (SQLException throwables) {
            DialogUtil.showError(this, "Ошибка получения данных: " + throwables.getMessage());
            return;
        }
        if(client == null) {
            DialogUtil.showError(this, "Клиента с таким id не существует");
            return;
        }
        new EditClientUI(client);

    }
}
