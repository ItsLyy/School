<?php
include 'koneksi.php';

$id = $_GET['id'];

$sqlTransaksi = "DELETE FROM tb_transaksi WHERE id_barang = '$id'";
$sqlBarang = "DELETE FROM tb_barang WHERE id_barang = '$id'";
$query = mysqli_query($conn, $sqlTransaksi);
$query = mysqli_query($conn, $sqlBarang);

header("Location: index.php");
?>