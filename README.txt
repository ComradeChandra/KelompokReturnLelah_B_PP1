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

8. Antarmuka Menu Sederhana  
   Semua fitur diakses lewat menu di terminal/command prompt.
   Input mudah, tinggal ketik angka sesuai menu.

9. Registrasi, Login, dan Penyimpanan User
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

---

Kenapa Pakai Graph?

- Graph cocok untuk data yang saling terhubung, seperti batch barang yang punya urutan proses atau dependensi.
- Dengan graph, kita bisa melacak alur batch, mencari batch yang terkait, dan memantau status kadaluarsa dengan lebih mudah.

---

Catatan

- Data user akan tetap ada walaupun aplikasi ditutup, karena disimpan di file `users.txt`.


