package lubarog13.ui;

import lubarog13.Entetys.Client;
import lubarog13.ExtendedTableModelWithDocs;
import lubarog13.base.BaseForm;
import lubarog13.base.BaseFormTrain;
import lubarog13.manager.ClientManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;

public class ClientTableForm extends BaseFormTrain {
    private JPanel mainPanel;
    private JTable table1;
    private JButton createButton;
    private JButton clearButton;
    private JComboBox<String> lNameBox;
    private JComboBox<String> yearBirthBox;
    private JButton idButton;
    private JButton registrationButton;
    private JTextField searchField;
    private boolean isIdSort = true;
    private boolean isRegSort;

    private ExtendedTableModelWithDocs<Client> model;

    public ClientTableForm() {
        super(800, 600);
        setContentPane(mainPanel);
        initTable();
        initBoxes();
        initButtons();
        setVisible(true);
    }

    private void initTable() {
        model = new ExtendedTableModelWithDocs<>(Client.class, new String[]{"ID", "Имя", "Фамилия", "Отчество", "День рождения", "Дата регистрации", "Email", "Телефон", "Пол", "Картирка"});
        try {
            model.setAllRows(ClientManager.getClients());
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError("Ошибка получения данных");
        }
        table1.setModel(model);
        table1.setRowHeight(100);
        model.getFilters()[0] = client -> {
            if(searchField.getText().length()==0) return true;
            String name = client.getFirstName() + " " + client.getPatronymic() + " " +client.getLastName();
            return name.contains(searchField.getText());
        };
        model.getFilters()[1] = client -> {
            if(lNameBox.getSelectedIndex()==0) return true;
            return client.getLastName().startsWith(lNameBox.getSelectedItem().toString());
        };
        model.getFilters()[2] = client -> {
            if(yearBirthBox.getSelectedIndex()==0) return true;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(client.getBirthday());
            return calendar.get(Calendar.YEAR) == Integer.parseInt(yearBirthBox.getItemAt(yearBirthBox.getSelectedIndex()));
        };
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    Client client = model.getFilteredRows().get(table1.rowAtPoint(e.getPoint()));
                    dispose();
                    new ClientUpdateForm(client);
                }
            }
        });
    }


    private void initBoxes() {
        lNameBox.addItem("Фильтр по фамилии:");
        for (char c='А'; c<='Я'; c++){
            lNameBox.addItem(String.valueOf(c));
        }
        lNameBox.addItemListener(e -> model.updateFilteredRows());
        yearBirthBox.addItem("Фильтр по году рожд.");
        List<Client> clients = model.getAllRows();
        Calendar calendar = Calendar.getInstance();
        Set<String> years = new HashSet<>();
        for (Client client: clients){
            calendar.setTime(client.getBirthday());
            years.add(String.valueOf(calendar.get(Calendar.YEAR)));
        }

        for(String year: years){
            yearBirthBox.addItem(year);
        }
        yearBirthBox.addItemListener(e -> model.updateFilteredRows());
    }

    private void initButtons() {
        idButton.addActionListener(e -> {
            if(isIdSort){
                model.setSorter((client, t1) -> Integer.compare(t1.getId(), client.getId()));
            } else {
                model.setSorter((client, t1) -> Integer.compare(client.getId(), t1.getId()));
            }
            isIdSort=!isIdSort;
            isRegSort=false;
            model.updateFilteredRows();
        });

        registrationButton.addActionListener(e -> {
            if(isRegSort){
                model.setSorter(((client, t1) -> t1.getRegistrationDate().compareTo(client.getRegistrationDate())));
            }else model.setSorter((client, t1) -> client.getRegistrationDate().compareTo(t1.getRegistrationDate()));
            isRegSort=!isRegSort;
            isIdSort=false;
            model.updateFilteredRows();
        });

        clearButton.addActionListener(e -> {
            isIdSort=true;
            isRegSort=false;
            model.setSorter(null);
            searchField.setText("");
            yearBirthBox.setSelectedIndex(0);
            lNameBox.setSelectedIndex(0);
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                model.updateFilteredRows();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                model.updateFilteredRows();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                model.updateFilteredRows();
            }
        });

        createButton.addActionListener(actionEvent -> {
            dispose();
            new ClientCreateForm();
        });
    }
}
