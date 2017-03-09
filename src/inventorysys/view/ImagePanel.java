/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author diego-d
 */
public class ImagePanel extends JPanel{

    public ImagePanel(String img, int w, int h) {
        this.setSize(w, h);
        this.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("images/"+img);
        JLabel label = new JLabel();
        label.setIcon(icon);
        this.add(label);
    }
}
