package TubesPP1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Kelas GraphBarangMatrix menyimpan daftar barang dan relasinya dalam bentuk matriks adjacency
// Serializable supaya objek GraphBarangMatrix bisa disimpan ke file (serialisasi)
public class GraphBarangMatrix implements Serializable {
    // List untuk menyimpan semua barang yang ada di graph
    private List<Barang> daftarBarang;
    // Matriks adjacency untuk menyimpan relasi antar barang (misal: urutan kadaluarsa)
    private int[][] adjacencyMatrix;

    // Konstruktor: inisialisasi list barang dan matriks adjacency
    public GraphBarangMatrix() {
        daftarBarang = new ArrayList<>(); // Membuat list kosong untuk barang
        adjacencyMatrix = new int[100][100]; // Matriks 100x100, maksimal 100 barang
    }

    // Method untuk menambah barang baru ke graph
    // Juga mengatur relasi (edge) di matriks adjacency berdasarkan tanggal kadaluarsa
    public void tambahBarang(Barang barang) {
        daftarBarang.add(barang); // Tambah barang ke list

        // indexBaru adalah posisi barang yang baru ditambahkan
        int indexBaru = daftarBarang.size() - 1;
        // Loop semua barang yang sudah ada sebelumnya
        for (int i = 0; i < daftarBarang.size() - 1; i++) {
            Barang b = daftarBarang.get(i);

            // Jika barang baru kadaluarsa lebih dulu, buat edge dari barang baru ke barang lama
            if (barang.getTanggalKadaluarsa().isBefore(b.getTanggalKadaluarsa())) {
                adjacencyMatrix[indexBaru][i] = 1;
            } 
            // Jika barang baru kadaluarsa lebih lama, buat edge dari barang lama ke barang baru
            else if (barang.getTanggalKadaluarsa().isAfter(b.getTanggalKadaluarsa())) {
                adjacencyMatrix[i][indexBaru] = 1;
            }
            // Jika tanggal kadaluarsa sama, tidak dibuat edge (biarkan 0)
        }
    }

    // Method untuk menampilkan semua barang yang ada di graph
    public void tampilkanBarang() {
        // Loop semua barang dan tampilkan satu per satu
        for (int i = 0; i < daftarBarang.size(); i++) {
            System.out.println((i + 1) + ". " + daftarBarang.get(i));
        }
    }

    // Method untuk menampilkan matriks adjacency (relasi antar barang)
    public void tampilkanMatriks() {
        System.out.println("\nAdjacency Matrix:");
        // Tampilkan header kolom (nama barang)
        System.out.print("     ");
        for (Barang b : daftarBarang) {
            System.out.print(b.getNama() + "\t");
        }
        System.out.println();

        // Tampilkan setiap baris matriks
        for (int i = 0; i < daftarBarang.size(); i++) {
            // Tampilkan nama barang di awal baris
            System.out.print(daftarBarang.get(i).getNama() + "\t");
            // Tampilkan isi matriks untuk baris ini
            for (int j = 0; j < daftarBarang.size(); j++) {
                System.out.print(adjacencyMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // Getter untuk mengambil list semua barang
    public List<Barang> getDaftarBarang() {
        return daftarBarang;
    }
}