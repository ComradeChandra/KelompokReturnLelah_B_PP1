package TubesPP1;

// Import kelas-kelas yang dibutuhkan
import TubesPP1.BatchVertex; // Kelas untuk objek batch barang
import java.util.ArrayList;  // Untuk menyimpan daftar batch (vertex)
import java.util.HashMap;    // Untuk mapping batchId ke index
import java.util.LinkedList; // Untuk antrian BFS
import java.util.List;       // Untuk List<BatchVertex>
import java.util.Map;        // Untuk Map batchId ke index
import java.util.Queue;      // Untuk antrian BFS
import java.io.BufferedWriter; // Untuk menulis ke file
import java.io.FileWriter;     // Untuk menulis ke file

// Kelas InventoryGraph menyimpan seluruh batch barang dalam bentuk graph (matriks adjacency)
// Setiap batch adalah simpul (vertex), dan hubungan antar batch adalah edge
public class InventoryGraph {

    // List untuk menyimpan semua batch (vertex)
    private List<BatchVertex> vertices;
    // Matriks adjacency untuk menyimpan relasi antar batch (edge)
    private int[][] adjMatrix;
    // Jumlah batch yang sudah dimasukkan
    private int numVertices;
    // Maksimal batch yang bisa dimasukkan
    private int maxVertices;
    // Map untuk mencari index batch berdasarkan batchId
    private Map<String, Integer> vertexIndices;

    // Konstruktor: inisialisasi graph dengan kapasitas tertentu
    public InventoryGraph(int maxVertices) {
        this.maxVertices = maxVertices;
        vertices = new ArrayList<>(maxVertices); // List untuk menyimpan batch
        adjMatrix = new int[maxVertices][maxVertices]; // Matriks adjacency
        numVertices = 0;
        vertexIndices = new HashMap<>(); // Map batchId ke index
    }

    // Menambah batch baru ke graph
    // Setiap batch baru akan menjadi vertex baru di graph
    public void addBatchVertex(BatchVertex vertex) {
        if (numVertices >= maxVertices) {
            System.out.println("Graph sudah penuh, tidak bisa menambah batch baru.");
            return;
        }
        if (!vertexIndices.containsKey(vertex.batchId)) {
            vertices.add(vertex);
            vertexIndices.put(vertex.batchId, numVertices);
            numVertices++;
            System.out.println("Batch '" + vertex.batchId + "' (" + vertex.productName + ") berhasil ditambahkan.");
        } else {
            System.out.println("Batch dengan ID '" + vertex.batchId + "' sudah ada.");
        }
    }

    // Menambah hubungan (edge) antar batch
    // Edge berarti ada proses/relasi dari batch asal ke batch tujuan
    public void addBatchEdge(String batchId1, String batchId2) {
        Integer index1 = vertexIndices.get(batchId1);
        Integer index2 = vertexIndices.get(batchId2);

        if (index1 != null && index2 != null) {
            if (adjMatrix[index1][index2] == 1) {
                System.out.println("Edge antara '" + batchId1 + "' dan '" + batchId2 + "' sudah ada.");
                return;
            }
            adjMatrix[index1][index2] = 1;
            System.out.println("Edge ditambahkan dari Batch '" + batchId1 + "' ke Batch '" + batchId2 + "'.");
        } else {
            System.out.println("Salah satu atau kedua Batch ID tidak ditemukan. Pastikan Batch sudah ditambahkan.");
        }
    }

