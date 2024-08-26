/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproject.utill;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ConfigDB {
    public Connection cn;
    
    public ConfigDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_javaproject", "root", "");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
    }
    
    private Object[][] isiTabel(String SQL, int kolom) {
        Object[][] data = null;
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            rs.last();
            int baris = rs.getRow();
            int j = 0;
            rs.beforeFirst();
            
            data = new Object[baris][kolom];
            
            while (rs.next()) {
                for (int i = 0; i < kolom; i++) {
                    data[j][i] = rs.getString(i+1);
                }
                j++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
        return data;
    }
    
    public void tampilData(String judul[], String SQL, JTable tabel) {
        try {
            String title[] = judul;
            int jumlah = title.length;
            tabel.setModel(new DefaultTableModel(isiTabel(SQL, jumlah), title));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
    }
    
    public void aturLebarKolom(JTable tabel, int baris[]) {
        try {
            int getBaris[] = baris;
            int jumlahBaris = getBaris.length;
            tabel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for(int i = 0; i < jumlahBaris-1; i++) {
                tabel.getColumnModel().getColumn(i).setPreferredWidth(getBaris[i]);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
    }
    
    public void simpanData(String SQL) {
        try {
            Statement st = this.cn.createStatement();
            st.execute(SQL);
            st.close();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
    }
    
    public void hapusData(String SQL) {
        try {
            Statement st = this.cn.createStatement();
            st.execute(SQL);
            st.close();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
    }
    
    public void duplikasiData (String tabel, String key, String nilai) {
        boolean ada = false;
        try {
            Statement st = this.cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + tabel + " WHERE "+ key + " = '" + nilai + "'");
            if (rs.next()) {
                ada = true;
            } else {
                ada = false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
    }
    
    public void cariData (String judul[], String SQL, JTable tabel) {
        try {
            String title[] = judul;
            int jumlah = title.length;
            tabel.setModel(new DefaultTableModel(isiTabel(SQL, jumlah), title));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
    }
}
