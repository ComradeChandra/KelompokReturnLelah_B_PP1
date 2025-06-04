package TubesPP1;

import java.io.Serializable;
import java.time.LocalDate;

// Kelas Barang menyimpan data nama, tanggal masuk, dan tanggal kadaluarsa barang
// Serializable supaya objek Barang bisa disimpan ke file (serialisasi)
public class Barang implements Serializable { // Perbaiki: huruf besar di awal nama class
    // Variabel untuk menyimpan nama barang
    private String nama;
    // Variabel untuk menyimpan tanggal barang masuk
    private LocalDate tanggalMasuk;
    // Variabel untuk menyimpan tanggal kadaluarsa barang
    private LocalDate tanggalKadaluarsa;

    // Konstruktor untuk membuat objek barang baru
    // Parameter: nama barang, tanggal masuk, tanggal kadaluarsa
    public Barang(String nama, LocalDate tanggalMasuk, LocalDate tanggalKadaluarsa) { // Perbaiki: huruf besar di awal nama konstruktor
        this.nama = nama;
        this.tanggalMasuk = tanggalMasuk;
        this.tanggalKadaluarsa = tanggalKadaluarsa;
    }

    // Getter untuk mengambil nama barang
    public String getNama() {
        return nama;
    }

    // Setter untuk mengubah nama barang
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter untuk mengambil tanggal masuk barang
    public LocalDate getTanggalMasuk() {
        return tanggalMasuk;
    }

    // Setter untuk mengubah tanggal masuk barang
    public void setTanggalMasuk(LocalDate tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    // Getter untuk mengambil tanggal kadaluarsa barang
    public LocalDate getTanggalKadaluarsa() {
        return tanggalKadaluarsa;
    }

    // Setter untuk mengubah tanggal kadaluarsa barang
    public void setTanggalKadaluarsa(LocalDate tanggalKadaluarsa) {
        this.tanggalKadaluarsa = tanggalKadaluarsa;
    }

    // Fungsi untuk menampilkan data barang dalam bentuk string
    // Akan dipanggil saat objek Barang dicetak
    @Override
    public String toString() {
        return "Nama: " + nama +
               ", Masuk: " + tanggalMasuk +
               ", Kadaluarsa: " + tanggalKadaluarsa;
    }

    // Fungsi untuk membandingkan dua objek barang
    // Barang dianggap sama jika nama dan tanggal kadaluarsanya sama
    @Override
    public boolean equals(Object obj) {
        // Jika objeknya sama persis, langsung true
        if (this == obj) return true;
        // Jika objek null atau beda class, return false
        if (obj == null || getClass() != obj.getClass()) return false;
        // Casting objek ke Barang
        Barang barang = (Barang) obj;
        // Bandingkan nama dan tanggal kadaluarsa
        return nama.equals(barang.nama) &&
               tanggalKadaluarsa.equals(barang.tanggalKadaluarsa);
    }

    // Fungsi hashCode untuk mendukung penggunaan di struktur data seperti HashMap/HashSet
    @Override
    public int hashCode() {
        // Menghasilkan angka unik berdasarkan nama dan tanggal kadaluarsa
        return nama.hashCode() + tanggalKadaluarsa.hashCode();
    }
}
