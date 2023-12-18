package com.crud.statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Objects;


public class UpdatePanel {
 DefaultTableModel tableModelUpdate;
 ResultSet resultSetUpdate=null;

    public UpdatePanel() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SupplierId, Supplier, Barang, Jumlah, Email;
                SupplierId = jSupplier_id .getText();
                Supplier = jSupplierText.getText();
                Barang = jBarangText.getText();
                Jumlah = jJumlahText.getText();
                Email = jEmailText.getText();
                if (!Objects.equals(SupplierId,"") && !Objects.equals(Supplier,"") && !Objects.equals(Barang,"") && !Objects.equals(Jumlah, "") && !Objects.equals(Email, "")){
                try {
                    preparedStatement = Connector.ConnectDB().prepareStatement("UPDATE suppliers SET supplier_name=?, Barang=?, Jumlah=?, email=? WHERE supplier_id=?;");
                    preparedStatement.setString(1, Supplier);
                    preparedStatement.setString(2, Barang);
                    preparedStatement.setString(3, Jumlah);
                    preparedStatement.setString(4, Email);
                    preparedStatement.setString(5, SupplierId);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Update Data Success");
                    JComponent component = (JComponent) e.getSource();
                    Window window = SwingUtilities.getWindowAncestor(component);
                    window.dispose();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

                } else {
                        JOptionPane.showMessageDialog(null, "Input shouldn't empty");
                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JComponent component = (JComponent) e.getSource();
                    Window window = SwingUtilities.getWindowAncestor(component);
                    window.dispose();


            }
        });
        jtextfieldsearchupdate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                super.keyReleased(e);
                String key = jtextfieldsearchupdate.getText();
                System.out.println(key);

                if (!Objects.equals(key,  "")) {
                     {
                         try {
                             searchDataUpdate(key);
                         } catch (SQLException ex) {
                             throw new RuntimeException(ex);
                         }

                     }

                }
            }
        });
    }
 private  void searchDataUpdate(String key) throws SQLException {
     Object[] columnTitle = {"supplier_id", "Supplier_name", "Barang", "Jumlah", "Email"};
     tableModelUpdate = new DefaultTableModel(null, columnTitle);
     jtableupdate.setModel(tableModelUpdate);


     //retrieve mySQL DB
     Connection connect = Connector.ConnectDB();
     Statement stt = connect.createStatement();
     tableModelUpdate.getDataVector().removeAllElements();

     //set query to fetch data
     resultSetUpdate = stt.executeQuery("SELECT * FROM suppliers WHERE supplier_name LIKE '%"+key+"%' OR Barang LIKE '%" +key + "%' OR Jumlah LIKE '%" + key + "%' OR email LIKE '%" + key + "%' ");
     while (resultSetUpdate.next()) {
         Object[] data = {
                 resultSetUpdate.getString("supplier_id"),
                 resultSetUpdate.getString("supplier_name"),
                 resultSetUpdate.getString("Barang"),
                 resultSetUpdate.getString("Jumlah"),
                 resultSetUpdate.getString("email"),
         };
tableModelUpdate.addRow(data);

     }
 }
    public JPanel getUpdatePanel(){
        return mainUpdatePanel;
    }
    private PreparedStatement preparedStatement;
    private JPanel mainUpdatePanel;
    private JLabel jTitleUpdatePanel;
    private JTextField jSupplier_id;
    private JTextField jSupplierText;
    private JTextField jBarangText;
    private JTextField jJumlahText;
    private JTextField jEmailText;
    private JButton cancelButton;
    private JButton updateButton;
    private JLabel jPanelLabel;
    private JLabel jSupplierLabel;
    private JLabel jBarangLabel;
    private JLabel jJumlahLabel;
    private JLabel jEmailLabel;
    private JLabel jsupplierid;
    private JLabel jLabelSearch;
    private JPanel jPanelUpdate;
    private JLabel jlabelsearch;
    private JTextField jtextfieldsearchupdate;
    private JPanel jpaneltableupdate;
    private JTable jtableupdate;
}
