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
    <!-- Membuat Table untuk mendisplay / menampilkan data -->
     <!-- Memanggil sebuah data dari database -->
     <?php
        $queryAmbilData = 'SELECT * FROM tb_barang';
        $arrayData = mysqli_query($conn, $queryAmbilData);
        
        // Melakukan Perulangan untuk dapat memanggil semua data di database
        foreach ($arrayData as $item) {
          // Agar kita dapat menampilkan data ke table kita perlu tutup tag php ini dan
          // membungkus sebuah <tr></tr> agar data ditampilkan di setiap baris
          ?>
    <table>
      <thead>
        <tr>
          <th>ID Barang</th>
          <th>Tanggal Barang</th>
          <th>Stok Barang</th>
          <th>Harga Barang</th>
          <th>Tanggal Transaksi</th>
          <th>Jumlah Transaksi</th>
          <th>Harga Transaksi</th>
        </tr>
      </thead>
      <tbody>
          <tr>
            <td><?php echo $item['id_barang'] ?></td>
            <td><?php echo $item['nama_barang'] ?></td>
            <td><?php echo $item['stok_barang'] ?></td>
            <td><?php echo $item['harga_barang'] ?></td>
          </tr>
      </tbody>
    </table>
    <?php
  }
  ?>
  </main>
</body>
</html>