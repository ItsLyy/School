/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproject.utill;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaproject.utill.ConfigDB;
import javax.swing.table.DefaultTableModel;
import javaproject.utill.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
/**
 *
 * @author itsly
 */
public class AnggotaUtill {
    String judulKolom[] = {"ID Anggota", "Nama Anggota", "Tempat Lahir", "Tanggal Lahir", "Jenis Kelamin", "Status", "Alamat", "Telp"};
    String sqlAnggota = "SELECT * FROM tb_anggota";
    int lebarKolom[] = {100, 300, 250, 100, 150, 200, 400, 100};
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    JTable tableDisplay; 
    JTextField iIdAnggota;
    JTextField iNamaAnggota;
    JTextField iTempatLahirAnggota;
    JDateChooser iTanggalLahirAnggota;
    JComboBox iKelaminAnggota;
    JComboBox iStatusAnggota;
    JTextField iAlamatAnggota;
    JTextField iNomorTeleponAnggota;
    JTextField iSearch;
    
    public AnggotaUtill(JTable tableDisplay, 
            JTextField iIdAnggota,
            JTextField iNamaAnggota,
            JTextField iTempatLahir,
            JDateChooser iTanggalLahir,
            JComboBox iKelaminAnggota,
            JComboBox iStatusAnggota,
            JTextField iAlamatAnggota,
            JTextField iNomorTeleponAnggota,
            JTextField iSearch) {
        this.tableDisplay = tableDisplay;
        this.iIdAnggota = iIdAnggota;
        this.iNamaAnggota = iNamaAnggota;
        this.iTempatLahirAnggota = iTempatLahir;
        this.iTanggalLahirAnggota = iTanggalLahir;
        this.iKelaminAnggota = iKelaminAnggota;
        this.iStatusAnggota = iStatusAnggota;
        this.iAlamatAnggota = iAlamatAnggota;
        this.iNomorTeleponAnggota = iNomorTeleponAnggota;
        this.iSearch = iSearch;
    }
    
    public void tampilTabelAnggota() {        
        new ConfigDB().tampilData(judulKolom, sqlAnggota, this.tableDisplay);
        new ConfigDB().aturLebarKolom(this.tableDisplay, lebarKolom);
    }
    
    public void clearInputAnggota() {
        this.iIdAnggota.setText(null);
        this.iNamaAnggota.setText(null);
        this.iTempatLahirAnggota.setText(null);
        this.iTanggalLahirAnggota.setDate(null);
        this.iKelaminAnggota.setSelectedItem(".:Pilih Data:.");
        this.iStatusAnggota.setSelectedItem(".:Pilih Data:.");
        this.iAlamatAnggota.setText(null);
        this.iNomorTeleponAnggota.setText(null);
    }
    
    public void simpanDataAnggota() {
        try {
            if (this.iIdAnggota.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID Anggota belum terisi");
                this.iIdAnggota.requestFocus();
                return;
            }
            
            if (this.iNamaAnggota.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama anggota belum terisi");
                this.iNamaAnggota.requestFocus();
                return;
            }
            if (this.iTempatLahirAnggota.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tempat lahir anggota belum terisi");
                this.iTempatLahirAnggota.requestFocus();
                return;
            }
            
            if (this.iKelaminAnggota.getSelectedItem() == ".:Pilih Data:.") {
                JOptionPane.showMessageDialog(null, "Jenis kelamin anggota belum dipilih");
                this.iKelaminAnggota.requestFocus();
                return;
            }
            
            if (this.iStatusAnggota.getSelectedItem() == ".:Pilih Data:.") {
                JOptionPane.showMessageDialog(null, "Status anggota belum dipilih");
                this.iStatusAnggota.requestFocus();
                return;
            }
            
            if (this.iAlamatAnggota.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Alamat anggota belum terisi");
                this.iAlamatAnggota.requestFocus();
                return;
            }
            
            if (this.iNomorTeleponAnggota.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nomor telepon belum terisi");
                this.iNomorTeleponAnggota.requestFocus();
                return;
            }
            
            if (new ConfigDB().duplikasiData("tb_anggota", "id_anggota", this.iIdAnggota.getText()) == true) {
                JOptionPane.showMessageDialog(null, "ID Anggota sudah terdaftar");
                return;
            }
            
            String tgl = sdf.format(this.iTanggalLahirAnggota.getDate());
            String SQL = "INSERT INTO tb_anggota VALUES('" + this.iIdAnggota.getText() + "', '" 
                    + this.iNamaAnggota.getText() + "', '" + this.iTempatLahirAnggota.getText() + "', '" 
                    + tgl + "', '" + String.valueOf(this.iKelaminAnggota.getSelectedItem()) + "', '" 
                    + String.valueOf(this.iStatusAnggota.getSelectedItem()) + "', '" + this.iAlamatAnggota.getText() + "', '"+ this.iNomorTeleponAnggota.getText() +"')";
            new ConfigDB().simpanData(SQL);
            tampilTabelAnggota();
            clearInputAnggota();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian Simpan Data : \n [" + e.toString() + "]");
        }
    }
    
