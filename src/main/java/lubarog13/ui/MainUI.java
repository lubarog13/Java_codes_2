package lubarog13.ui;

import lubarog13.BaseUI;

import javax.swing.*;

public class MainUI extends BaseUI {
    private JPanel mainPanel;
    private JButton allClientsButton;
    private JButton createClientsButton;

    public MainUI() {
        super("My application", 600, 400);
        setContentPane(mainPanel);
        allClientsButton.addActionListener(e -> showClients());
        createClientsButton.addActionListener(e -> createClient());
        setVisible(true);
    }

    private void showClients() {
        dispose();
        new AllClientUI();
    }

    private void createClient() {
        dispose();
        new ClientUI();
    }
}
