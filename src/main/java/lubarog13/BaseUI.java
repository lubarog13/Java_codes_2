package lubarog13;

import lubarog13.util.DialogUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseUI extends JFrame {

    public static Image APP_ICON = null;

    static {
        try {
            APP_ICON = ImageIO.read(BaseUI.class.getClassLoader().getResource("materials.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            DialogUtil.showError(null, "Ошибка загрузки иконки");
        }
    }

    public BaseUI(String title, int width, int height)
    {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width, height));
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height / 2
        );
        if (APP_ICON!=null)
        setIconImage(APP_ICON);
        setTitle(title);
    }

}
