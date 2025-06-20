package TubesPP1;

import java.security.MessageDigest;           // Untuk melakukan hashing password
import java.security.NoSuchAlgorithmException; // Untuk menangani error jika algoritma hashing tidak ditemukan

// Kelas User untuk menyimpan data akun pengguna
// Pada aplikasi ini, setiap user punya username dan password (password disimpan dalam bentuk hash, bukan teks asli)
// Hashing password bertujuan agar password tidak mudah dicuri walaupun ada yang bisa mengakses data di memori
public class User {
    private String username;        // Menyimpan username user
    private String passwordHash;    // Menyimpan hash password, bukan password polos

    // Konstruktor User: menerima username dan password asli, lalu password langsung di-hash
    // Digunakan saat user baru mendaftar
    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password); // Password langsung di-hash saat disimpan
    }

    // Konstruktor untuk user yang password-nya sudah dalam bentuk hash (untuk load dari file)
    // Digunakan saat membaca data user dari file (karena di file sudah dalam bentuk hash)
    public User(String username, String passwordHash, boolean isHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Mengambil username user
    public String getUsername() { return username; }

    // Mengambil hash password (bukan password asli)
    public String getPasswordHash() { return passwordHash; }

    // Fungsi hashing password dengan algoritma SHA-256
    // Fungsi ini mengubah password asli menjadi kode acak (hash) yang tidak bisa dikembalikan ke bentuk aslinya
    // Tujuannya agar password lebih aman, karena hash tidak bisa dibalik ke password asli
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Membuat objek untuk hashing SHA-256
            byte[] hash = md.digest(password.getBytes()); // Hash password jadi array byte
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b)); // Ubah setiap byte ke string heksadesimal (misal: "a3f2...")
            }
            return sb.toString(); // hasil hash dalam bentuk string heksadesimal
        } catch (NoSuchAlgorithmException e) {
            // Exception ini terjadi jika algoritma SHA-256 tidak tersedia di sistem (sangat jarang terjadi)
            throw new RuntimeException("SHA-256 not available");
        }
    }

    // Fungsi untuk cek apakah password yang dimasukkan user saat login cocok dengan hash yang tersimpan
    // Caranya: password input di-hash dulu, lalu dibandingkan dengan hash yang sudah disimpan
    public boolean checkPassword(String passwordInput) {
        return this.passwordHash.equals(hashPassword(passwordInput));
    }
}