    public void ubahDataAnggota() {
        try {
            if (
                    this.iIdAnggota.getText().isEmpty() &&
                    this.iNamaAnggota.getText().isEmpty() &&
                    this.iTempatLahirAnggota.getText().isEmpty() &&
                    this.iKelaminAnggota.getSelectedItem() == ".:Pilih Data:." &&
                    this.iStatusAnggota.getSelectedItem() == ".:Pilih Data:." &&
                    this.iAlamatAnggota.getText().isEmpty() &&
                    this.iNomorTeleponAnggota.getText().isEmpty()
                    ) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih data yang ingin diubah");
                tableDisplay.requestFocus();
            } else {
                // Tambahan cek apakah Kode tersebut tersedia atau tidak
                if (new ConfigDB().cekId("tb_anggota", "id_anggota", this.iIdAnggota.getText()) == true) {
                    String tgl = sdf.format(this.iTanggalLahirAnggota.getDate());
                    System.out.print(tgl);
                    String SQL = "UPDATE tb_anggota SET nama = '" + this.iNamaAnggota.getText() + 
                        "', tempat_lahir = '" + this.iTempatLahirAnggota.getText() + 
                        "', tgl_lahir = '" + tgl + 
                        "', jkl = '" + String.valueOf(this.iKelaminAnggota.getSelectedItem()) + 
                        "', status = '" + String.valueOf(this.iStatusAnggota.getSelectedItem()) + 
                        "', alamat = '" + this.iAlamatAnggota.getText() + 
                        "', telp = '" + this.iNomorTeleponAnggota.getText() + 
                        "' WHERE id_anggota = '" + this.iIdAnggota.getText() + "'";
                    new ConfigDB().ubahData(SQL);
                    tampilTabelAnggota();
                    clearInputAnggota();
                } else {
                    JOptionPane.showMessageDialog(null, "Data film belum ditambahkan atau Kode Film salah");
                    iIdAnggota.requestFocus();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian Ubah Data : \n [" + e.toString() + "]");
        }
    }
    
    public void hapusDataAnggota() {
        try {
            if (
                    this.iIdAnggota.getText().isEmpty() &&
                    this.iNamaAnggota.getText().isEmpty() &&
                    this.iTempatLahirAnggota.getText().isEmpty() &&
                    this.iKelaminAnggota.getSelectedItem() == ".:Pilih Data:." &&
                    this.iStatusAnggota.getSelectedItem() == ".:Pilih Data:." &&
                    this.iAlamatAnggota.getText().isEmpty() &&
                    this.iNomorTeleponAnggota.getText().isEmpty()
                    ) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih data yang ingin diubah");
                tableDisplay.requestFocus();
            } else {
                // Tambahan cek apakah Kode tersebut tersedia atau tidak
                if (new ConfigDB().cekId("tb_anggota", "id_anggota", this.iIdAnggota.getText()) == true) {
                    String SQL = "DELETE FROM tb_anggota WHERE id_anggota = '" + this.iIdAnggota.getText() + "'";
                    new ConfigDB().hapusData(SQL);
                    tampilTabelAnggota();
                    clearInputAnggota();
                } else {
                    JOptionPane.showMessageDialog(null, "Data film belum ditambahkan atau Kode Film salah");
                    this.iIdAnggota.requestFocus();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian Hapus Data : \n [" + e.toString() + "]");
        }
    }
    
    public void mouseClickAnggota(int baris) {
        this.iIdAnggota.setText(String.valueOf(tableDisplay.getValueAt(baris, 0)));
        this.iNamaAnggota.setText(String.valueOf(tableDisplay.getValueAt(baris, 1)));
        this.iTempatLahirAnggota.setText(String.valueOf(tableDisplay.getValueAt(baris, 2)));
        try {
            this.iTanggalLahirAnggota.setDate(sdf.parse(String.valueOf(tableDisplay.getValueAt(baris, 3))));
        } catch (ParseException ex) {
            Logger.getLogger(AnggotaUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.iKelaminAnggota.setSelectedItem(String.valueOf(tableDisplay.getValueAt(baris, 4)));
        this.iStatusAnggota.setSelectedItem(String.valueOf(tableDisplay.getValueAt(baris, 5)));
        this.iAlamatAnggota.setText(String.valueOf(tableDisplay.getValueAt(baris, 6)));
        this.iNomorTeleponAnggota.setText(String.valueOf(tableDisplay.getValueAt(baris, 7)));
    }
    
    public void cariAnggota() {
        try {
            String SQL = "SELECT * FROM tb_anggota WHERE nama LIKE '%" + iSearch.getText() + "%'";
            new ConfigDB().cariData(judulKolom, SQL, tableDisplay);
            new ConfigDB().aturLebarKolom(this.tableDisplay, lebarKolom);
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
