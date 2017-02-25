/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.model.Product;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author diego-d
 */
public class SearchPanel extends JPanel {

    private InventoryPanel inventoryPanel;

    public SearchPanel(InventoryPanel inventoryPanel) {
        super(new FlowLayout());
        setupView();
        this.inventoryPanel = inventoryPanel;
    }

    private void setupView() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Busqueda");
        Border border = BorderFactory.createCompoundBorder(titledBorder, new EmptyBorder(10, 10, 10, 10));
        this.setBorder(border);

        String[] criteriaOptions = {"Codigo", "Nombre", "Marca"};
        JComboBox comboBoxSearchCriteria = new JComboBox(criteriaOptions);
        JTextField queryTextField = new JTextField(40);
        JButton searchButton = new JButton("Buscar");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String criteriaName = (String) comboBoxSearchCriteria.getSelectedItem();
                String whereSentence = "";
                if (criteriaName.equals(criteriaOptions[0])) {
                    whereSentence = "code LIKE ";
                } else if (criteriaName.equals(criteriaOptions[1])) {
                    whereSentence = "name LIKE ";
                } else {
                    whereSentence = "brand LIKE ";
                }

                try {
                    ArrayList<Product> products = Product.find(whereSentence
                            + "'"
                            + queryTextField.getText()
                            + "%'");
                    inventoryPanel.notifySearchPanelAction(products);
                } catch (SQLException ex) {
                    Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JButton showAllButton = new JButton("Ver Todos");
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Product> products = Product.findAll();
                    inventoryPanel.notifySearchPanelAction(products);
                } catch (SQLException ex) {
                    Logger.getLogger(SearchPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        this.add(comboBoxSearchCriteria);
        this.add(queryTextField);
        this.add(searchButton);
        this.add(showAllButton);
    }

}
