package for_exam;

import lubarog13.Entetys.Material;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class MaterialTable extends BaseForm{
    private JTable table;
    private JPanel mainPanel;
    private JButton createButton;
    private JButton clearButton;
    private JComboBox<String> materialTypeBox;
    private JComboBox<String> countInPackBox;
    private JButton idButton;
    private JButton costButton;
    private JTextField serachField;
    private ExtendedTableModel<Material> model;
    private boolean idSort = true;
    private boolean costSort;

    public MaterialTable() {
        super(800, 600);
        setContentPane(mainPanel);
        initTable();
        initBoxes();
        initButtons();
        setVisible(true);
    }

    private void initTable() {
        model = new ExtendedTableModel<>(
                Material.class,
                new String[]{"id", "Название", "Количество в упаковке", "Единица измерения", "Количество на складе", "Минимальное количество", "Описание", "Цена", "Путь к изображению", "Тип", "Изображение"}
        );
        try {
            model.setAllRows(MaterialManager.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError(this, "Ошибка загрузки данных из базы");
        }
        table.setModel(model);
        table.setRowHeight(100);
        model.getFilters()[0] = new Predicate<Material>() {
            @Override
            public boolean test(Material material) {
                if(serachField.getText()==null || serachField.getText().length()==0) return true;
                return material.getTitle().contains(serachField.getText()) || material.getMaterialType().contains(serachField.getText());
            }
        };
        model.getFilters()[1] = (material -> {
            if(materialTypeBox.getSelectedIndex()==0) return true;
            return material.getMaterialType().equals(materialTypeBox.getItemAt(materialTypeBox.getSelectedIndex()));
        });
        model.getFilters()[2] = (material -> {
            if(countInPackBox.getSelectedIndex()==0) return true;
            if (countInPackBox.getSelectedIndex()==1 && material.getCountInPack()<10) return true;
            if(countInPackBox.getSelectedIndex()==2 && material.getCountInPack()<30 && material.getCountInPack()>=10) return true;
            if(countInPackBox.getSelectedIndex()==3 && material.getCountInPack()<50 && material.getCountInPack()>=30) return true;
            if(countInPackBox.getSelectedIndex()==4 && material.getCountInPack()<100 && material.getCountInPack()>=50) return true;
            return countInPackBox.getSelectedIndex() == 5 && material.getCountInPack() < 1000 && material.getCountInPack() >= 100;
        });
    }

    private void initBoxes() {
        materialTypeBox.addItem("Тип материала:");
        Set<String> materials = new HashSet<>();
        for(Material material: model.getAllRows()){
            materials.add(material.getMaterialType());
        }
        for (String material: materials){
            materialTypeBox.addItem(material);
        }
        materialTypeBox.addItemListener(itemEvent -> model.updateFilters());
        countInPackBox.addItem("Количество в упаковке:");
        countInPackBox.addItem("<10");
        countInPackBox.addItem("<30");
        countInPackBox.addItem("<50");
        countInPackBox.addItem("<100");
        countInPackBox.addItem("<1000");
        countInPackBox.addItemListener(itemEvent -> model.updateFilters());
        serachField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                model.updateFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                model.updateFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                model.updateFilters();
            }
        });
    }


    private void initButtons() {
        clearButton.addActionListener(actionEvent -> {
            serachField.setText("");
            countInPackBox.setSelectedIndex(0);
            materialTypeBox.setSelectedIndex(0);
            model.setSorter(null);
        });
        idButton.addActionListener(actionEvent -> {
            if(!idSort){
                model.setSorter((material, t1) -> Integer.compare(material.getId(), t1.getId()));
            } else {
                model.setSorter(((material, t1) -> Integer.compare(t1.getId(), material.getId())));
            }
            idSort=!idSort;
            costSort=false;
        });
        costButton.addActionListener(actionEvent -> {
            if(!costSort){
                model.setSorter((material, t1) -> Double.compare(material.getCost(), t1.getCost()));
            } else {
                model.setSorter(((material, t1) -> Double.compare(t1.getCost(), material.getCost())));
            }
            costSort=!costSort;
            idSort=false;
        });
    }
}
