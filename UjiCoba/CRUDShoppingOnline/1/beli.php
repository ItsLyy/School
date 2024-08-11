<?php
include 'koneksi.php';
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Pembelian</title>
</head>
<body>
  <form action="beli_proses.php" method="POST">
    <table>
      <?php
      $id = $_GET['id'];
      $sql = "SELECT * FROM tb_barang WHERE id_barang = '$id'";
      $query = mysqli_query($conn, $sql);
      $arrayData = mysqli_fetch_assoc($query);
      ?>
      <tr>
        <th>Nama</th>
        <td><input type="text" value="<?php echo $arrayData['nama_barang']; ?>" readonly name="nama_barang"></td>
      </tr>
      <tr>
        <th>Stok</th>
        <td><input type="text" value="<?php echo $arrayData['stok_barang']; ?>" readonly name="stok_barang"></td>
      </tr>
      <tr>
        <th>Harga</th>
        <td><input type="text" value="<?php echo $arrayData['harga_barang']; ?>" readonly name="harga_barang"></td>
      </tr>
      <tr>
        <th>Jumlah</th>
        <td><input type="number" name="jumlah_transaksi"></td>
      </tr>
      <tr>
        <th>Bayar</th>
        <td><input type="number" name="bayar_transaksi"></td>
      </tr>
      <tr>
        <th>Keterangan</th>
        <td><textarea type="text" name="keterangan_transaksi"></textarea></td>
      </tr>
    </table>
    <input type="hidden" name="id" value="<?php echo $arrayData['id_barang']; ?>">
    <input type="submit" value="Beli">
  </form>
  
</body>
</html>