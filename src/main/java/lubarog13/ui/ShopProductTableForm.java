package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.CustomTableModel;
import lubarog13.Entetys.Product;
import lubarog13.Entetys.ShopProduct;
import lubarog13.ExtendedTableModelWithDocs;
import lubarog13.manager.ShopProductManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

public class ShopProductTableForm extends BaseUI {
    private JTable table;
    private JButton createButton;
    private JPanel mainPanel;
    private JComboBox<String> titleBox;
    private JComboBox<String> typeBox;
    private JButton clearButton;
    private boolean idSort = true;
    private boolean minCostSort;
    private JButton mincostSortButton;
    private JButton idSortButton;
    private JTextField searchField;
    private JButton searchButton;

    private ExtendedTableModelWithDocs<ShopProduct> model;

    public ShopProductTableForm() {
        super("Products", 1000, 800);
        setContentPane(mainPanel);
        initTable();
        initBoxes();
        initButtons();
        setVisible(true);
    }

    private void initTable() {
        try {
            table.getTableHeader().setReorderingAllowed(false);
             model = new ExtendedTableModelWithDocs<ShopProduct>(
                     ShopProduct.class,
                     new String[]{"ID", "Название", "Тип", "Артикул", "Описание", "Путь к картинке", "Production Person Count", "Production Workshop Number", "Минимальная стоимость для агента", "Картинка"}
             ) {
                 @Override
                 public void onUpdateRowsEvent() {
                 }
            };
             model.setAllRows(ShopProductManager.getAll());
             table.setModel(model);
             table.setRowHeight(100);
             table.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     if(e.getClickCount()==2) {
                         dispose();
                         new ShopProductUpdateForm((ShopProduct) model.getFilteredRows().get(table.rowAtPoint(e.getPoint())));
                     }
                 }
             });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }
        model.getFilters()[0] = new Predicate<ShopProduct>() {
            @Override
            public boolean test(ShopProduct shopProduct) {
                String searchText = searchField.getText();

                if(searchText == null || searchText.isEmpty()) {
                    return true;
                }
                String s = shopProduct.getTitle() + shopProduct.getProductType() + shopProduct.getArticleNumber();
                return s.contains(searchText);
            }
        };
        model.getFilters()[1] = new Predicate<ShopProduct>() {
            @Override
            public boolean test(ShopProduct shopProduct) {
                if(titleBox.getSelectedIndex()!=0)
                return shopProduct.getTitle().startsWith(titleBox.getSelectedItem().toString());
                return true;
            }
        };
        model.getFilters()[2] = new Predicate<ShopProduct>() {
            @Override
            public boolean test(ShopProduct shopProduct) {
                if (typeBox.getSelectedIndex()!=0)
                return shopProduct.getProductType().equals(typeBox.getSelectedItem());
                return true;
            }
        };
    }

    private void initButtons() {
        createButton.addActionListener(e -> {
            dispose();
            new ShopProductCreateForm();
        });
        clearButton.addActionListener(e -> {
            searchField.setText("");
            titleBox.setSelectedIndex(0);
            typeBox.setSelectedIndex(0);
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
        idSortButton.addActionListener(e ->
                model.setSorter(new Comparator<ShopProduct>() {
                    @Override
                    public int compare(ShopProduct o1, ShopProduct o2) {
                        return Integer.compare(o1.getId(), o2.getId());
                    }
                })
        );
        mincostSortButton.addActionListener(e ->
                model.setSorter(new Comparator<ShopProduct>() {
                    @Override
                    public int compare(ShopProduct o1, ShopProduct o2) {
                        return Double.compare(o1.getMinCostForAgent(), o2.getMinCostForAgent());
                    }
                })
        );
    }

    private void initBoxes() {
        Set<String> types = new HashSet<>();
        List<ShopProduct> products = model.getAllRows();
        for (ShopProduct product: products) {
            types.add(product.getProductType());
        }
        typeBox.addItem("");
        for (String type: types){
            typeBox.addItem(type);
        }
        titleBox.addItem("");
        List<String> chars = new ArrayList<>();
        for (char c='А'; c<='Я'; c++) {
            chars.add(String.valueOf(c));
        }
        for (String cha: chars){
            titleBox.addItem(cha);
        }
        titleBox.addItemListener(e -> {
            if(e.getStateChange()== ItemEvent.SELECTED) model.updateFilteredRows();
        });
        typeBox.addItemListener(e -> {
            if(e.getStateChange()== ItemEvent.SELECTED) model.updateFilteredRows();
        });
    }
}
