package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.CustomTableModel;
import lubarog13.Entetys.Client;
import lubarog13.manager.ClientManager1;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;

public class ClientTableUI extends BaseUI {
    private JTable table;
    private JButton createButton;
    private JPanel mainPanel;
    private JComboBox<String> birthDateComboBox;
    private JComboBox<String> lastNameComboBox;
    private JButton registrationDateSort;
    private JButton clearSettingsButton;
    private JButton idComboBox;
    private boolean idSort = true;
    private boolean dateSort = false;

    private CustomTableModel<Client> model;

    public ClientTableUI() {
        super("My application", 800, 600);
        setContentPane(mainPanel);
        initTables();
        initButtons();
        initBoxes();
        setVisible(true);
    }
    private void initTables() {
        try {
            model = new CustomTableModel<>(
                    Client.class,
                    new String[] {"id", "Имя", "Фамилия", "Отчество", "Дата рождения", "Дата регистрации", "Email", "Телефон", "Пол", "Фотография", "Изображение"},
                    ClientManager1.getClients()
            );
            table.setModel(model);
            table.setRowHeight(100);
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
        idComboBox.addActionListener(e ->{
            Collections.sort(model.getRows(), new Comparator<Client>() {
                @Override
                public int compare(Client o1, Client o2) {
                    if(idSort) {
                        return Integer.compare(o2.getId(), o1.getId());
                    } else {
                        return Integer.compare(o1.getId(), o2.getId());
                    }
                }
            });
                    idSort = !idSort;
                    dateSort = false;

                    model.fireTableDataChanged();
                });
        registrationDateSort.addActionListener(e -> {
            Collections.sort(model.getRows(), new Comparator<Client>() {
                @Override
                public int compare(Client o1, Client o2) {
                    if(dateSort) {
                        return o1.getRegistrationDate().compareTo(o2.getRegistrationDate());
                    }
                    else {
                        return o2.getRegistrationDate().compareTo(o1.getRegistrationDate());
                    }
                }
            });
            dateSort = !dateSort;
            idSort = false;
            model.fireTableDataChanged();
        });
        clearSettingsButton.addActionListener(e -> {
            birthDateComboBox.setSelectedIndex(0);
            lastNameComboBox.setSelectedIndex(0);
        });
    }

    private void initBoxes() {
        birthDateComboBox.addItem("Все даты рождения");
        birthDateComboBox.addItem("<1950");
        birthDateComboBox.addItem("1950-1970");
        birthDateComboBox.addItem("1971-1990");
        birthDateComboBox.addItem("1991-2010");
        birthDateComboBox.addItem(">2010");
        lastNameComboBox.addItem("Все фамилии");
        try {
            List<Client> clientList = ClientManager1.getClients();
            Set<String> names = new HashSet<>();
            for (Client client: clientList){
                names.add(client.getLastName().substring(0, 1));
            }
            for (String s: names) {
                lastNameComboBox.addItem(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            DialogUtil.showError(this, "Ошибка работы с бд");
        }

        lastNameComboBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });
        birthDateComboBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                applyFilters();
            }
        });
    }
    private void applyFilters() {
        try {
            List<Client> clients = ClientManager1.getClients();
            if(lastNameComboBox.getSelectedIndex()!=0) {
                clients.removeIf(client -> !client.getLastName().substring(0, 1).equals(lastNameComboBox.getSelectedItem()));
            }
                switch (birthDateComboBox.getSelectedIndex()) {
                    case 0:
                        break;
                    case 1 :
                        clients.removeIf(client -> client.getBirthday().compareTo(new GregorianCalendar(1950, Calendar.JANUARY, 1).getTime())>0);
                        break;
                    case 2 :
                        clients.removeIf(client -> client.getBirthday().compareTo(new GregorianCalendar(1950, Calendar.JANUARY, 1).getTime())>0 || client.getBirthday().compareTo(new GregorianCalendar(1971, Calendar.JANUARY, 1).getTime())>0);
                        break;
                    case 3:
                        clients.removeIf(client -> client.getBirthday().compareTo(new GregorianCalendar(1971, Calendar.JANUARY, 1).getTime())<0 || client.getBirthday().compareTo(new GregorianCalendar(1991, Calendar.JANUARY, 1).getTime())>0);
                        break;
                    case 4:
                        clients.removeIf(client -> client.getBirthday().compareTo(new GregorianCalendar(1991, GregorianCalendar.JANUARY, 1).getTime())<0 || client.getBirthday().compareTo(new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime())>0);
                        break;
                    case 5:
                        clients.removeIf(client -> client.getBirthday().compareTo(new GregorianCalendar(2011, Calendar.JANUARY, 1).getTime())<0);
                        break;
                    default:
                        break;
                }
            model.setRows(clients);
            model.fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError(this, "Ошибка работы с бд");
        }
    }
}
