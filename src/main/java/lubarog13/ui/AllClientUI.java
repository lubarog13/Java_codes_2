package lubarog13.ui;

import lubarog13.BaseSubUI;
import lubarog13.BaseUI;
import lubarog13.Entetys.Client;
import lubarog13.manager.ClientManager1;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class AllClientUI extends BaseSubUI<MainUI> {
    private JTextArea textArea;
    private JLabel clientLabel;
    private JPanel mainPanel;
    private JButton backButton;


    public AllClientUI(MainUI mainUI) {
        super(mainUI, 600, 400);
        setContentPane(mainPanel);
        showClients();
        backButton.addActionListener(e -> closeSubUI());
        setVisible(true);
    }

    private void showClients() {
        try {
            List<Client> clientList = ClientManager1.getClients();
            StringBuilder stringBuilder = new StringBuilder();
            for(Client client: clientList) {
                stringBuilder.append(client.toString()).append("\n");
            }
            textArea.setText(stringBuilder.toString());
            closeSubUI();
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError(this, "Ошибка работы с бд: " + e.getMessage());
        }
    }

}
