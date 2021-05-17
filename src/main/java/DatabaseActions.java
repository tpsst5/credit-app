import java.sql.*;

public class DatabaseActions {

    public static void createDatabase(String fileName) {

        String url = "jdbc:sqlite:src/main/resources/sqlite/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void createTable(String fileName) {

        String url = "jdbc:sqlite:src/main/resources/sqlite/" + fileName;

        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "    id INTEGER PRIMARY KEY,\n"
                + "    number TEXT NOT NULL,\n"
                + "    pin TEXT NOT NULL,\n"
                + "    balance INTEGER DEFAULT 0\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public static Connection connect(String fileName) {

        String url = "jdbc:sqlite:src/main/resources/sqlite/" + fileName;

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;

    }

    public static void insert(String fileName, String cardNum, String pin, long balance) {

        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";

        try (Connection conn = DatabaseActions.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardNum);
            pstmt.setString(2, pin);
            pstmt.setLong(3, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
