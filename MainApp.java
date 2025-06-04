package TubesPP1;

import java.util.Scanner;
import java.time.LocalDate;

// MainApp adalah program utama yang menjalankan menu aplikasi
public class MainApp {

    public static void main(String[] args) {
        // Membuat objek Scanner untuk membaca input dari keyboard
        // Penting: Scanner harus ditutup setelah selesai dipakai untuk mencegah resource leak
        Scanner scanner = new Scanner(System.in);

        // Memuat data graph dari file jika ada, jika tidak ada maka buat graph baru
        GraphBarangMatrix graph = loadGraph();

        while (true) {
            // Menampilkan menu utama ke layar
            System.out.println("\n====== MENU ======");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Lihat Semua Barang");
            System.out.println("3. Lihat Matriks Graph");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu: ");

            // Membaca pilihan menu dari user
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Buang karakter newline agar input berikutnya tidak error

            switch (pilihan) {
                case 1:
                    // Menu untuk menambah barang baru
                    System.out.print("Nama barang: ");
                    String nama = scanner.nextLine();

                    System.out.print("Tanggal masuk (yyyy-mm-dd): ");
                    LocalDate masuk = LocalDate.parse(scanner.nextLine());

                    System.out.print("Tanggal kadaluarsa (yyyy-mm-dd): ");
                    LocalDate kadaluarsa = LocalDate.parse(scanner.nextLine());

                    // Membuat objek Barang baru dari input user
                    Barang barang = new Barang(nama, masuk, kadaluarsa);
                    // Menambah barang ke graph
                    graph.tambahBarang(barang);
                    System.out.println("Barang berhasil ditambahkan.");
                    break;

                case 2:
                    // Menu untuk menampilkan semua barang
                    System.out.println("\n=== Daftar Barang ===");
                    // Loop semua barang dan tampilkan, beri keterangan jika sudah expired
                    for (Barang b : graph.getDaftarBarang()) {
                        String status = b.getTanggalKadaluarsa().isBefore(LocalDate.now()) ? " (Expired)" : "";
                        System.out.println(b + status);
                    }
                    break;

                case 3:
                    // Menu untuk menampilkan matriks relasi antar barang
                    graph.tampilkanMatriks();
                    break;

                case 4:
                    // Menu keluar, simpan data sebelum keluar
                    saveGraph(graph);
                    System.out.println("Data disimpan. Keluar dari program.");
                    scanner.close(); // Tutup scanner untuk mencegah resource leak
                    return; // Keluar dari program

                default:
                    // Jika input menu tidak valid
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // Method untuk menyimpan graph ke file (serialisasi)
    // Menyimpan seluruh data barang dan relasinya ke file "data.ser"
    private static void saveGraph(GraphBarangMatrix graph) {
        // Menggunakan try-with-resources agar file otomatis tertutup (tidak ada resource leak)
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream("data.ser"))) {
            oos.writeObject(graph); // Simpan objek graph ke file
        } catch (java.io.IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    // Method untuk memuat graph dari file, jika gagal (misal file belum ada) maka buat graph baru
    private static GraphBarangMatrix loadGraph() {
        // Menggunakan try-with-resources agar file otomatis tertutup
        try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream("data.ser"))) {
            return (GraphBarangMatrix) ois.readObject(); // Baca objek graph dari file
        } catch (java.io.IOException | ClassNotFoundException e) {
            // Jika file tidak ditemukan atau error, buat graph baru
            return new GraphBarangMatrix();
        }
    }
}