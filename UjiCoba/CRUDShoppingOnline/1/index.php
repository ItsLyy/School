<?php
include 'koneksi.php';
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Uji Coba LSP</title>
</head>
<body>
  <main>
    <a href="riwayat.php">R I W A Y A T</a>
    <a href="tambah.php">T A M B A H</a>
    <!-- Membuat Table untuk mendisplay / menampilkan data -->
    <table>
      <thead>
        <tr>
          <th>ID Barang</th>
          <th>Nama Barang</th>
          <th>Stok Barang</th>
          <th>Harga Barang</th>
        </tr>
      </thead>
      <tbody>
        <!-- Memanggil sebuah data dari database -->
        <?php
        $queryAmbilData = 'SELECT * FROM tb_barang';
        $arrayData = mysqli_query($conn, $queryAmbilData);
        
        // Melakukan Perulangan untuk dapat memanggil semua data di database
        foreach ($arrayData as $item) {
          // Agar kita dapat menampilkan data ke table kita perlu tutup tag php ini dan
          // membungkus sebuah <tr></tr> agar data ditampilkan di setiap baris
          ?>
          <tr>
            <td><?php echo $item['id_barang'] ?></td>
            <td><?php echo $item['nama_barang'] ?></td>
            <td><?php echo $item['stok_barang'] ?></td>
            <td><?php echo $item['harga_barang'] ?></td>
            <td><a href="beli.php?id=<?php echo $item['id_barang'] ?>">B E L I</a> | <a href="edit.php?id=<?php echo $item['id_barang'] ?>">E D I T</a> | <a href="hapus_proses.php?id=<?php echo $item['id_barang'] ?>">D E L E T E</a></td>
          </tr>
          <?php
        }
        ?>
      </tbody>
    </table>
  </main>
</body>
</html>