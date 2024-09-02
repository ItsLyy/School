/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproject.utill;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaproject.utill.ConfigDB;
import javax.swing.table.DefaultTableModel;
import javaproject.utill.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
/**
 *
 * @author itsly
 */
public class FilmUtill {
    String judulKolom[] = {"Kode Film", "Judul", "Genre", "Tahun", "Asal", "Stok"};
    String sqlFilm = "SELECT * FROM tb_film";
    int lebarKolom[] = {100, 300, 100, 60, 200, 60};
    
    JTable tableDisplay; 
    JTextField iKodeFilm;
    JTextField iJudulFilm;
    JTextField iGenreFilm;
    JTextField iAsalFilm;
    JTextField iStokFilm;
    JTextField iTahunFilm;
    JTextField iCariFilm;
    
    public FilmUtill(JTable tableDisplay, 
            JTextField iKodeFilm,
            JTextField iJudulFilm,
            JTextField iGenreFilm,
            JTextField iAsalFilm,
            JTextField iStokFilm,
            JTextField iTahunFilm,
            JTextField iCariFilm) {
        this.tableDisplay = tableDisplay;
        this.iKodeFilm = iKodeFilm;
        this.iJudulFilm = iJudulFilm;
        this.iGenreFilm = iGenreFilm;
        this.iAsalFilm = iAsalFilm;
        this.iStokFilm = iStokFilm;
        this.iTahunFilm = iTahunFilm;
        this.iCariFilm = iCariFilm;
    }
    
    public void tampilTabelFilm() {        
        new ConfigDB().tampilData(judulKolom, sqlFilm, this.tableDisplay);
        new ConfigDB().aturLebarKolom(this.tableDisplay, lebarKolom);
    }
    
    public void clearInputFilm() {
        iKodeFilm.setText(null);
        iJudulFilm.setText(null);
        iGenreFilm.setText(null);
        iAsalFilm.setText(null);
        iStokFilm.setText(null);
        iTahunFilm.setText(null);
    }
    
    public void simpanDataFilm() {
        try {
            if (iKodeFilm.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Kode Film belum terisi");
                iKodeFilm.requestFocus();
                return;
            }
            
            if (iJudulFilm.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Judul Film belum terisi");
                iJudulFilm.requestFocus();
                return;
            }
            if (iGenreFilm.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Genre Film belum terisi");
                iGenreFilm.requestFocus();
                return;
            }
            
            if (iTahunFilm.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tahun Film belum terisi");
                iTahunFilm.requestFocus();
                return;
            }
            
            if (iAsalFilm.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Asal Film belum terisi");
                iAsalFilm.requestFocus();
                return;
            }
            
            if (iStokFilm.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Stok Film belum terisi");
                iStokFilm.requestFocus();
                return;
            }
            
            if (new ConfigDB().duplikasiData("tb_film", "kode_film", iKodeFilm.getText()) == true) {
                JOptionPane.showMessageDialog(null, "Kode Film sudah terdaftar");
                return;
            }
            
            String SQL = "INSERT INTO tb_film VALUES('" + iKodeFilm.getText() + "', '" 
                    + iJudulFilm.getText() + "', '" + iGenreFilm.getText() + "', " 
                    + iTahunFilm.getText() + ", '" + iAsalFilm.getText() + "', " 
                    + iStokFilm.getText() + ")";
            new ConfigDB().simpanData(SQL);
            tampilTabelFilm();
            clearInputFilm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian Simpan Data : \n [" + e.toString() + "]");
        }
    }
    
    public void ubahDataFilm() {
        try {
            if (
                    iKodeFilm.getText().isEmpty() &&
                    iJudulFilm.getText().isEmpty() &&
                    iGenreFilm.getText().isEmpty() &&
                    iTahunFilm.getText().isEmpty() &&
                    iAsalFilm.getText().isEmpty() &&
                    iStokFilm.getText().isEmpty()
                    ) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih data yang ingin diubah");
                tableDisplay.requestFocus();
            } else {
                // Tambahan cek apakah Kode tersebut tersedia atau tidak
                System.out.println(iKodeFilm.getText());
                if (new ConfigDB().cekId("tb_film", "kode_film", iKodeFilm.getText()) == true) {
                    String SQL = "UPDATE tb_film SET judul = '" + iJudulFilm.getText() + 
                        "', genre = '" + iGenreFilm.getText() + 
                        "', tahun = " + iTahunFilm.getText() + 
                        ", asal = '" + iAsalFilm.getText() + 
                        "', stok = " + iStokFilm.getText() + 
                        " WHERE kode_film = '" + iKodeFilm.getText() + "'";
                    new ConfigDB().ubahData(SQL);
                    tampilTabelFilm();
                    clearInputFilm();
                } else {
                    JOptionPane.showMessageDialog(null, "Data film belum ditambahkan atau Kode Film salah");
                    iKodeFilm.requestFocus();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian Ubah Data : \n [" + e.toString() + "]");
        }
    }
    
    public void hapusDataFilm() {
        try {
            if (
                    iKodeFilm.getText().isEmpty() &&
                    iJudulFilm.getText().isEmpty() &&
                    iGenreFilm.getText().isEmpty() &&
                    iTahunFilm.getText().isEmpty() &&
                    iAsalFilm.getText().isEmpty() &&
                    iStokFilm.getText().isEmpty()
                    ) {
                JOptionPane.showMessageDialog(null, "Silahkan pilih data yang ingin diubah");
                tableDisplay.requestFocus();
            } else {
                // Tambahan cek apakah Kode tersebut tersedia atau tidak
                if (new ConfigDB().cekId("tb_film", "kode_film", iKodeFilm.getText()) == true) {
                    String SQL = "DELETE FROM tb_film WHERE kode_film = '" + iKodeFilm.getText() + "'";
                    new ConfigDB().hapusData(SQL);
                    tampilTabelFilm();
                    clearInputFilm();
                } else {
                    JOptionPane.showMessageDialog(null, "Data film belum ditambahkan atau Kode Film salah");
                    iKodeFilm.requestFocus();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf terjadi kesalahan pada bagian Hapus Data : \n [" + e.toString() + "]");
        }
    }
    
    public void mouseClickFilm(int baris) {
        iKodeFilm.setText(String.valueOf(tableDisplay.getValueAt(baris, 0)));
        iJudulFilm.setText(String.valueOf(tableDisplay.getValueAt(baris, 1)));
        iGenreFilm.setText(String.valueOf(tableDisplay.getValueAt(baris, 2)));
        iAsalFilm.setText(String.valueOf(tableDisplay.getValueAt(baris, 3)));
        iStokFilm.setText(String.valueOf(tableDisplay.getValueAt(baris, 4)));
        iTahunFilm.setText(String.valueOf(tableDisplay.getValueAt(baris, 5)));
    }
    
    public void cariFilm() {
        try {
            String SQL = "SELECT * FROM tb_film WHERE judul LIKE '%" + iCariFilm.getText() + "%'";
            new ConfigDB().cariData(judulKolom, SQL, tableDisplay);
            new ConfigDB().aturLebarKolom(this.tableDisplay, lebarKolom);
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
