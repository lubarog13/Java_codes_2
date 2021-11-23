package lubarog13;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class BaseSubUI<T extends BaseUI> extends BaseUI{
    private T mainForm;

    public BaseSubUI(T mainForm, int width, int height)
    {
        super("Моё приложение", width, height);
        this.mainForm = mainForm;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeSubUI();
            }
        });

        mainForm.setEnabled(false);
    }

    public void closeSubUI()
    {
        dispose();
        mainForm.setEnabled(true);
        mainForm.setVisible(false);
        mainForm.setVisible(true);
    }
}
