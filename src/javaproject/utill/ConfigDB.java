/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproject.utill;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

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
            Statement st = this.cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
    
    public void ubahData(String SQL) {
        try {
            Statement st = this.cn.createStatement();
            st.execute(SQL);
            st.close();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
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
    
    public boolean duplikasiData (String tabel, String key, String nilai) {
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
        return ada;
    }
    
    public boolean cekId (String tabel, String key, String nilai) {
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
        return ada;
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
    
    public void tampilLaporan(String laporanFile, String SQL){
        try {
            File file = new File(laporanFile);
            System.out.println(file);
            JasperDesign jasDes = JRXmlLoader.load(file);
            JRDesignQuery sqlQuery = new JRDesignQuery();
            sqlQuery.setText(SQL);
            jasDes.setQuery(sqlQuery);
            JasperReport JR = JasperCompileManager.compileReport(jasDes);
            JasperPrint JP = JasperFillManager.fillReport(JR,null,ConfigDB.this.cn);
            JasperViewer.viewReport(JP);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
    }
    
    public int jumlahRecord(String SQL) {
        int hasil = 0;
        int i = 0;
        try {
            Statement st = ConfigDB.this.cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next()) {
                i++;
                hasil = i;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
        return hasil;
    }
    
    public String ambilData(String SQL, String value) {
        String hasil = "";
        try {
            Statement st = ConfigDB.this.cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next()) {
                hasil = rs.getString(value);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian : \n [" + e.toString() + "]");
        }
        return hasil;
    }
}
