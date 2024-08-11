<?php
include 'koneksi.php';

$previousId = $_POST['oldId'];
$newId = $_POST['newId'];
$nama = $_POST['nama_barang'];
$stok = $_POST['stok_barang'];
$harga = $_POST['harga_barang'];

$currentDate = date("Y-d-m");

$sqlBarang = "UPDATE tb_barang SET id_barang = '$newId', nama_barang = '$nama', stok_barang = $stok, harga_barang = $harga WHERE id_barang = '$previousId'";
$query = mysqli_query($conn, $sqlBarang);

header("Location: index.php");
?>