    // Menampilkan matriks adjacency (relasi antar batch)
    // Matriks ini menunjukkan batch mana saja yang saling terhubung
    public void displayAdjacencyMatrix() {
        if (numVertices == 0) {
            System.out.println("\n--- Adjacency Matrix (Kosong) ---");
            System.out.println("Belum ada batch yang ditambahkan ke graph.");
            return;
        }

        // Header kolom (ID batch)
        System.out.println("\n--- Adjacency Matrix ---");
        System.out.print("        ");
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%-7s", vertices.get(i).batchId);
        }
        // Garis bawah
        System.out.print("\n        ");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("-------");
        }
        System.out.println();

        // Isi matriks (0 = tidak terhubung, 1 = ada edge)
        for (int i = 0; i < numVertices; i++) {
            System.out.printf("%-7s |", vertices.get(i).batchId);
            for (int j = 0; j < numVertices; j++) {
                System.out.printf("%-6d ", adjMatrix[i][j]);
            }
            System.out.println();
        }
    }

    // Mengambil objek batch berdasarkan batchId
    // Digunakan untuk edit/hapus/cari batch tertentu
    public BatchVertex getBatchVertex(String batchId) {
        Integer index = vertexIndices.get(batchId);
        if (index != null) {
            return vertices.get(index);
        }
        return null;
    }

    // Mencari batch yang akan kadaluarsa dalam x hari ke depan
    // Mengembalikan list batch yang expiring
    public List<BatchVertex> findExpiringBatches(int daysThreshold) {
        List<BatchVertex> expiring = new ArrayList<>();
        System.out.println("\nMencari batch yang akan kadaluarsa dalam " + daysThreshold + " hari...");
        for (BatchVertex batch : vertices) {
            if (batch.isExpiringSoon(daysThreshold)) {
                batch.updateStatus("Akan Kadaluarsa");
                expiring.add(batch);
            }
        }
        return expiring;
    }

    // Mencari batch yang sudah kadaluarsa
    // Mengembalikan list batch yang expired
    public List<BatchVertex> findExpiredBatches() {
        List<BatchVertex> expired = new ArrayList<>();
        System.out.println("\nMencari batch yang sudah kadaluarsa...");
        for (BatchVertex batch : vertices) {
            if (batch.isExpired()) {
                batch.updateStatus("Kadaluarsa");
                expired.add(batch);
            }
        }
        return expired;
    }

    // Menampilkan semua batch yang ada di graph
    public void displayAllBatches() {
        System.out.println("\n--- Daftar Semua Batch ---");
        if (vertices.isEmpty()) {
            System.out.println("Belum ada batch yang ditambahkan.");
            return;
        }
        for (BatchVertex batch : vertices) {
            System.out.println(batch.toString());
        }
    }

    // Breadth-First Search (BFS): menelusuri batch dari satu batch ke batch lain secara berurutan
    // Cocok untuk menampilkan alur batch secara melebar
    public List<BatchVertex> bfs(String startBatchId) {
        // Reset status kunjungan semua batch
        for (BatchVertex v : vertices) {
            v.setVisited(false);
        }

        List<BatchVertex> traversalResult = new ArrayList<>();
        Queue<BatchVertex> queue = new LinkedList<>();

        BatchVertex startVertex = getBatchVertex(startBatchId);
        if (startVertex == null) {
            System.out.println("Batch '" + startBatchId + "' tidak ditemukan untuk memulai traversal.");
            return traversalResult;
        }

        queue.add(startVertex);
        startVertex.setVisited(true);
        System.out.println("\n--- Melacak Alur/Dependensi Batch dari: " + startBatchId + " (BFS) ---");

        while (!queue.isEmpty()) {
            BatchVertex currentVertex = queue.poll();
            traversalResult.add(currentVertex);
            System.out.println("   -> " + currentVertex.toString());

            int currentIndex = vertexIndices.get(currentVertex.batchId);
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[currentIndex][i] == 1 && !vertices.get(i).isVisited()) {
                    vertices.get(i).setVisited(true);
                    queue.add(vertices.get(i));
                }
            }
        }
        return traversalResult;
    }

    // Depth-First Search (DFS): menelusuri batch secara rekursif (menyelam ke dalam)
    // Cocok untuk menampilkan alur batch secara mendalam
    public List<BatchVertex> dfs(String startBatchId) {
        // Reset status kunjungan semua batch
        for (BatchVertex v : vertices) {
            v.setVisited(false);
        }

        List<BatchVertex> traversalResult = new ArrayList<>();
        BatchVertex startVertex = getBatchVertex(startBatchId);
        if (startVertex == null) {
            System.out.println("Batch '" + startBatchId + "' tidak ditemukan untuk memulai traversal.");
            return traversalResult;
        }
        System.out.println("\n--- Melacak Alur/Dependensi Batch dari: " + startBatchId + " (DFS) ---");
        dfsRecursive(startVertex, traversalResult);
        return traversalResult;
    }

    // Fungsi rekursif untuk DFS
    private void dfsRecursive(BatchVertex currentVertex, List<BatchVertex> traversalResult) {
        currentVertex.setVisited(true);
        traversalResult.add(currentVertex);
        System.out.println("   -> " + currentVertex.toString());

        int currentIndex = vertexIndices.get(currentVertex.batchId);
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[currentIndex][i] == 1 && !vertices.get(i).isVisited()) {
                dfsRecursive(vertices.get(i), traversalResult);
            }
        }
    }

    // Mengembalikan semua batch sebagai List
    // Digunakan untuk menyimpan ke file
    public List<BatchVertex> getAllBatches() {
        List<BatchVertex> result = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            result.add(vertices.get(i));
        }
        return result;
    }

    // Menghapus batch berdasarkan ID
    // Menghapus vertex dan menggeser data di list dan matriks agar tetap rapat
    public boolean removeBatchVertex(String batchId) {
        int idx = -1;
        for (int i = 0; i < numVertices; i++) {
            if (vertices.get(i).getBatchId().equals(batchId)) {
                idx = i;
                break;
            }
        }
        if (idx == -1) return false;

        // Geser array vertices dan adjMatrix agar tidak ada celah setelah dihapus
        for (int i = idx; i < numVertices - 1; i++) {
            vertices.set(i, vertices.get(i + 1));
            for (int j = 0; j < numVertices; j++) {
                adjMatrix[i][j] = adjMatrix[i + 1][j];
                adjMatrix[j][i] = adjMatrix[j][i + 1];
            }
        }
        numVertices--;
        return true;
    }

    // Fungsi untuk menyimpan semua batch ke file batches.txt
    // Setiap batch akan disimpan dalam satu baris dengan format: id;nama;produksi;expired;qty;status
    public void saveAllBatchesToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (BatchVertex b : vertices) {
                bw.write(b.getBatchId() + ";" +
                         b.getProductName() + ";" +
                         DateUtil.formatDate(b.getProductionDate()) + ";" +
                         DateUtil.formatDate(b.getExpiryDate()) + ";" +
                         b.getQuantity() + ";" +
                         b.getStatus());
                bw.newLine();
            }
        } catch (Exception e) {
            // Exception bisa terjadi jika file tidak bisa ditulis (misal: tidak ada izin, disk penuh, dsb)
            System.out.println("Gagal menyimpan data batch ke file.");
        }
    }
}