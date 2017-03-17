/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.InventorySys;
import inventorysys.model.Product;
import inventorysys.model.Product.Categories;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author diego-d
 */
public class ShopWindow {

    JFrame frame;

    public void setupViewAndShow() {
        frame = new JFrame();
        ShopPanel shopPanel = new ShopPanel();

        frame = new JFrame("Tienda");
        frame.setSize(700, 500);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.add(shopPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                InventorySys.showMainOptions(); //To change body of generated methods, choose Tools | Templates.
            }
            
});

        JMenuBar menuBar = new JMenuBar();

        JMenu productsMenu = new JMenu("Productos");
        menuBar.add(productsMenu);

        for (int i = 0; i < Categories.NAMES.length; i++) {
            String category = Categories.NAMES[i];
            JMenu categorySubmenu = new JMenu(category);
            productsMenu.add(categorySubmenu);

            try {
                ArrayList<Product> products = Product.find("category = " + i);
                for (Product product : products) {
                    JMenuItem productMenuItem = new JMenuItem(product.getName());
                    categorySubmenu.add(productMenuItem);
                    productMenuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ProductShortInfoWindow productShortInfoWindow = new ProductShortInfoWindow(shopPanel, product);
                        }
                    });
                }
            } catch (SQLException ex) {
                Logger.getLogger(ShopWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }
}
