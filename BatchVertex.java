package TubesPP1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Kelas BatchVertex mewakili satu batch barang di gudang
// Setiap batch punya ID, nama produk, tanggal produksi, tanggal kadaluarsa, jumlah, status, dan penanda kunjungan (untuk graph)
public class BatchVertex {
    public String batchId;           // ID unik batch, misal: B001
    public String productName;       // Nama produk, misal: Susu Ultra
    public LocalDate productionDate; // Tanggal produksi batch
    public LocalDate expiryDate;     // Tanggal kadaluarsa batch
    public int quantity;             // Jumlah barang dalam batch
    public String status;            // Status batch (Diterima, Akan Kadaluarsa, Kadaluarsa, Terjual)
    public boolean isVisited;        // Penanda untuk algoritma pencarian graph (BFS/DFS)

    // Konstruktor: Membuat objek batch baru dengan data awal
    public BatchVertex(String batchId, String productName, LocalDate productionDate, LocalDate expiryDate, int quantity) {
        this.batchId = batchId;
        this.productName = productName;
        this.productionDate = productionDate;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.status = "Diterima"; // Status awal saat batch diterima
        this.isVisited = false;   // Awalnya belum dikunjungi (untuk pencarian graph)
    }

    // --- Getter Methods ---
    // Fungsi-fungsi berikut untuk mengambil data dari objek batch
    public String getBatchId() { return batchId; }
    public String getProductName() { return productName; }
    public LocalDate getProductionDate() { return productionDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public boolean isVisited() { return isVisited; }

    // --- Setter Methods ---
    // Mengubah status kunjungan (untuk algoritma pencarian graph)
    public void setVisited(boolean visited) { isVisited = visited; }
    // Mengubah status batch (misal: jadi "Kadaluarsa" atau "Akan Kadaluarsa")
    public void updateStatus(String newStatus) { this.status = newStatus; }

    // --- Utility Methods ---
    // Mengecek apakah batch sudah kadaluarsa (dibandingkan tanggal hari ini)
    public boolean isExpired() {
        return LocalDate.now().isAfter(this.expiryDate);
    }

    // Mengecek apakah batch akan kadaluarsa dalam x hari ke depan
    public boolean isExpiringSoon(int daysThreshold) {
        LocalDate today = LocalDate.now();
        LocalDate thresholdDate = today.plusDays(daysThreshold);
        // True jika kadaluarsa antara hari ini dan thresholdDate
        return !this.expiryDate.isBefore(today) && !this.expiryDate.isAfter(thresholdDate);
    }

    // Menampilkan data batch dalam format yang mudah dibaca
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "ID: " + batchId +
               " | Produk: " + productName +
               " | Produksi: " + productionDate.format(formatter) +
               " | Kadaluarsa: " + expiryDate.format(formatter) +
               " | Qty: " + quantity +
               " | Status: " + status;
    }
}