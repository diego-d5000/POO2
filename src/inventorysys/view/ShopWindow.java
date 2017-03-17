/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.model.Product;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
    public void setupViewAndShow(){
        frame = new JFrame();
        ShopPanel shopPanel = new ShopPanel();
        
        frame = new JFrame("Tienda");
        frame.setSize(700, 500);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.add(shopPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu productsMenu = new JMenu("Productos");
        menuBar.add(productsMenu);
        
        JMenuItem productOne = new JMenuItem("Producto 1");
        productsMenu.add(productOne);
        productOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = null; 
                try {
                    product = Product.findByCode("3265983999");
                } catch (SQLException ex) {
                    Logger.getLogger(ShopWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                ProductShortInfoWindow productShortInfoWindow = new ProductShortInfoWindow(shopPanel, product);
            }
        });
        JMenuItem productTwo = new JMenuItem("Producto 2");
        productsMenu.add(productTwo);
        JMenuItem productThree = new JMenuItem("Producto 3");
        productsMenu.add(productThree);
        JMenuItem productFour = new JMenuItem("Producto 4");
        productsMenu.add(productFour);
        
        frame.setJMenuBar(menuBar);
        
        frame.setVisible(true);
    }
}
