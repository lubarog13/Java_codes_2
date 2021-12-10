package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.Entetys.ShopProduct;
import lubarog13.manager.ShopProductManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ShopProductCreateForm extends BaseUI {
    private JTextField titleField;
    private JTextField typeField;
    private JTextField articulField;
    private JTextField descriptionField;
    private JTextField imageField;
    private JSpinner ppcspinner;
    private JSpinner pwnspinner;
    private JTextField minCountField;
    private JButton saveButton;
    private JButton backButton;
    private JPanel mainPanel;


    public ShopProductCreateForm() {
        super("Products", 800, 600);
        setContentPane(mainPanel);
        saveButton.addActionListener(e -> save());
        backButton.addActionListener(e -> goBack());
        setVisible(true);
    }

    private void save() {
        if(titleField.getText().length()>100 || titleField.getText().length()==0) {
            DialogUtil.showError("Неверный размер названия");
            return;
        }
        if(typeField.getText().length()>100 || typeField.getText().length()==0) {
            DialogUtil.showError("Неверный размер типа");
            return;
        }
        if(articulField.getText().length()>10 || articulField.getText().length()==0) {
            DialogUtil.showError("Неверный размер артикула");
            return;
        }
        if(imageField.getText().length()>100) {
            DialogUtil.showError("Неверный размер пути к картинке");
            return;
        }
        Double cost = 0.0;
        Integer ppc, ppw;
        try {
            cost = Double.parseDouble(minCountField.getText());
        } catch (Exception e){
            DialogUtil.showError("Неверный формат цены");
            return;
        }
        try {
            ppc = Integer.parseInt(ppcspinner.getValue().toString());
        } catch (Exception e){
            DialogUtil.showError("Неверный формат people count");
            return;
        }
        try {
            ppw = Integer.parseInt(pwnspinner.getValue().toString());
        } catch (Exception e){
            DialogUtil.showError("Неверный формат workshop number");
            return;
        }
        try {
            ShopProductManager.createShopProduct(new ShopProduct(0, titleField.getText(),
                    typeField.getText(), articulField.getText(),
                    descriptionField.getText(), imageField.getText(),
                    ppc, ppw,
                    cost));
        } catch (SQLException e) {
            DialogUtil.showError("ошибка сохранения в базе");return;
        }
        DialogUtil.showInfo("Запись создана");
        goBack();
    }

    private void goBack() {
        dispose();
        new ShopProductTableForm();
    }
}
