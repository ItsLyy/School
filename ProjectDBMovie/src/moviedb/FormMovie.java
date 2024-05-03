/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package moviedb;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Object;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

/**
 *
 * @author irlyfizaharis
 */

class ImageIconRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        if(value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            Image img = icon.getImage().getScaledInstance(155, 230, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);

            setIcon(scaledIcon);
            setText(null);
        } else {
            // If the value is not an ImageIcon, handle it normally
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
        return this;
    }
}


public class FormMovie extends javax.swing.JFrame {
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel tabModel;
    
    public File file = null;
    public String fname = null;
    public String path;
    public int size = 0;
    public byte[] pimage = null;
    
    private ImageIcon format = null;
    
    Connection cn = Koneksi.Koneksi();
    PreparedStatement pstmt;
    
    public void columnTitle(){
        Object[] judul = {"ID", "Cover", "Title", "Director", "Producer", "Main Character", "Publication Year", "Overview"};
        tabModel = new DefaultTableModel(null, judul);
        tbMovie.setModel(tabModel);

        tbMovie.getColumnModel().getColumn(1).setCellRenderer(new ImageIconRenderer());
        tbMovie.getColumnModel().getColumn(1).setPreferredWidth(135);
        tbMovie.getColumnModel().getColumn(7).setPreferredWidth(300);
    }
    
    public void reset(){
        inputTitle.setText("");
        inputDirector.setText("");
        inputProducer.setText("");
        inputMainCharacter.setText("");
        inputPublicationYear.setStartYear(2024);
        inputOverview.setText("");
        outputPathImage.setText("");
        outputCover.setText("");
        outputCover.setIcon(null);
        outputId.setText("");
        
        editMode(false);
    }
    
