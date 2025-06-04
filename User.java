package TubesPP1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Kelas User untuk menyimpan data akun pengguna
// Pada aplikasi ini, setiap user punya username dan password (password disimpan dalam bentuk hash, bukan teks asli)
// Hashing password bertujuan agar password tidak mudah dicuri walaupun ada yang bisa mengakses data di memori
public class User {
    private String username;
    private String passwordHash; // Simpan hash, bukan password polos

    // Konstruktor User: menerima username dan password asli, lalu password langsung di-hash
    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    // Constructor untuk user yang password-nya sudah dalam bentuk hash (untuk load dari file)
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
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // hasil hash dalam bentuk string heksadesimal
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available");
        }
    }

    // Fungsi untuk cek apakah password yang dimasukkan user saat login cocok dengan hash yang tersimpan
    // Caranya: password input di-hash dulu, lalu dibandingkan dengan hash yang sudah disimpan
    public boolean checkPassword(String passwordInput) {
        return this.passwordHash.equals(hashPassword(passwordInput));
    }
}
