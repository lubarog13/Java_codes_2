package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.Entetys.ShopProduct;
import lubarog13.manager.ShopProductManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ShopProductUpdateForm extends BaseUI {
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
    private JTextField idField;
    private JButton deleteButton;


    public ShopProductUpdateForm(ShopProduct shopProduct) {
        super("Products", 800, 600);
        setContentPane(mainPanel);
        initFields(shopProduct);
        saveButton.addActionListener(e -> save());
        backButton.addActionListener(e -> goBack());
        deleteButton.addActionListener(e -> delete());
        setVisible(true);
    }

    private void initFields(ShopProduct shopProduct) {
        idField.setText(String.valueOf(shopProduct.getId()));
        idField.setEditable(false);
        titleField.setText(shopProduct.getTitle());
        descriptionField.setText(shopProduct.getDescription());
        typeField.setText(shopProduct.getProductType());
        imageField.setText(shopProduct.getImagePath());
        ppcspinner.setValue(shopProduct.getProductionPersonCount());
        pwnspinner.setValue(shopProduct.getProductionWorkshopNumber());
        minCountField.setText(String.valueOf(shopProduct.getMinCostForAgent()));
        articulField.setText(shopProduct.getArticleNumber());
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
            ShopProductManager.updateShopProduct(new ShopProduct(Integer.parseInt(idField.getText()), titleField.getText(),
                    typeField.getText(), articulField.getText(),
                    descriptionField.getText(), imageField.getText(),
                    ppc, ppw,
                    cost));
        } catch (SQLException e) {
            DialogUtil.showError("Ошибка сохранения в базе");return;
        }
        DialogUtil.showInfo("Запись обновлена");
        goBack();
    }

    private void goBack() {
        dispose();
        new ShopProductTableForm();
    }

    private void delete() {
        try {
            ShopProductManager.deleteProduct(Integer.parseInt(idField.getText()));
        } catch (SQLException throwables) {
            DialogUtil.showError("Ошибка удаления записи");
            return;
        }
        DialogUtil.showInfo("Запись удалена");
        goBack();
    }
}