    public void displayDataToTable(String where){
        try {
            st = cn.createStatement();
            tabModel.getDataVector().removeAllElements();
            tabModel.fireTableDataChanged();
            rs = st.executeQuery("SELECT * FROM tb_movies" + where);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            
            while (rs.next()) {
                byte[] imageData = rs.getBytes("cover");
                ImageIcon imageIcon = new ImageIcon(imageData);
                Image img = imageIcon.getImage().getScaledInstance(255, 330, Image.SCALE_SMOOTH);
                ImageIcon scaledImageIcon = new ImageIcon(img);
                String path2 = "C:\\Users\\itsly\\OneDrive\\Documents\\temp\\project\\img.jpg";
                FileOutputStream foss = new FileOutputStream(path2);
                foss.write(imageData);
                foss.close();
                Object[] data = {
                    rs.getString("id"),
                    scaledImageIcon,
                    rs.getString("title"),
                    rs.getString("director"),
                    rs.getString("producer"),
                    rs.getString("main_character"),
                    dateFormat.format(rs.getDate("publication_year")),
                    rs.getString("overview"),
                };
                tabModel.addRow(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void displayDataToInput(String where){
        try {
            st = cn.createStatement();
            rs = st.executeQuery("SELECT * FROM tb_movies" + where);
            
            while (rs.next()) {
                byte[] imageData = rs.getBytes("cover");
                format = new ImageIcon(imageData);
                Image mm = format.getImage();
                Image img2 =mm.getScaledInstance(255, 330, Image.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(img2);
                String path3 = "C:\\Users\\itsly\\OneDrive\\Documents\\temp\\project\\img.jpg";
                FileOutputStream fos = new FileOutputStream(path3);
                fos.write(imageData);
                outputCover.setIcon(image);
                
                outputId.setText(rs.getString("id"));
                inputTitle.setText(rs.getString("title"));
                inputDirector.setText(rs.getString("director"));
                inputProducer.setText(rs.getString("producer"));
                inputMainCharacter.setText(rs.getString("main_character"));
                inputPublicationYear.setYear(rs.getInt("publication_year"));
                inputOverview.setText(rs.getString("overview"));
                
                System.out.println(outputId.getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String idMaker() {
        try {
            st = cn.createStatement();
            rs = st.executeQuery("SELECT id FROM tb_movies ORDER BY id DESC LIMIT 1");
            String id = "";
            long fullCode;
            String firstCode;
            int lastCode = 0;
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            
            while (rs.next()) {
                fullCode = (rs.getLong("id")) + 1;
                System.out.print(fullCode);
                id = String.valueOf(fullCode);
                return id;
            }
            
            firstCode = dateFormat.format(currentDate);
            id = firstCode + lastCode;
            
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void editMode(boolean state) {
        if (state == true) {
            btSimpan.setEnabled(false);
            btHapus.setEnabled(true);
            btUpdate.setEnabled(true);
        } else {
            btSimpan.setEnabled(true);
            btHapus.setEnabled(false);
            btUpdate.setEnabled(false);
        }
    }
    
    public static void uploadImageToDatabase(String imagePath) {
        Connection conn = Koneksi.Koneksi();
        PreparedStatement pstmt = null;
        FileInputStream fis = null;

        try {
            // Creating a PreparedStatement
            String query = "INSERT INTO tb_movies (id, cover) VALUES (?, ?)";
            pstmt = conn.prepareStatement(query);

            // Setting the image name
            File image = new File(imagePath);
            pstmt.setString(1, "192847");

            // Setting the image data
            fis = new FileInputStream(image);
            pstmt.setBinaryStream(2, (InputStream) fis, (int) image.length());

            // Executing the query
            pstmt.executeUpdate();
            System.out.println("Image uploaded successfully to the database!");

        } catch (SQLException | FileNotFoundException e) {
        } finally {
            try {
                if (fis != null) fis.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void uploadCover() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fnwf = new FileNameExtensionFilter("PNG JPG AND JPEG","png","jpeg","jpg");
        fileChooser.addChoosableFileFilter (fnwf);
        int load = fileChooser.showOpenDialog(null);
        if (load==fileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();

            path = file.getAbsolutePath();
            outputPathImage.setText(path);
            ImageIcon ii = new ImageIcon(path);
            Image img = ii.getImage().getScaledInstance (255, 330, Image.SCALE_SMOOTH);
            outputCover.setIcon(new ImageIcon(img));
        }
    }
    
    

    /**
     * Creates new form FormSiswa
     */
    public FormMovie() {
        initComponents();
        columnTitle();
        displayDataToTable("");
        btHapus.setEnabled(false);
        btUpdate.setEnabled(false);
        
        
        outputCover.setText("");
        outputId.setText("");
        
        outputPathImage.setEditable(false);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        inputTitle = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        inputDirector = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputOverview = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        btSimpan = new javax.swing.JButton();
        btHapus = new javax.swing.JButton();
        btUpdate = new javax.swing.JButton();
        btReset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbMovie = new javax.swing.JTable();
        inputSearch = new javax.swing.JTextField();
        btSearch = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        inputProducer = new javax.swing.JTextField();
        inputMainCharacter = new javax.swing.JTextField();
        inputPublicationYear = new com.toedter.calendar.JYearChooser();
        outputId = new javax.swing.JLabel();
        outputCover = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        outputPathImage = new javax.swing.JTextField();
        btUpload = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("MOVIE LIST");

        inputTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputTitleActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setLabelFor(inputTitle);
        jLabel2.setText("Title");

        inputDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputDirectorActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setLabelFor(inputDirector);
        jLabel3.setText("Director");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setLabelFor(inputDirector);
        jLabel4.setText("Producer");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setLabelFor(inputDirector);
        jLabel5.setText("Main Character");

        inputOverview.setColumns(20);
        inputOverview.setRows(5);
        jScrollPane1.setViewportView(inputOverview);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setLabelFor(inputDirector);
        jLabel6.setText("Overview");

        btSimpan.setText("Simpan");
        btSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSimpanActionPerformed(evt);
            }
        });

        btHapus.setText("Hapus");
        btHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHapusActionPerformed(evt);
            }
        });

        btUpdate.setText("Update");
        btUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateActionPerformed(evt);
            }
        });

        btReset.setText("Reset");
        btReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btResetActionPerformed(evt);
            }
        });

        tbMovie.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Cover", "Title", "Director", "Producer", "Main Character", "Publication Year", "Overview"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbMovie.setRowHeight(250);
        tbMovie.setRowSelectionAllowed(false);
        tbMovie.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbMovieFocusGained(evt);
            }
        });
        tbMovie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMovieMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbMovie);

        inputSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputSearchActionPerformed(evt);
            }
        });

        btSearch.setText("Search");
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setLabelFor(inputDirector);
        jLabel7.setText("Publication Year");

        inputProducer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputProducerActionPerformed(evt);
            }
        });

        inputMainCharacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputMainCharacterActionPerformed(evt);
            }
        });

        inputPublicationYear.setMinimum(0);
        inputPublicationYear.setValue(0);

        outputId.setText("ID Display");

        outputCover.setBackground(new java.awt.Color(51, 51, 51));
        outputCover.setText("Cover");
        outputCover.setMaximumSize(new java.awt.Dimension(255, 330));
        outputCover.setMinimumSize(new java.awt.Dimension(255, 330));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setLabelFor(inputTitle);
        jLabel8.setText("Cover");

        outputPathImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputPathImageActionPerformed(evt);
            }
        });

        btUpload.setText("Upload");
        btUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUploadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(outputId, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 97, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(outputPathImage)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(inputTitle)
                            .addComponent(inputDirector)
                            .addComponent(jScrollPane1)
                            .addComponent(inputProducer)
                            .addComponent(inputMainCharacter)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btReset, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 235, Short.MAX_VALUE))
                            .addComponent(inputPublicationYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addComponent(outputCover, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(outputPathImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(btUpload))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(inputProducer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(inputMainCharacter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inputPublicationYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(outputCover, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btUpdate)
                    .addComponent(btReset)
                    .addComponent(btHapus)
                    .addComponent(btSimpan)
                    .addComponent(outputId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(inputSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputTitleActionPerformed

    private void inputDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputDirectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputDirectorActionPerformed

    private void btHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHapusActionPerformed
        // TODO add your handling code here:
        try {
            st = cn.createStatement();
            st.executeUpdate("DELETE FROM tb_movies WHERE id=" + outputId.getText());
            displayDataToTable("");
            JOptionPane.showMessageDialog(null, "Hapus Berhasil");
            reset();
        } catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_btHapusActionPerformed

    private void btSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSimpanActionPerformed
        // TODO add your handling code here:
        System.out.print(path);
        try{
            String query = "INSERT INTO tb_movies (id, cover, title, director, producer, main_character, publication_year, overview) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = cn.prepareStatement(query);

            // Setting the parameters
            pstmt.setString(1, idMaker()); // Assuming you have a method to generate the ID
            File image = new File(path);
            FileInputStream fis = new FileInputStream(image);
            pstmt.setBinaryStream(2, fis, (int) image.length());
            pstmt.setString(3, inputTitle.getText());
            pstmt.setString(4, inputDirector.getText());
            pstmt.setString(5, inputProducer.getText());
            pstmt.setString(6, inputMainCharacter.getText());
            pstmt.setInt(7, inputPublicationYear.getYear());
            pstmt.setString(8, inputOverview.getText());
            
            pstmt.executeUpdate();
            
            displayDataToTable("");
            JOptionPane.showMessageDialog(null, "Simpan Berhasil");
            reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btSimpanActionPerformed

    private void tbMovieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMovieMouseClicked
        // TODO add your handling code here:
        String id = tbMovie.getValueAt(tbMovie.getSelectedRow(), 0).toString();
        displayDataToInput(" WHERE id = '" + id + "'");
        
        editMode(true);
    }//GEN-LAST:event_tbMovieMouseClicked

    private void btResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btResetActionPerformed

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        // TODO add your handling code here:
        try {
            String query = "UPDATE tb_movies SET id=?, cover=?, title=?, director=?, producer=?, main_character=?, publication_year=?, overview=? WHERE id=?";
            pstmt = cn.prepareStatement(query);

            // Setting the parameters
            pstmt.setString(1, idMaker());
            File image = new File(path);
            FileInputStream fis = new FileInputStream(image);
            pstmt.setBinaryStream(2, fis, (int) image.length());
            pstmt.setString(3, inputTitle.getText());
            pstmt.setString(4, inputDirector.getText());
            pstmt.setString(5, inputProducer.getText());
            pstmt.setString(6, inputMainCharacter.getText());
            pstmt.setInt(7, (int) inputPublicationYear.getYear());
            pstmt.setString(8, inputOverview.getText());
            pstmt.setString(9, outputId.getText().toString());
            
            pstmt.executeUpdate();
            
            displayDataToTable("");
            JOptionPane.showMessageDialog(null, "Update Berhasil");
            reset();
        } catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_btUpdateActionPerformed

    private void inputSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputSearchActionPerformed

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        // TODO add your handling code here:
        try {
            st = cn.createStatement();
            System.out.println(inputSearch.getText().toString());
            displayDataToTable(" WHERE nama LIKE '%" + inputSearch.getText().toString() + "%'");
            JOptionPane.showMessageDialog(null, "Search Ditemukan");
            reset();
        } catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_btSearchActionPerformed

    private void inputProducerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputProducerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputProducerActionPerformed

    private void inputMainCharacterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputMainCharacterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputMainCharacterActionPerformed

    private void outputPathImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputPathImageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_outputPathImageActionPerformed

    private void btUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUploadActionPerformed
        // TODO add your handling code here:
        uploadCover();
    }//GEN-LAST:event_btUploadActionPerformed

    private void tbMovieFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbMovieFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tbMovieFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormMovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMovie().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btHapus;
    private javax.swing.JButton btReset;
    private javax.swing.JButton btSearch;
    private javax.swing.JButton btSimpan;
    private javax.swing.JButton btUpdate;
    private javax.swing.JButton btUpload;
    private javax.swing.JTextField inputDirector;
    private javax.swing.JTextField inputMainCharacter;
    private javax.swing.JTextArea inputOverview;
    private javax.swing.JTextField inputProducer;
    private com.toedter.calendar.JYearChooser inputPublicationYear;
    private javax.swing.JTextField inputSearch;
    private javax.swing.JTextField inputTitle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel outputCover;
    private javax.swing.JLabel outputId;
    private javax.swing.JTextField outputPathImage;
    private javax.swing.JTable tbMovie;
    // End of variables declaration//GEN-END:variables
}
