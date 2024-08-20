-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 20, 2024 at 04:19 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_javaproject`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_anggota`
--

CREATE TABLE `tb_anggota` (
  `id_anggota` varchar(8) NOT NULL,
  `nama` varchar(40) DEFAULT NULL,
  `tempat_lahir` varchar(15) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `jkl` varchar(15) DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `telp` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_detail_pinjam`
--

CREATE TABLE `tb_detail_pinjam` (
  `id_detail_pinjam` varchar(8) NOT NULL,
  `kode_film` varchar(5) DEFAULT NULL,
  `tgl_pinjam` date DEFAULT NULL,
  `tgl_kembali` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  `denda` int(11) DEFAULT NULL,
  `id_pinjam` varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_film`
--

CREATE TABLE `tb_film` (
  `kode_film` varchar(5) NOT NULL,
  `judul` varchar(30) DEFAULT NULL,
  `genre` varchar(20) DEFAULT NULL,
  `tahun` int(4) DEFAULT NULL,
  `asal` varchar(15) DEFAULT NULL,
  `stok` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_pinjam`
--

CREATE TABLE `tb_pinjam` (
  `id_pinjam` varchar(8) NOT NULL,
  `id_anggota` varchar(8) DEFAULT NULL,
  `total_denda` int(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Stand-in structure for view `view_pinjam`
-- (See below for the actual view)
--
CREATE TABLE `view_pinjam` (
`id_pinjam` varchar(8)
,`id_anggota` varchar(8)
,`nama` varchar(40)
,`kode_film` varchar(5)
,`judul` varchar(30)
,`tgl_pinjam` date
,`tgl_kembali` date
,`status` varchar(20)
,`keterangan` text
,`denda` int(11)
);

-- --------------------------------------------------------

--
-- Structure for view `view_pinjam`
--
DROP TABLE IF EXISTS `view_pinjam`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_pinjam`  AS SELECT DISTINCT `tb_pinjam`.`id_pinjam` AS `id_pinjam`, `tb_pinjam`.`id_anggota` AS `id_anggota`, `tb_anggota`.`nama` AS `nama`, `tb_detail_pinjam`.`kode_film` AS `kode_film`, `tb_film`.`judul` AS `judul`, `tb_detail_pinjam`.`tgl_pinjam` AS `tgl_pinjam`, `tb_detail_pinjam`.`tgl_kembali` AS `tgl_kembali`, `tb_detail_pinjam`.`status` AS `status`, `tb_detail_pinjam`.`keterangan` AS `keterangan`, `tb_detail_pinjam`.`denda` AS `denda` FROM (((`tb_anggota` join `tb_pinjam` on(`tb_anggota`.`id_anggota` = `tb_pinjam`.`id_anggota`)) join `tb_detail_pinjam` on(`tb_detail_pinjam`.`id_pinjam` = `tb_pinjam`.`id_pinjam`)) join `tb_film` on(`tb_film`.`kode_film` = `tb_detail_pinjam`.`kode_film`)) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_anggota`
--
ALTER TABLE `tb_anggota`
  ADD PRIMARY KEY (`id_anggota`);

--
-- Indexes for table `tb_detail_pinjam`
--
ALTER TABLE `tb_detail_pinjam`
  ADD PRIMARY KEY (`id_detail_pinjam`),
  ADD KEY `kode_film` (`kode_film`),
  ADD KEY `id_pinjam` (`id_pinjam`);

--
-- Indexes for table `tb_film`
--
ALTER TABLE `tb_film`
  ADD PRIMARY KEY (`kode_film`);

--
-- Indexes for table `tb_pinjam`
--
ALTER TABLE `tb_pinjam`
  ADD PRIMARY KEY (`id_pinjam`),
  ADD KEY `id_anggota` (`id_anggota`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_detail_pinjam`
--
ALTER TABLE `tb_detail_pinjam`
  ADD CONSTRAINT `tb_detail_pinjam_ibfk_1` FOREIGN KEY (`kode_film`) REFERENCES `tb_film` (`kode_film`),
  ADD CONSTRAINT `tb_detail_pinjam_ibfk_2` FOREIGN KEY (`id_pinjam`) REFERENCES `tb_pinjam` (`id_pinjam`);

--
-- Constraints for table `tb_pinjam`
--
ALTER TABLE `tb_pinjam`
  ADD CONSTRAINT `tb_pinjam_ibfk_1` FOREIGN KEY (`id_anggota`) REFERENCES `tb_anggota` (`id_anggota`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
