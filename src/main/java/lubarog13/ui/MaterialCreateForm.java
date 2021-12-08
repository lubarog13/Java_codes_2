package lubarog13.ui;

import lubarog13.BaseUI;
import lubarog13.Entetys.Material;
import lubarog13.manager.MaterialManager;
import lubarog13.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class MaterialCreateForm extends BaseUI {
    private JPanel mainPanel;
    private JTextField titleText;
    private JTextField unitText;
    private JSpinner countInPackage;
    private JTextField countInStock;
    private JTextField minCount;
    private JTextArea descriptionText;
    private JTextField cost;
    private JButton createButton;
    private JButton backButton;
    private JTextField image;
    private JTextField type;

    public MaterialCreateForm() {
        super("Materials", 800, 600);
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }

    private void initButtons() {
        this.createButton.addActionListener(e -> saveMaterial());
        backButton.addActionListener(e -> goBack());
    }

    private void saveMaterial() {
        if(titleText.getText().length()==0 || titleText.getText().length()>100) {
            DialogUtil.showError(this, "Неверный размер названия");
            return;
        }
        if(Integer.parseInt(countInPackage.getValue().toString())<=0) {
            DialogUtil.showError(this, "Неверное количество в упаковке");
            return;
        }
        if (unitText.getText().length()>10 || unitText.getText().length()==0){
            DialogUtil.showError(this, "Неверный размер единицы измерения");
            return;
        }
        Double countIs, mincount, Cost;
        try {
            countIs = Double.parseDouble(countInStock.getText());
            mincount = Double.parseDouble(minCount.getText());
            Cost = Double.parseDouble(cost.getText());
        } catch (Exception e) {
            DialogUtil.showError(this, "Неверный формат дробного числа");
            return;
        }
        if(countIs==0 || mincount==0 ||Cost==0) {
            DialogUtil.showError(this, "Значения должны быть больше нуля");
            return;
        }
        if(type.getText().length()==0 || type.getText().length()>50){
            DialogUtil.showError(this, "Неверный размер типа");
            return;
        }
        if(image.getText().length()>100) {
            DialogUtil.showError(this, "Неверный размер изображения");
            return;
        }
        try {
            MaterialManager.createMaterial(new Material(0, titleText.getText(), Integer.parseInt(countInPackage.getValue().toString()),
                    unitText.getText(), countIs, mincount, descriptionText.getText(), Cost, image.getText(), type.getText()));
        } catch (SQLException throwables) {
            DialogUtil.showError(this, "Ошибка создания материала");
            return;
        }
        DialogUtil.showInfo(this, "Запись создана");
        goBack();
    }

    private void goBack() {
        dispose();
        new MaterialTableForm();
    }
}
