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
public class TransaksiUtill {
    String judulKolom[] = {"No", "ID Pinjam", "Kode Film", "Tanggal Pinjam", "Tanggal Kembali", "Status", "Keterangan", "Denda"};
    String sqlView = "SELECT * FROM view_pinjam ORDER BY id_pinjam DESC";
    int lebarKolom[] = {70, 120, 250, 110, 200, 120, 120, 120};
    DefaultTableModel list;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    JTable tableDisplayFirst; 
    JTable tableDisplaySecond; 
    JTextField iIdAnggotaTransaksi;
    JTextField iIdFilmTransaksi;
    JTextField iIdPinjamTransaksi;
    JTextField iNamaAnggotaTransaksi;
    JTextField iFilmTransaksi;
    JComboBox iKeteranganTransaksi;
    JDateChooser iTanggalPinjamTransaksi;
    JDateChooser iTanggalKembaliTransaksi;
    JTextField iSearch;
    
    public TransaksiUtill(JTable tableDisplayFirst,
            JTable tableDisplaySecond, 
            JTextField iIdAnggotaTransaksi,
            JTextField iIdFilmTransaksi,
            JTextField iIdPinjamTransaksi,
            JTextField iNamaAnggotaTransaksi,
            JTextField iFilmTransaksi,
            JComboBox iKeteranganTransaksi,
            JDateChooser iTanggalPinjamTransaksi,
            JDateChooser iTanggalKembaliTransaksi,
            JTextField iSearch) {
        this.tableDisplayFirst = tableDisplayFirst;
        this.tableDisplaySecond = tableDisplaySecond;
        this.iIdAnggotaTransaksi = iIdAnggotaTransaksi;
        this.iIdFilmTransaksi = iIdFilmTransaksi;
        this.iIdPinjamTransaksi = iIdPinjamTransaksi;
        this.iNamaAnggotaTransaksi = iNamaAnggotaTransaksi;
        this.iFilmTransaksi = iFilmTransaksi;
        this.iKeteranganTransaksi = iKeteranganTransaksi;
        this.iTanggalPinjamTransaksi = iTanggalPinjamTransaksi;
        this.iTanggalKembaliTransaksi = iTanggalKembaliTransaksi;
        this.iSearch = iSearch;
        
        list = new DefaultTableModel();
        tableDisplaySecond.setModel(list);
        list.addColumn("ID Anggota");
        list.addColumn("Nama Anggota");
        list.addColumn("Kode Film");
        list.addColumn("Judul");
    }
    
    public void tampilTabelTransaksi() {        
        new ConfigDB().tampilData(judulKolom, sqlView, this.tableDisplayFirst);
        new ConfigDB().aturLebarKolom(this.tableDisplayFirst, lebarKolom);
    }
    
    public void clearInputTransaksi() {
        int jumlah = tableDisplaySecond.getRowCount();
        for ( int i = 0; i < jumlah; i++) {
            list.removeRow(i);
        }
        this.iIdAnggotaTransaksi.setText(null);
        this.iIdFilmTransaksi.setText(null);
        this.iIdPinjamTransaksi.setText(null);
        this.iNamaAnggotaTransaksi.setText(null);
        this.iFilmTransaksi.setText(null);
        this.iKeteranganTransaksi.setSelectedItem(".:Pilih Data:.");
        this.iTanggalPinjamTransaksi.setDate(null);
        this.iTanggalKembaliTransaksi.setDate(null);
    }
    
    public void cariIdPeminjaman() {
        String SQL = "SELECT * FROM detail_pinjam WHERE id_pinjam = '" + this.iIdPinjamTransaksi.getText() + "'";
        new ConfigDB().cariData(this.judulKolom, SQL, this.tableDisplayFirst);
        new ConfigDB().aturLebarKolom(this.tableDisplayFirst, this.lebarKolom);
    }
    
    public void cariTunggakan() {
        String SQL = "SELECT * FROM view_pinjam WHERE id_anggota = '" + iIdAnggotaTransaksi.getText() + "' AND status = 'Belum Dikembalikan'";
        int jumlah = new ConfigDB().jumlahRecord(SQL);
        if (jumlah == 0) {
            JOptionPane.showMessageDialog(null, "Tidak ada tunggakan Film  yang belum dikembalikan");
        } else {
            JOptionPane.showMessageDialog(null, "Ada" + String.valueOf(jumlah) + "tunggakan Film yang belum dikembalikan");
        }
    }
    
