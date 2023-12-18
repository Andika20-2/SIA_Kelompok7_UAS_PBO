package com.crud.statistic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataInterface {

    public DataInterface() {
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Supplier, Barang, Jumlah, Email;
                Supplier  = jTextFieldSupplier.getText();
                Barang = jTextFieldJumlah.getText();
                Jumlah = jTextFieldBarang.getText();
                Email  = jTextFieldEmail.getText();
                try {
                    preparedStatement = Connector.ConnectDB().prepareStatement("INSERT INTO  suppliers (supplier_name, Barang, Jumlah, email) values (?,?,?,?);");
                    preparedStatement.setString(1, Supplier);
                    preparedStatement.setString(2, Barang);
                    preparedStatement.setString(3, Jumlah);
                    preparedStatement.setString(4, Email);
                    preparedStatement.executeUpdate();
                    showData();
                    JOptionPane.showMessageDialog(null, "Data Successfully Inserted");
                } catch (SQLException err) {
                    Logger.getLogger(DataInterface.class.getName()).log(Level.SEVERE, null, err);
                }
                jTextFieldSupplier.setText("");
                jTextFieldBarang.setText("");
                jTextFieldJumlah.setText("");
                jTextFieldEmail.setText("");
                }
        });
        UpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(DataInterface::createUpdateGUI);

            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(DataInterface::createDeleteGUI);

            }
        });
    }

    public JPanel getMainPanel(){
        showData();
        return mainPanel;
    }

    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;



    private void showData(){

    try{
            Object[] columnTitle = {"supplier_id", "Supplier", "Barang", "Jumlah", "Email"};
            tableModel = new DefaultTableModel(null, columnTitle);
            jTable.setModel(tableModel);

        //retrieve connection from DB
        Connection connection = Connector.ConnectDB();
        Statement statement = connection.createStatement();
        tableModel.getDataVector().removeAllElements();


        //initiate result with SQL Query
        resultSet = statement.executeQuery("SELECT * FROM suppliers");
        while (resultSet.next()) {
            Object[] data = {
                    resultSet.getString("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("Barang"),
                    resultSet.getString("Jumlah"),
                    resultSet.getString("email"),
            };
            tableModel.addRow(data);
        }
        }catch (SQLException err) {
            throw new RuntimeException(err);
        }
        }

    private static void createUpdateGUI(){
        UpdatePanel updateUI = new UpdatePanel();
        JPanel updateRoot = updateUI.getUpdatePanel();

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(updateRoot);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    private static void createDeleteGUI(){
        DeletePanel deleteUI = new DeletePanel();
        JPanel deleteRoot = deleteUI.getDeletePanel();


        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setContentPane(deleteRoot);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private JPanel mainPanel;
    private JLabel jTitlePanel;
    private JTextField jTextFieldSupplier;
    private JTextField jTextFieldJumlah;
    private JTextField jTextFieldBarang;
    private JTable jTable;
    private JButton AddButton;
    private JButton UpdateButton;
    private JButton DeleteButton;
    private JPanel jFirstPanel;
    private JPanel jSecondPanel;
    private JPanel jThirdPanel;
    private JLabel jLabelBarang;
    private JLabel jLabelSupplier;
    private JLabel jLabelJumlah;
    private JTextField jTextFieldEmail;
    private JLabel jPanelEmail;
}
