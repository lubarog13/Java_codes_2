package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.CustomTableModel;
import lubarog13.Entetys.Service;
import lubarog13.ExtendedTableModelWithDocs;
import lubarog13.manager.ServiceManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ServiceTableForm extends BaseUI {
    private JTable table;
    private JPanel mainPanel;
    private JButton createButton;
    private JComboBox<String> titleBox;
    private JComboBox<String> costBox;
    private JButton IdButton;
    private JButton durationButton;
    private JButton clearButton;
    private JTextField searchField;
    private ExtendedTableModelWithDocs<Service> model;


    public ServiceTableForm() {
        super("Services", 800, 600);
        setContentPane(mainPanel);
        initTable();
        initButtons();
        initBoxes();
        setVisible(true);
    }

    private void initTable() {
        model = new ExtendedTableModelWithDocs<>(Service.class, new String[]{"ID", "Title", "Cost", "DurationInSeconds", "Description", "Discount", "MainImagePath"});
        try {
            model.setAllRows(ServiceManager.getAll());
            model.getFilters()[0] = new Predicate<Service>() {
                @Override
                public boolean test(Service service) {
                    if(titleBox.getSelectedIndex()!=0) {
                        if(service.getTitle().startsWith(titleBox.getSelectedItem().toString())) return true;
                        else return false;
                    }
                    return true;
                }
            };
            model.getFilters()[1] = new Predicate<Service>() {
                @Override
                public boolean test(Service service) {
                    if (costBox.getSelectedIndex()!=0){
                        if(costBox.getSelectedIndex()==1 && service.getCost()<1000)
                            return true;
                        if(costBox.getSelectedIndex()==2 && service.getCost()<3000)
                            return true;
                        if(costBox.getSelectedIndex()==3 && service.getCost()<4000)
                            return true;
                        if(costBox.getSelectedIndex()==4 && service.getCost()<5000)
                            return true;
                        if(costBox.getSelectedIndex()==5 && service.getCost()<10000)
                            return true;
                        return false;
                    }
                    return true;
                }
            };
            model.getFilters()[2] = new Predicate<Service>() {
                @Override
                public boolean test(Service service) {
                    if(searchField.getText().length()==0)
                        return true;
                    return service.getTitle().contains(searchField.getText());
                }
            };
            table.setModel(model);
            table.setRowHeight(100);
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError("Ошибка работы с базой");
        }
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) {
                    Service service = model.getFilteredRows().get(table.rowAtPoint(e.getPoint()));
                    dispose();
                    new ServiceUpdateForm(service);
                }
            }
        });
    }

    private void initBoxes() {
        titleBox.addItem("");
        for (char c='А'; c<='Я'; c++ ){
            titleBox.addItem(String.valueOf(c));
        }
        costBox.addItem("");
        costBox.addItem("<1000");
        costBox.addItem("<3000");
        costBox.addItem("<4000");
        costBox.addItem("<5000");
        costBox.addItem("<10000");
        titleBox.addItemListener(e -> model.updateFilteredRows());
        costBox.addItemListener(e -> model.updateFilteredRows());
    }

    private void initButtons() {
        createButton.addActionListener(e -> {
            dispose();
            new ServiceTableCreateForm();
        });
        IdButton.addActionListener(e -> {
            model.setSorter(new Comparator<Service>() {
                @Override
                public int compare(Service o1, Service o2) {
                    return Integer.compare(o1.getId(), o2.getId());
                }
            });
        });
        durationButton.addActionListener(e -> model.setSorter(((o1, o2) -> Integer.compare(o1.getDuration(), o2.getDuration()))));
        clearButton.addActionListener(e -> {
            costBox.setSelectedIndex(0);
            titleBox.setSelectedIndex(0);
            searchField.setText("");
            model.setSorter(null);
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                model.updateFilteredRows();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                model.updateFilteredRows();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                model.updateFilteredRows();
            }
        });
    }
}