    public void tambahListItemPinjam() {
        try {
            String isi[] = {iIdAnggotaTransaksi.getText(), iNamaAnggotaTransaksi.getText(), iIdFilmTransaksi.getText(), iFilmTransaksi.getText()};
            list.addRow(isi);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }
    
    public void simpanDataTransaksi() {
        try {
            if (this.iIdAnggotaTransaksi.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID Anggota belum terisi");
                this.iIdAnggotaTransaksi.requestFocus();
                return;
            }
            
            if (this.iIdFilmTransaksi.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID Film belum terisi");
                this.iIdFilmTransaksi.requestFocus();
                return;
            }
            
            if (this.iIdPinjamTransaksi.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID Pinjam belum terisi");
                this.iIdPinjamTransaksi.requestFocus();
                return;
            }
            
            if (this.iNamaAnggotaTransaksi.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama anggota belum terisi");
                this.iNamaAnggotaTransaksi.requestFocus();
                return;
            }
            if (this.iFilmTransaksi.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Film belum terisi");
                this.iFilmTransaksi.requestFocus();
                return;
            }
            
            if (this.iKeteranganTransaksi.getSelectedItem() == ".:Pilih Data:.") {
                JOptionPane.showMessageDialog(null, "keterangan belum dipilih");
                this.iKeteranganTransaksi.requestFocus();
                return;
            }
            
            if (new ConfigDB().duplikasiData("tb_anggota", "id_anggota", this.iIdPinjamTransaksi.getText()) == true) {
                JOptionPane.showMessageDialog(null, "ID Anggota sudah terdaftar");
                return;
            }
            
            // BELUM
            String SQLPinjam = "INSERT INTO tb_pinjam VALUES('" + iIdPinjamTransaksi.getText() + "', '" + iIdAnggotaTransaksi.getText() + "', 0)";
            new ConfigDB().simpanData(SQLPinjam);
            int jumlah = tableDisplaySecond.getRowCount();
            for (int i = 0; i < jumlah; i++) {
                String tglPinjam = sdf.format(this.iTanggalPinjamTransaksi.getDate());
                String tglKembali = sdf.format(this.iTanggalKembaliTransaksi.getDate());
                String SQLDetail = "INSERT INTO tb_detail_pinjam VALUES(null, '" + String.valueOf(tableDisplaySecond.getValueAt(i,2)) + "', '" + tglPinjam + "', '" + tglKembali + "', '0', '" + String.valueOf(iKeteranganTransaksi.getSelectedItem()) + "', 0, '" + iIdPinjamTransaksi.getText() + "')";
                new ConfigDB().simpanData(SQLDetail);
            }
            this.tampilTabelTransaksi();
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
    
    public void mouseClickTransaksi(int baris) throws SQLException {
        this.iIdPinjamTransaksi.setText(String.valueOf(this.tableDisplayFirst.getValueAt(baris, 1)));
        this.iIdFilmTransaksi.setText(String.valueOf(this.tableDisplayFirst.getValueAt(baris, 2)));
        this.iKeteranganTransaksi.setSelectedItem(String.valueOf(this.tableDisplayFirst.getValueAt(baris, 6)));
        try {
            this.iTanggalPinjamTransaksi.setDate(sdf.parse(String.valueOf(this.tableDisplayFirst.getValueAt(baris, 3))));
            this.iTanggalPinjamTransaksi.setDate(sdf.parse(String.valueOf(this.tableDisplayFirst.getValueAt(baris, 4))));
        } catch (ParseException ex) {
            Logger.getLogger(TransaksiUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Statement st = new ConfigDB().cn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM tb_pinjam WHERE id_pinjam='" + iIdPinjamTransaksi.getText() + "'");
        
        //BELUM
        if(rs.next()) {
            iIdAnggotaTransaksi.setText(rs.getString("id_anggota"));
        }
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
