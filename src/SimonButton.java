import javax.swing.*;
import java.awt.*;

/**
 * Created by diego-d on 28/01/17.
 */
public class SimonButton extends JButton {
    private static final int ILLUMINATION_INT = 4987942;

    SimonButton(String text, int color) {
        super(text);
        this.setForeground(Color.WHITE);
        this.setBackground(new Color(color));
    }
}
