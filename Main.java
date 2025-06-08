package TubesPP1;

import TubesPP1.BatchVertex;
import TubesPP1.InventoryGraph;
import TubesPP1.DateUtil;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;
import java.util.List; // Pastikan baris ini ada

public class Main {
    public static void main(String[] args) {
        // Membuat scanner untuk membaca input dari keyboard
        Scanner scanner = new Scanner(System.in);

        // Membuat objek InventoryGraph untuk menyimpan semua batch barang (graph)
        InventoryGraph inventoryGraph = new InventoryGraph(20); // Maksimal 20 batch

        // ===========================
        // ARRAY BIASA UNTUK USER
        // ===========================
        // Membuat array untuk menyimpan data user, kapasitas maksimal 100 user
        User[] users = new User[100];
        // Variabel untuk menghitung jumlah user yang sudah terisi di array
        int userCount = 0;
        // Variabel untuk menyimpan user yang sedang login
        User currentUser = null;

        // ===========================
        // FUNGSI UNTUK ARRAY BIASA
        // ===========================
        // Membaca user dari file users.txt (supaya user tetap ada walau aplikasi ditutup)
        userCount = loadUsersFromFileArray("users.txt", users);

        // Menu awal: user harus registrasi atau login dulu sebelum bisa pakai aplikasi
        while (true) {
            System.out.println("\n=== SISTEM SISTEM PENCATATAN BATCH BARANG KADALUARSA ===");
            System.out.println("1. Registrasi");
            System.out.println("2. Login");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            String menu = scanner.nextLine();

            if (menu.equals("1")) {
                // Registrasi user baru
                System.out.print("Masukkan username baru: ");
                String username = scanner.nextLine();
                System.out.print("Masukkan password: ");
                String password = scanner.nextLine();

                // Cek apakah username sudah dipakai user lain
                boolean exists = false;
                for (int i = 0; i < userCount; i++) {
                    // Bandingkan username input dengan username di array
                    if (users[i].getUsername().equals(username)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    System.out.println("Username sudah terdaftar. Silakan pilih username lain.");
                } else {
                    // Tambah user baru ke array jika belum penuh
                    if (userCount < users.length) {
                        users[userCount] = new User(username, password); // Simpan user baru di array
                        userCount++; // Tambah jumlah user
                        // ===========================
                        // FUNGSI UNTUK ARRAY BIASA
                        // ===========================
                        saveUsersToFileArray(users, userCount, "users.txt"); // Simpan semua user ke file
                        System.out.println("Registrasi berhasil! Silakan login.");
                    } else {
                        System.out.println("Kapasitas user sudah penuh!");
                    }
                }
            } else if (menu.equals("2")) {
                // Login user
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                // Cek apakah username dan password cocok
                boolean found = false;
                for (int i = 0; i < userCount; i++) {
                    if (users[i].getUsername().equals(username) && users[i].checkPassword(password)) {
                        currentUser = users[i];
                        found = true;
                        break;
                    }
                }
                if (found) {
                    System.out.println("Login berhasil! Selamat datang, " + currentUser.getUsername() + ".");
                    break; // Lanjut ke menu utama
                } else {
                    System.out.println("Username atau password salah.");
                }
            } else if (menu.equals("0")) {
                // Keluar aplikasi
                System.out.println("Keluar aplikasi.");
                scanner.close();
                return;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }

        // Setelah login, tampilkan menu utama aplikasi
        System.out.println("Selamat datang di Sistem Pencatatan Batch Barang Kadaluarsa (Graph Based)!");

        // Menu utama aplikasi, user bisa mengelola batch barang
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
            System.out.println("9. Edit Batch");
            System.out.println("10. Hapus Batch");
            System.out.println("11. Logout");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilihan Anda: ");

            int choice = -1;
            try {
                choice = scanner.nextInt(); // Baca input menu (angka)
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Mohon masukkan angka.");
                scanner.nextLine(); // Buang input yang salah
                continue;
            }
            scanner.nextLine(); // Buang newline setelah angka

            switch (choice) {
                case 1:
                    // Tambah batch barang baru ke sistem
                    System.out.println("\n--- Tambah Batch Barang ---");
                    System.out.print("   ID Batch (misal: B001): ");
                    String batchId = scanner.nextLine();
                    System.out.print("   Nama Produk: ");
                    String productName = scanner.nextLine();

                    // Input tanggal produksi, dicek agar formatnya benar
                    LocalDate productionDate = null;
                    while (productionDate == null) {
                        System.out.print("   Tanggal Produksi (YYYY-MM-DD): ");
                        String prodDateStr = scanner.nextLine();
                        try {
                            productionDate = DateUtil.parseDate(prodDateStr);
                        } catch (DateTimeParseException e) {
                            System.out.println("Format tanggal salah. Gunakan YYYY-MM-DD.");
                        }
                    }

                    // Input tanggal kadaluarsa, harus setelah tanggal produksi
                    LocalDate expiryDate = null;
                    while (expiryDate == null) {
                        System.out.print("   Tanggal Kadaluarsa (YYYY-MM-DD): ");
                        String expiryDateStr = scanner.nextLine();
                        try {
                            expiryDate = DateUtil.parseDate(expiryDateStr);
                            if (expiryDate.isBefore(productionDate)) {
                                System.out.println("Tanggal kadaluarsa tidak boleh sebelum tanggal produksi.");
                                expiryDate = null;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Format tanggal salah. Gunakan YYYY-MM-DD.");
                        }
                    }

                    // Input jumlah barang, harus angka positif
                    int quantity = 0;
                    boolean validQuantity = false;
                    while (!validQuantity) {
                        System.out.print("   Kuantitas: ");
                        try {
                            quantity = scanner.nextInt();
                            if (quantity <= 0) {
                                System.out.println("Kuantitas harus lebih dari 0.");
                            } else {
                                validQuantity = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Kuantitas harus berupa angka.");
                            scanner.nextLine(); // Buang input salah
                        }
                    }
                    scanner.nextLine(); // Buang newline

                    // Membuat objek batch baru dan menambahkannya ke graph
                    BatchVertex newBatch = new BatchVertex(batchId, productName, productionDate, expiryDate, quantity);
                    inventoryGraph.addBatchVertex(newBatch);
                    break;

                case 2:
                    // Tambah hubungan antar batch (edge)
                    System.out.println("\n--- Tambah Hubungan Antar Batch ---");
                    System.out.print("   ID Batch Asal (misal: B001): ");
                    String sourceBatchId = scanner.nextLine();
                    System.out.print("   ID Batch Tujuan (misal: B002): ");
                    String destBatchId = scanner.nextLine();
                    inventoryGraph.addBatchEdge(sourceBatchId, destBatchId);
                    break;

                case 3:
                    // Tampilkan adjacency matrix (tabel relasi antar batch)
                    inventoryGraph.displayAdjacencyMatrix();
                    break;

                case 4:
                    // Cari batch yang akan kadaluarsa dalam x hari ke depan
                    int daysThreshold = 0;
                    boolean validDays = false;
                    while (!validDays) {
                        System.out.print("   Cari batch yang kadaluarsa dalam berapa hari ke depan? ");
                        try {
                            daysThreshold = scanner.nextInt();
                            if (daysThreshold < 0) {
                                System.out.println("Jumlah hari tidak boleh negatif.");
                            } else {
                                validDays = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Input tidak valid. Mohon masukkan angka.");
                            scanner.nextLine(); // Buang input salah
                        }
                    }
                    scanner.nextLine(); // Buang newline

                    // Tampilkan batch yang akan kadaluarsa
                    List<BatchVertex> expiring = inventoryGraph.findExpiringBatches(daysThreshold);
                    if (expiring.isEmpty()) {
                        System.out.println("Tidak ada batch yang akan kadaluarsa dalam " + daysThreshold + " hari.");
                    } else {
                        System.out.println("Batch yang akan kadaluarsa:");
                        for (BatchVertex b : expiring) {
                            System.out.println(b);
                        }
                    }
                    break;

                case 5:
                    // Cari batch yang sudah kadaluarsa
                    List<BatchVertex> expired = inventoryGraph.findExpiredBatches();
                    if (expired.isEmpty()) {
                        System.out.println("Tidak ada batch yang sudah kadaluarsa.");
                    } else {
                        System.out.println("Batch yang sudah kadaluarsa:");
                        for (BatchVertex b : expired) {
                            System.out.println(b);
                        }
                    }
                    break;

                case 6:
                    // Tampilkan semua batch yang ada di sistem
                    inventoryGraph.displayAllBatches();
                    break;

                case 7:
                    // Lacak alur batch (BFS)
                    System.out.print("   Masukkan ID Batch Awal untuk pelacakan BFS: ");
                    String startBatchBfs = scanner.nextLine();
                    inventoryGraph.bfs(startBatchBfs);
                    break;

                case 8:
                    // Lacak alur batch (DFS)
                    System.out.print("   Masukkan ID Batch Awal untuk pelacakan DFS: ");
                    String startBatchDfs = scanner.nextLine();
                    inventoryGraph.dfs(startBatchDfs);
                    break;

                case 9:
                    // Edit batch
                    System.out.print("Masukkan ID Batch yang ingin diedit: ");
                    String editId = scanner.nextLine();
                    BatchVertex batchToEdit = inventoryGraph.getBatchVertex(editId);
                    if (batchToEdit == null) {
                        System.out.println("Batch tidak ditemukan.");
                    } else {
                        System.out.print("Nama Produk baru (kosongkan jika tidak diubah): ");
                        String newName = scanner.nextLine();
                        if (!newName.isEmpty()) batchToEdit.productName = newName;

                        System.out.print("Tanggal Produksi baru (YYYY-MM-DD, kosongkan jika tidak diubah): ");
                        String newProd = scanner.nextLine();
                        if (!newProd.isEmpty()) batchToEdit.productionDate = DateUtil.parseDate(newProd);

                        System.out.print("Tanggal Kadaluarsa baru (YYYY-MM-DD, kosongkan jika tidak diubah): ");
                        String newExp = scanner.nextLine();
                        if (!newExp.isEmpty()) batchToEdit.expiryDate = DateUtil.parseDate(newExp);

                        System.out.print("Kuantitas baru (kosongkan jika tidak diubah): ");
                        String newQty = scanner.nextLine();
                        if (!newQty.isEmpty()) batchToEdit.quantity = Integer.parseInt(newQty);

                        System.out.println("Batch berhasil diedit.");
                    }
                    break;

                case 10:
                    // Hapus batch
                    System.out.print("Masukkan ID Batch yang ingin dihapus: ");
                    String delId = scanner.nextLine();
                    boolean removed = inventoryGraph.removeBatchVertex(delId);
                    if (removed) {
                        System.out.println("Batch berhasil dihapus.");
                    } else {
                        System.out.println("Batch tidak ditemukan.");
                    }
                    break;

                case 11:
                    // Logout, kembali ke menu login/registrasi
                    System.out.println("Anda telah logout.");
                    main(args); // Restart aplikasi dari awal
                    return;

                case 0:
                    // Keluar aplikasi
                    System.out.println("Terima kasih telah menggunakan Sistem Pencatatan Barang Kadaluarsa! Sampai jumpa lagi.");
                    // ===========================
                    // FUNGSI UNTUK ARRAY BIASA
                    // ===========================
                    saveUsersToFileArray(users, userCount, "users.txt");
                    // Untuk batch, pastikan InventoryGraph juga sudah tidak pakai List di penyimpanan file
                    inventoryGraph.saveAllBatchesToFile("batches.txt");
                    scanner.close();
                    return;

                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    // ===========================
    // FUNGSI UNTUK ARRAY BIASA
    // ===========================
    // Fungsi untuk membaca user dari file ke array
    // Mengembalikan jumlah user yang berhasil dibaca
    public static int loadUsersFromFileArray(String filename, User[] users) {
        int count = 0; // Hitung jumlah user yang berhasil dibaca
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line; // Deklarasi variabel line
            while ((line = br.readLine()) != null && count < users.length) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    users[count] = new User(parts[0], parts[1], true); // true: password sudah hash
                    count++;
                }
            }
        } catch (IOException e) {
            // Jika file belum ada, tidak masalah
        }
        return count;
    }

    // ===========================
    // FUNGSI UNTUK ARRAY BIASA
    // ===========================
    // Fungsi untuk menyimpan semua user dari array ke file
    public static void saveUsersToFileArray(User[] users, int userCount, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < userCount; i++) {
                bw.write(users[i].getUsername() + ";" + users[i].getPasswordHash());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data user ke file.");
        }
    }
}