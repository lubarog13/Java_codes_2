package lubarog13.base;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseFormTrain extends JFrame {
    public static Image APP_ICON;
    static {
        try {
            APP_ICON = ImageIO.read(BaseFormTrain.class.getClassLoader().getResource("materials.jpg"));
        } catch (IOException e) {

        }
    }

    public BaseFormTrain(int width, int height){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width, height));
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 -height / 2
        );
        if(APP_ICON!=null) setIconImage(APP_ICON);
        setTitle("Мое приложение");
    }
}
