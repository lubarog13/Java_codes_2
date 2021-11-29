package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.CustomTableModel;
import lubarog13.Entetys.Client;
import lubarog13.manager.ClientManager1;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ClientTableUI extends BaseUI {
    private JTable table;
    private JButton createButton;
    private JPanel mainPanel;

    private CustomTableModel<Client> model;

    public ClientTableUI() {
        super("My application", 800, 600);
        setContentPane(mainPanel);
        initTables();
        initButtons();
        setVisible(true);
    }
    private void initTables() {
        try {
            model = new CustomTableModel<>(
                    Client.class,
                    new String[] {"id", "Имя", "Фамилия", "Отчество", "Дата рождения", "Дата регистрации", "Email", "Телефон", "Пол", "Фотография"},
                    ClientManager1.getClients()
            );
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        createButton.addActionListener(e -> {
            dispose();
            new ClientUI();
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    dispose();
                    new EditClientUI(model.getRows().get(row));
                }
            }
        });
    }
}
