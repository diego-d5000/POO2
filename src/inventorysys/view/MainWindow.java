/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysys.view;

import inventorysys.model.Product;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author diego-d
 */
public class MainWindow {

    JFrame frame;
    InventoryPanel inventoryPanel;

    public void setupViewAndShow() {
        inventoryPanel = new InventoryPanel();
        SearchPanel searchPanel = new SearchPanel(inventoryPanel);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(fileMenu);

        frame = new JFrame("Inventario");
        frame.setSize(900, 600);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.add(searchPanel);
        frame.add(inventoryPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenu fileMenuExportOption = new JMenu("Exportar...");
        fileMenu.add(fileMenuExportOption);
        JMenuItem subOptionCsv = new JMenuItem("CSV");
        subOptionCsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String header = "code,name,brand,providerPrice,publicPrice,minStock,maxStock,stock\n";
                saveXsvFile(header, ',');
            }
        });
        fileMenuExportOption.add(subOptionCsv);
        JMenuItem subOptionPsv = new JMenuItem("PSV");
        subOptionPsv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String header = "code|name|brand|providerPrice|publicPrice|minStock|maxStock|stock\n";
                saveXsvFile(header, '|');
            }
        });
        fileMenuExportOption.add(subOptionPsv);
        JMenuItem subOptionCsvL = new JMenuItem("CSV Legible (Excel)");
        subOptionCsvL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String header = "Codigo,"
                        + "Nombre,"
                        + "Marca,"
                        + "Precio Proveedor,"
                        + "Precio Publico,"
                        + "Stock Minimo,"
                        + "Stock Maximo,"
                        + "Existencias\n";
                saveXsvFile(header, ',');
            }
        });
        fileMenuExportOption.add(subOptionCsvL);
        JMenuItem subOptionPsvL = new JMenuItem("PSV Legible (Excel)");
        subOptionPsvL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String header = "Codigo|"
                        + "Nombre|"
                        + "Marca|"
                        + "Precio Proveedor|"
                        + "Precio Publico|"
                        + "Stock Minimo|"
                        + "Stock Maximo|"
                        + "Existencias\n";
                saveXsvFile(header, '|');
            }
        });
        fileMenuExportOption.add(subOptionPsvL);

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private void saveXsvFile(String csvHeader, char separation) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Charset charset = Charset.forName("US-ASCII");
            BufferedWriter writer = null;
            try {
                writer = Files.newBufferedWriter(Paths.get(file.getPath()), charset);
                writer.write(csvHeader, 0, csvHeader.length());

                ArrayList<Product> products = inventoryPanel.getTableProductList();
                for (Product product : products) {
                    String csvRow = product.toXsv(separation) + "\n";
                    writer.write(csvRow, 0, csvRow.length());
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
