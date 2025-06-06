package TubesPP1;

// Import library tanggal bawaan Java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Kelas utilitas untuk operasi tanggal (parsing dan formatting)
// Supaya format tanggal di aplikasi selalu konsisten (YYYY-MM-DD)
public class DateUtil {

    // Formatter untuk mengubah string ke LocalDate dan sebaliknya dengan pola "yyyy-MM-dd"
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Mengubah string tanggal (misal "2024-06-07") menjadi objek LocalDate
    // Jika format salah, akan melempar exception
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString, FORMATTER);
    }

    // Mengubah objek LocalDate menjadi string tanggal dengan format "yyyy-MM-dd"
    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}