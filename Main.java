package TubesPP1;

import TubesPP1.BatchVertex;
import TubesPP1.InventoryGraph;
import TubesPP1.DateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Kelas Main adalah titik masuk aplikasi (tempat program dijalankan)
// Di sini user bisa mengelola batch barang, menambah relasi, dan melakukan pencarian
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryGraph inventoryGraph = new InventoryGraph(20); // Maksimal 20 batch

        // Tampilan awal aplikasi
        System.out.println("✨ Selamat datang di Sistem Pencatatan Barang Kadaluarsa (Graph Based)! ✨");
        System.out.println("Oleh: Aurellia Tri Putri - " + LocalDate.now().getYear());

        // Perulangan menu utama
        while (true) {
            System.out.println("\n--- Menu Utama ---");
            System.out.println("1. Tambah Batch Barang Baru");
            System.out.println("2. Tambah Hubungan Antar Batch (Edge)");
            System.out.println("3. Tampilkan Adjacency Matrix");
            System.out.println("4. Cari Batch Mendekati Kadaluarsa");
            System.out.println("5. Cari Batch yang Sudah Kadaluarsa");
            System.out.println("6. Tampilkan Semua Batch");
            System.out.println("7. Lacak Alur/Dependensi Batch (BFS)");
            System.out.println("8. Lacak Alur/Dependensi Batch (DFS)");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("➡️ Pilihan Anda: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("❌ Input tidak valid. Mohon masukkan angka.");
                scanner.nextLine(); // Buang input yang salah
                continue;
            }
            scanner.nextLine(); // Buang newline setelah angka

            switch (choice) {
                case 1: // Tambah Batch Barang
                    // Input data batch baru dari user
                    System.out.println("\n--- Tambah Batch Barang ---");
                    System.out.print("   ID Batch (misal: B001): ");
                    String batchId = scanner.nextLine();
                    System.out.print("   Nama Produk: ");
                    String productName = scanner.nextLine();

                    // Input tanggal produksi
                    LocalDate productionDate = null;
                    while (productionDate == null) {
                        System.out.print("   Tanggal Produksi (YYYY-MM-DD): ");
                        String prodDateStr = scanner.nextLine();
                        try {
                            productionDate = DateUtil.parseDate(prodDateStr);
                        } catch (DateTimeParseException e) {
                            System.out.println("❌ Format tanggal salah. Gunakan YYYY-MM-DD.");
                        }
                    }

                    // Input tanggal kadaluarsa
                    LocalDate expiryDate = null;
                    while (expiryDate == null) {
                        System.out.print("   Tanggal Kadaluarsa (YYYY-MM-DD): ");
                        String expiryDateStr = scanner.nextLine();
                        try {
                            expiryDate = DateUtil.parseDate(expiryDateStr);
                            if (expiryDate.isBefore(productionDate)) {
                                System.out.println("❌ Tanggal kadaluarsa tidak boleh sebelum tanggal produksi.");
                                expiryDate = null;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("❌ Format tanggal salah. Gunakan YYYY-MM-DD.");
                        }
                    }

                    // Input jumlah barang
                    int quantity = 0;
                    boolean validQuantity = false;
                    while (!validQuantity) {
                        System.out.print("   Kuantitas: ");
                        try {
                            quantity = scanner.nextInt();
                            if (quantity <= 0) {
                                System.out.println("❌ Kuantitas harus lebih dari 0.");
                            } else {
                                validQuantity = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("❌ Kuantitas harus berupa angka.");
                            scanner.nextLine(); // Buang input salah
                        }
                    }
                    scanner.nextLine(); // Buang newline

                    // Membuat objek batch baru dan menambahkannya ke graph
                    BatchVertex newBatch = new BatchVertex(batchId, productName, productionDate, expiryDate, quantity);
                    inventoryGraph.addBatchVertex(newBatch);
                    break;

                case 2: // Tambah Hubungan Antar Batch
                    // Input ID batch asal dan tujuan, lalu buat edge di graph
                    System.out.println("\n--- Tambah Hubungan Antar Batch ---");
                    System.out.print("   ID Batch Asal (misal: B001): ");
                    String sourceBatchId = scanner.nextLine();
                    System.out.print("   ID Batch Tujuan (misal: B002): ");
                    String destBatchId = scanner.nextLine();
                    inventoryGraph.addBatchEdge(sourceBatchId, destBatchId);
                    break;

                case 3: // Tampilkan Adjacency Matrix
                    // Menampilkan tabel relasi antar batch
                    inventoryGraph.displayAdjacencyMatrix();
                    break;

                case 4: // Cari Batch Mendekati Kadaluarsa
                    // User memasukkan berapa hari ke depan, lalu tampilkan batch yang akan kadaluarsa
                    int daysThreshold = 0;
                    boolean validDays = false;
                    while (!validDays) {
                        System.out.print("   Cari batch yang kadaluarsa dalam berapa hari ke depan? ");
                        try {
                            daysThreshold = scanner.nextInt();
                            if (daysThreshold < 0) {
                                System.out.println("❌ Jumlah hari tidak boleh negatif.");
                            } else {
                                validDays = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("❌ Input tidak valid. Mohon masukkan angka.");
                            scanner.nextLine(); // Buang input salah
                        }
                    }
                    scanner.nextLine(); // Buang newline

                    List<BatchVertex> expiringBatches = inventoryGraph.findExpiringBatches(daysThreshold);
                    if (expiringBatches.isEmpty()) {
                        System.out.println("🎉 Tidak ada batch yang mendekati kadaluarsa dalam periode tersebut.");
                    } else {
                        System.out.println("\n--- Batch yang Akan Kadaluarsa (Dalam " + daysThreshold + " Hari) ---");
                        for (BatchVertex batch : expiringBatches) {
                            System.out.println(batch.toString());
                        }
                    }
                    break;

                case 5: // Cari Batch yang Sudah Kadaluarsa
                    // Menampilkan batch yang sudah kadaluarsa
                    List<BatchVertex> expiredBatches = inventoryGraph.findExpiredBatches();
                    if (expiredBatches.isEmpty()) {
                        System.out.println("🎉 Tidak ada batch yang sudah kadaluarsa.");
                    } else {
                        System.out.println("\n--- Batch yang Sudah Kadaluarsa ---");
                        for (BatchVertex batch : expiredBatches) {
                            System.out.println(batch.toString());
                        }
                    }
                    break;

                case 6: // Tampilkan Semua Batch
                    // Menampilkan semua batch yang ada di sistem
                    inventoryGraph.displayAllBatches();
                    break;

                case 7: // Lacak Alur/Dependensi Batch (BFS)
                    // User memasukkan ID batch awal, lalu sistem menelusuri batch lain yang terhubung (BFS)
                    System.out.print("   Masukkan ID Batch Awal untuk pelacakan BFS: ");
                    String startBatchBfs = scanner.nextLine();
                    inventoryGraph.bfs(startBatchBfs);
                    break;

                case 8: // Lacak Alur/Dependensi Batch (DFS)
                    // User memasukkan ID batch awal, lalu sistem menelusuri batch lain yang terhubung (DFS)
                    System.out.print("   Masukkan ID Batch Awal untuk pelacakan DFS: ");
                    String startBatchDfs = scanner.nextLine();
                    inventoryGraph.dfs(startBatchDfs);
                    break;

                case 0: // Keluar Aplikasi
                    // Menutup aplikasi
                    System.out.println("👋 Terima kasih telah menggunakan Sistem Pencatatan Barang Kadaluarsa! Sampai jumpa lagi! (づ￣ ³￣)づ");
                    scanner.close();
                    return;

                default:
                    System.out.println("🤔 Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }
}