package TubesPP1;

import TubesPP1.BatchVertex;
import TubesPP1.InventoryGraph;
import TubesPP1.DateUtil;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Membuat scanner untuk membaca input dari keyboard
        Scanner scanner = new Scanner(System.in);

        // Membuat objek InventoryGraph untuk menyimpan semua batch barang
        InventoryGraph inventoryGraph = new InventoryGraph(20); // Maksimal 20 batch

        // List untuk menyimpan semua user yang sudah terdaftar
        List<User> users = new ArrayList<>();
        // Variabel untuk menyimpan user yang sedang login
        User currentUser = null;

        // Membaca user dari file users.txt
        users = loadUsersFromFile("users.txt");

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
                for (User u : users) {
                    if (u.getUsername().equals(username)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    System.out.println("Username sudah terdaftar. Silakan pilih username lain.");
                } else {
                    users.add(new User(username, password));
                    System.out.println("Registrasi berhasil! Silakan login.");
                }
            } else if (menu.equals("2")) {
                // Login user
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                boolean found = false;
                for (User u : users) {
                    if (u.getUsername().equals(username) && u.checkPassword(password)) {
                        currentUser = u;
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
            System.out.println("9. Logout");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilihan Anda: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
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
                    List<BatchVertex> expiringBatches = inventoryGraph.findExpiringBatches(daysThreshold);
                    if (expiringBatches.isEmpty()) {
                        System.out.println("Tidak ada batch yang mendekati kadaluarsa dalam periode tersebut.");
                    } else {
                        System.out.println("\n--- Batch yang Akan Kadaluarsa (Dalam " + daysThreshold + " Hari) ---");
                        for (BatchVertex batch : expiringBatches) {
                            System.out.println(batch.toString());
                        }
                    }
                    break;

                case 5:
                    // Cari batch yang sudah kadaluarsa
                    List<BatchVertex> expiredBatches = inventoryGraph.findExpiredBatches();
                    if (expiredBatches.isEmpty()) {
                        System.out.println("Tidak ada batch yang sudah kadaluarsa.");
                    } else {
                        System.out.println("\n--- Batch yang Sudah Kadaluarsa ---");
                        for (BatchVertex batch : expiredBatches) {
                            System.out.println(batch.toString());
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
                    // Logout, kembali ke menu login/registrasi
                    System.out.println("Anda telah logout.");
                    main(args); // Restart aplikasi dari awal
                    return;

                case 0:
                    // Keluar aplikasi
                    System.out.println("Terima kasih telah menggunakan Sistem Pencatatan Barang Kadaluarsa! Sampai jumpa lagi.");
                    // Simpan user ke file sebelum keluar
                    saveUsersToFile(users, "users.txt");
                    scanner.close();
                    return;

                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    // Membaca user dari file users.txt
    public static List<User> loadUsersFromFile(String filename) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1], true)); // true artinya password sudah hash
                }
            }
        } catch (IOException e) {
            // File belum ada, tidak masalah
        }
        return users;
    }

    // Menyimpan user ke file users.txt
    public static void saveUsersToFile(List<User> users, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (User u : users) {
                bw.write(u.getUsername() + ";" + u.getPasswordHash());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data user ke file.");
        }
    }
}