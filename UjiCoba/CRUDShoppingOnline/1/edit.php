<?php
include 'koneksi.php';
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit</title>
</head>
<body>
  <form action="edit_proses.php" method="POST">
    <table>
      <?php
      $id = $_GET['id'];
      $sql = "SELECT * FROM tb_barang WHERE id_barang = '$id'";
      $query = mysqli_query($conn, $sql);
      $arrayData = mysqli_fetch_assoc($query);
      ?>
      <tr>
        <th>ID</th>
        <td><input type="text" value="<?php echo $arrayData['id_barang']; ?>" name="newId"></td>
      </tr>
      <tr>
        <th>Nama</th>
        <td><input type="text" value="<?php echo $arrayData['nama_barang']; ?>" name="nama_barang"></td>
      </tr>
      <tr>
        <th>Stok</th>
        <td><input type="text" value="<?php echo $arrayData['stok_barang']; ?>" name="stok_barang"></td>
      </tr>
      <tr>
        <th>Harga</th>
        <td><input type="text" value="<?php echo $arrayData['harga_barang']; ?>" name="harga_barang"></td>
      </tr>
    </table>
    <input type="hidden" name="oldId" value="<?php echo $arrayData['id_barang']; ?>">
    <input type="submit" value="Beli">
  </form>
  
</body>
</html>