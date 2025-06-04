package TubesPP1;

// Kelas User untuk menyimpan data akun pengguna
public class User {
    private String username;
    private String password; // Untuk latihan, password disimpan polos

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
