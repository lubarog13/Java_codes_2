package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.CustomTableModel;
import lubarog13.Entetys.Product;
import lubarog13.Entetys.ShopProduct;
import lubarog13.manager.ShopProductManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;

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

    CustomTableModel<ShopProduct> model;

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
             model = new CustomTableModel<ShopProduct>(
                     ShopProduct.class,
                     new String[]{"ID", "Название", "Тип", "Артикул", "Описание", "Путь к картинке", "Production Person Count", "Production Workshop Number", "Минимальная стоимость для агента", "Картинка"},
                     ShopProductManager.getAll()
             );
             table.setModel(model);
             table.setRowHeight(100);
             table.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     if(e.getClickCount()==2) {
                         dispose();
                         new ShopProductUpdateForm((ShopProduct) model.getRows().get(table.rowAtPoint(e.getPoint())));
                     }
                 }
             });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }
    }

    private void initButtons() {
        createButton.addActionListener(e -> {
            dispose();
            new ShopProductCreateForm();
        });
        clearButton.addActionListener(e -> {
            typeBox.setSelectedIndex(0);
            titleBox.setSelectedIndex(0);
        });
        idSortButton.addActionListener(e -> {
            if(!idSort) {
                model.getRows().sort(new Comparator<ShopProduct>() {
                    @Override
                    public int compare(ShopProduct o1, ShopProduct o2) {
                        return Integer.compare(o1.getId(), o2.getId());
                    }
                });
            }
            else {
                model.getRows().sort(new Comparator<ShopProduct>() {
                    @Override
                    public int compare(ShopProduct o1, ShopProduct o2) {
                        return Integer.compare(o2.getId(), o1.getId());
                    }
                });
            }
            idSort = !idSort;
            minCostSort = false;
            model.fireTableDataChanged();
        });
        mincostSortButton.addActionListener(e -> {
            if(!minCostSort) {
                model.getRows().sort(Comparator.comparingDouble(ShopProduct::getMinCostForAgent));
            } else {
                model.getRows().sort(((o1, o2) -> Double.compare(o2.getMinCostForAgent(), o1.getMinCostForAgent())));
            }
            minCostSort=!minCostSort;
            idSort=false;
            model.fireTableDataChanged();
        });
        searchButton.addActionListener(e -> {
            try {
                List<ShopProduct> shopProducts = ShopProductManager.searchProduct(searchField.getText());
                if(shopProducts.size()==0) {
                    DialogUtil.showInfo("Ничего не найдено");
                    setFilters();
                    return;
                }
                model.setRows(shopProducts);
                model.fireTableDataChanged();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                DialogUtil.showError("Ошибка работы с базой");
            }
        });
    }

    private void initBoxes() {
        Set<String> types = new HashSet<>();
        List<ShopProduct> products = model.getRows();
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
        titleBox.addItemListener(e -> setFilters());
        typeBox.addItemListener(e -> setFilters());
    }

    private void setFilters() {
        try {
            List<ShopProduct>shopProducts = ShopProductManager.getAll();
            if(titleBox.getSelectedIndex()!=0) {
                shopProducts.removeIf(shopProduct -> !shopProduct.getTitle().startsWith(titleBox.getSelectedItem().toString()));
            }
            if(typeBox.getSelectedIndex()!=0){
               shopProducts.removeIf(shopProduct -> !shopProduct.getProductType().equals(typeBox.getSelectedItem().toString()));
            }
            model.setRows(shopProducts);
            model.fireTableDataChanged();
        } catch (SQLException throwables) {
            DialogUtil.showError("Ошибка работы с базой");
        }
    }
}
