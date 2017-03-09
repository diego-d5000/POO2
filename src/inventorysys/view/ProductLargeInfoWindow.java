/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.model.Sale;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author diego-d
 */
public class ProductLargeInfoWindow {

    ShopPanel shopPanelParent;
    JFrame frame;

    public ProductLargeInfoWindow(ShopPanel shopPanelParent) {
        this.shopPanelParent = shopPanelParent;
        setupAndShowView();
    }

    private void setupAndShowView() {
        frame = new JFrame();
        frame.setSize(200, 500);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ImagePanel imagePanel = new ImagePanel("gansito.jpg", 1, 1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(imagePanel, c);
        
        JTextArea descriptionText = new JTextArea("Lorem ipsum dolor sit amet, consectetur "
                + "adipiscing elit, sed do eiusmod tempor incididunt ut labore "
                + "et dolore magna aliqua. Ut enim ad minim veniam, quis "
                + "nostrud exercitation ullamco laboris nisi ut aliquip ex ea "
                + "commodo consequat. Duis aute irure dolor in reprehenderit in "
                + "voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
                + "Excepteur sint occaecat cupidatat non proident, sunt in "
                + "culpa qui officia deserunt mollit anim id est laborum.");
        descriptionText.setEditable(false);
        
        descriptionText.setLineWrap(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        panel.add(descriptionText, c);
        
        JButton buyButton = new JButton("Comprar");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanelParent.registerBoughtProduct(new Sale("0", "Gansito", 180, 1));
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridy = 2;
        c.gridx = 0;
        c.insets = new Insets(0, 5, 0, 5);
        panel.add(buyButton, c);
        
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        panel.add(closeButton, c);
        
        frame.add(panel);
        frame.setVisible(true);
    }

}
