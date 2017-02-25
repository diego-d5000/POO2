/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author diego-d
 */
public class MainWindow {
    JFrame frame;
    public void setupViewAndShow(){
        InventoryPanel inventoryPanel = new InventoryPanel();
        SearchPanel searchPanel = new SearchPanel(inventoryPanel);
        
        frame = new JFrame("Inventario");
        frame.setSize(900, 600);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.add(searchPanel);
        frame.add(inventoryPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
