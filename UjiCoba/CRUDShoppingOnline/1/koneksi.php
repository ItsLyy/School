<?php
  $dbHost = 'localhost';
  $dbName = 'db_ujicoba_lsp';
  $dbUsername = 'root';
  $dbPassword = '';

  $conn = mysqli_connect($dbHost, $dbUsername, $dbPassword, $dbName);

  // Opsional, digunakan untuk memeriksa sudah bisa atau belum dan jika ada suatu error akan ditampilkan
  if ($conn) {
    echo 'Koneksi Sukses';
  } else {
    die("Koneksi Gagal: " . mysqli_connect_error());
  }
?>