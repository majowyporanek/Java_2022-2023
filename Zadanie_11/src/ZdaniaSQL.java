import java.io.File;
import java.nio.file.Path;
import java.sql.*;

public class ZdaniaSQL implements GeneratorZdan {
    String filename;
    private static final String PREFIX = "jdbc:sqlite:";

    @Override
    public void plikBazyDanych(String filename) {
        this.filename = filename;
    }

    public String zwrocImie(int id, String czynnosc) {
        return (id == 0) ? czynnosc + "a" : czynnosc;
    }

    @Override
    public String zbudujZdanie(int zdanieID) {
        Connection conn = null;
        Statement stat = null;
        String url = "jdbc:sqlite:" + this.filename.;
        try {
            conn = DriverManager.getConnection(url);
            stat = conn.createStatement();
            String id = Integer.toString(zdanieID);
            ResultSet rs = stat.executeQuery("SELECT * FROM Zdanie where ZdanieID = " + id + ";");
        } catch (SQLException e) {
            throw new RuntimeException(e);
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
        }
        return null;
    }
}

//    public static void main(String[] args) {
//        System.out.println("BAZY");
//        Connection conn = null;
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql:C:\\Users\\klaud\\IdeaProjects\\Java-zaj-cia-2022\\JAVA\\Zadanie_11\\src\\baza");
//            Statement statement = conn.createStatement();
//            conn.createStatement();
//        } catch (Exception ignored) {
//            System.out.println("BAD");
//        }
//    }