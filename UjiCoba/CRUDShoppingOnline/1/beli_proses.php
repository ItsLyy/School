<?php
include 'koneksi.php';

$id = $_POST['id'];
$nama = $_POST['nama_barang'];
$stok = $_POST['stok_barang'];
$harga = $_POST['harga_barang'];
$jumlah = $_POST['jumlah_transaksi'];
$bayar = $_POST['bayar_transaksi'];
$keterangan = $_POST['keterangan_transaksi'];

$currentStok = $stok - $jumlah;

$currentDate = date("Y-d-m");

$sqlTransaksi = "INSERT INTO tb_transaksi VALUES((SELECT id_barang FROM tb_barang WHERE id_barang = '$id'), '$currentDate', $jumlah, $bayar, '$keterangan')";
$sqlBarang = "UPDATE tb_barang SET stok_barang = $currentStok WHERE id_barang = '$id'";
$query = mysqli_query($conn, $sqlTransaksi);
$query = mysqli_query($conn, $sqlBarang);
header('Location: index.php');
?>