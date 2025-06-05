SISTEM PENCATATAN BARANG KADALUARSA BERBASIS GRAPH

Apa Aplikasi Ini?

Aplikasi ini adalah program sederhana berbasis Java untuk mencatat, memantau, dan melacak batch barang di gudang yang punya tanggal kadaluarsa.
Setiap batch barang dicatat lengkap: ID, nama produk, tanggal produksi, tanggal kadaluarsa, jumlah, dan statusnya.
Aplikasi ini juga bisa menampilkan hubungan antar batch (misal: batch yang saling terkait), serta membantu mencari batch yang sudah atau hampir kadaluarsa.

---


Fitur Utama

1. Tambah Batch Barang Baru  
   Catat batch baru dengan ID, nama produk, tanggal produksi, tanggal kadaluarsa, dan jumlah barang.

2. Tambah Hubungan Antar Batch (Edge)  
   Hubungkan dua batch jika memang ada kaitan (misal: batch hasil re-packing, atau batch lanjutan).

3. Tampilkan Adjacency Matrix  
   Lihat tabel hubungan antar batch.  
   Setiap baris dan kolom adalah batch, angka 1 artinya ada hubungan, 0 artinya tidak ada.

4. Cari Batch Mendekati Kadaluarsa  
   Masukkan berapa hari ke depan, aplikasi akan menampilkan batch yang akan kadaluarsa dalam waktu itu.

5. Cari Batch yang Sudah Kadaluarsa  
   Tampilkan semua batch yang sudah melewati tanggal kadaluarsa.

6. Tampilkan Semua Batch  
   Lihat semua batch yang sudah dicatat, lengkap dengan statusnya.

7. Lacak Alur/Dependensi Batch (BFS/DFS)  
   Bisa menelusuri hubungan antar batch secara berurutan (BFS) atau menyelam ke dalam (DFS).
   Cocok untuk melihat urutan proses atau tracing batch.

8. Edit dan Hapus Batch  
   Sekarang user bisa mengedit data batch (nama produk, tanggal, jumlah) atau menghapus batch dari sistem.

9. Penyimpanan Data Batch ke File  
   Semua data batch akan disimpan otomatis ke file `batches.txt`.  
   Data batch tetap ada walaupun aplikasi ditutup dan akan dimuat ulang saat aplikasi dijalankan kembali.

10. Antarmuka Menu Sederhana  
    Semua fitur diakses lewat menu di terminal/command prompt.
    Input mudah, tinggal ketik angka sesuai menu.

11. Registrasi, Login, dan Penyimpanan User  
    Setiap user harus registrasi dan login sebelum menggunakan aplikasi.  
    Data user (username dan password hash) disimpan otomatis ke file `users.txt`.  
    Saat aplikasi dijalankan ulang, user yang sudah terdaftar bisa langsung login tanpa registrasi ulang.

---

Cara Kerja Singkat

- Setiap batch adalah simpul (vertex) di dalam graph.
- Hubungan antar batch (misal: batch A diproses jadi batch B) dicatat sebagai edge di graph.
- Tanggal kadaluarsa dipantau otomatis, status batch akan berubah jika sudah/hampir kadaluarsa.
- Pencarian batch yang kadaluarsa atau tracing hubungan batch dilakukan dengan algoritma graph (BFS/DFS).
- **Data user disimpan di file `users.txt`** dengan format:  
  `username;hash_password`  
  Password tidak disimpan dalam bentuk asli, tapi di-hash agar lebih aman.
- **Data batch disimpan di file `batches.txt`** dengan format:  
  `batchId;productName;productionDate;expiryDate;quantity;status`

---

Cara Menjalankan

1. Buka folder TubesPP1 di VS Code atau terminal.
2. Compile semua file Java:
      javac TubesPP1\*.java
3. Jalankan program:
      java TubesPP1.Main
4. Ikuti menu yang muncul di layar.

---

File Penting

- Main.java  
  Titik masuk aplikasi, berisi menu dan logika utama.

- BatchVertex.java  
  Kelas untuk menyimpan data satu batch barang.

- InventoryGraph.java  
  Kelas untuk menyimpan semua batch dan hubungan antar batch dalam bentuk graph/matriks.

- DateUtil.java  
  Kelas bantu untuk parsing dan format tanggal.

- User.java  
  Kelas untuk menyimpan data user, termasuk hashing password.

- users.txt  
  File untuk menyimpan data user yang sudah registrasi (username dan hash password).

- batches.txt  
  File untuk menyimpan data batch barang yang sudah dicatat.

---

Kenapa Pakai Graph?

- Graph cocok untuk data yang saling terhubung, seperti batch barang yang punya urutan proses atau dependensi.
- Dengan graph, kita bisa melacak alur batch, mencari batch yang terkait, dan memantau status kadaluarsa dengan lebih mudah.

---

Catatan

- Data user akan tetap ada walaupun aplikasi ditutup, karena disimpan di file `users.txt`.
- Data batch juga akan tetap ada walaupun aplikasi ditutup, karena disimpan di file `batches.txt`.
- Sekarang aplikasi sudah mendukung fitur edit dan hapus batch.



Panduan singkat untuk mengakses dan menjalankan proyek dari repository adalah sebagai berikut:

1. Mengunduh Kode Sumber: Pengguna dapat mengunduh seluruh kode sumber proyek dengan melakukan cloning repository menggunakan perintah Git:  

git clone https://github.com/ComradeChandra/KelompokReturnLelah_B_PP1.git


Atau, dapat juga mengunduh langsung berkas ZIP dari halaman GitHub repository dengan mengklik tombol "Code" dan memilih "Download ZIP".


2. Membuka Proyek: Setelah berhasil diunduh, navigasi ke dalam folder proyek (KelompokReturnLelah_B_PP1). Proyek ini dapat dibuka menggunakan Integrated Development Environment (IDE) seperti VS Code atau IntelliJ IDEA, atau langsung diakses melalui terminal/command prompt.


3. Menjalankan Aplikasi: Aplikasi ini dibangun dengan Java. Untuk menjalankan aplikasi, pastikan Java Development Kit (JDK) sudah terinstal. Kompilasi file Java kemudian jalankan kelas Main.java dari terminal:


cd KelompokReturnLelah_B_PP1/TubesPP1
javac *.java
java Main

Atau, jika menggunakan IDE, jalankan kelas Main secara langsung dari IDE tersebut.
Dokumentasi ini diharapkan dapat memberikan pemahaman yang jelas mengenai struktur dan fungsionalitas sistem, serta memfasilitasi pihak lain yang ingin meninjau atau mengembangkan lebih lanjut proyek ini.



