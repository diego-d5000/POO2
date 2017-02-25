/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys;

import inventorysys.model.Seed;
import inventorysys.view.InventoryPanel;
import inventorysys.view.MainWindow;
import inventorysys.view.ProductWindow;
import inventorysys.view.SearchPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author diego-d
 */
public class InventorySys {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Seed.createTable();
            MainWindow mainWindow = new MainWindow();
            mainWindow.setupViewAndShow();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(InventorySys.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InventorySys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
