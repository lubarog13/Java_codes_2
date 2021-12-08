package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.CustomTableModel;
import lubarog13.Entetys.Material;
import lubarog13.manager.MaterialManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

//Title, CountInPack, Unit, CountInStock, MinCount, Description, Cost, Image, MaterialType
public class MaterialTableForm extends BaseUI {
    private JPanel mainPanel;
    private JTable table1;
    private JButton createButton;
    private JScrollPane materialTable;
    private JButton titleSortButton;
    private JButton idSortButton;
    private JComboBox<String> typeComboBox;
    private JComboBox<String> titleComboBox;
    private JButton clearButton;
    private boolean sortedById = true;
    private boolean sortedByTitle;
    private CustomTableModel<Material> model;

    public MaterialTableForm() {
        super("Materials", 1000, 800);
        setContentPane(mainPanel);
        initTable();
        initBoxes();
        initButtons();
        setVisible(true);
    }

    private void initTable() {
        try {
            model = new CustomTableModel<>(
                    Material.class,
                    new String[]{"ID", "Название", "Кол-во в упак", "ед.изм", "Кол-во на складе", "Миню кол-во", "Описание", "Цена", "Путь к изображению", "Тип материала", "Изображение"},
                    MaterialManager.getMaterials()
            );
            table1.setModel(model);
            table1.setRowHeight(100);
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount()==2){
                        int row = table1.rowAtPoint(e.getPoint());
                        dispose();
                        new MaterialUpdateForm(model.getRows().get(row));
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        createButton.addActionListener(e -> {
            dispose();
            new MaterialCreateForm();
        });
        idSortButton.addActionListener(e -> {
            if(!sortedById)
            model.getRows().sort(new Comparator<Material>() {
                @Override
                public int compare(Material o1, Material o2) {
                    return Integer.compare(o1.getId(), o2.getId());
                }
            });
            else model.getRows().sort(new Comparator<Material>() {
                @Override
                public int compare(Material o1, Material o2) {
                    return Integer.compare(o2.getId(), o1.getId());
                }
            });
            sortedById = !sortedById;
            sortedByTitle=false;
            model.fireTableDataChanged();
        });
        titleSortButton.addActionListener(e -> {
            if(!sortedByTitle)
            Collections.sort(model.getRows(), new Comparator<Material>() {
                @Override
                public int compare(Material o1, Material o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            }); else
                Collections.sort(model.getRows(), new Comparator<Material>() {
                    @Override
                    public int compare(Material o1, Material o2) {
                        return o2.getTitle().compareTo(o1.getTitle());
                    }
                });
            sortedById=false;
            sortedByTitle=!sortedByTitle;
            model.fireTableDataChanged();
        });
        clearButton.addActionListener(e -> {
            typeComboBox.setSelectedIndex(0);
            titleComboBox.setSelectedIndex(0);
            applyFilters();
        });
    }

    private void initBoxes(){
        List<Material> materials = model.getRows();
        Set<String> types = new HashSet<>();
        for (Material material: materials){
            types.add(material.getMaterialType());
        }
        for (String type1: types){
            typeComboBox.addItem(type1);
        }
        typeComboBox.addItemListener(e -> applyFilters());
        for (char c='А'; c<'Я'; c++){
            titleComboBox.addItem(String.valueOf(c));
        }
        titleComboBox.addItemListener(e -> applyFilters());
    }

    private void applyFilters() {
        try {
            List<Material> materials = MaterialManager.getMaterials();
            if(titleComboBox.getSelectedIndex()!=0) {
                materials.removeIf(material -> !material.getTitle().startsWith(titleComboBox.getSelectedItem().toString()));
            }
            if(typeComboBox.getSelectedIndex()!=0){
                materials.removeIf(material -> !material.getMaterialType().equals(typeComboBox.getSelectedItem().toString()));
            }
            model.setRows(materials);
            model.fireTableDataChanged();
        } catch (SQLException throwables) {
            DialogUtil.showError(this, "Ошибка работы с базой");
        }
    }
}
