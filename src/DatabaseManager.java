import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseManager {
    public static List<String> studentLines;
    public static List<String> courseLines;

    // Establish database connection
    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/enrolledstudents";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "There is an error connecting to the database. Please make sure your SQL Database is running...","SQL Database Connection Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    // Create operation
    public static void createStudentRecord(String name, String id, String yearLevel, String gender, String course) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO student (StudentName, ID, YearLevel, Gender, CourseID) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, id);
            stmt.setString(3, yearLevel);
            stmt.setString(4, gender);
            stmt.setString(5, course);
            stmt.executeUpdate();
        } catch (MysqlDataTruncation e) {
            JOptionPane.showMessageDialog(null, "Input is more or less than the required amount: " + e.getMessage());
        }
    }
    public static void createCourseRecord(String name, String id) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO course (CourseName, ID) VALUES (?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, id);
            stmt.executeUpdate();
        }
    }


    // Read operation
    public static void readStudentRecords() throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM student");
             ResultSet rs = stmt.executeQuery()) {
            studentLines = new ArrayList<>();
            while (rs.next()) {
                studentLines.add(rs.getString(1) + "," +
                        rs.getString(2) + "," +
                        rs.getString(3) + "," +
                        rs.getString(4) + "," +
                        rs.getString(5)
                );
            }
        }
    }
    public static void readCourseRecords() throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM course");
             ResultSet rs = stmt.executeQuery()) {
            courseLines = new ArrayList<>();
            while (rs.next()) {
                courseLines.add(rs.getString(1) + "," + rs.getString(2));
            }
        }
    }
    public static Set<String> readCourseIDs() throws SQLException {
        Set<String> courseIDs = new HashSet<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM course");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                courseIDs.add(rs.getString(2));
            }
        }
        return courseIDs;
    }


    // Update operation
    public static void updateStudentRecord(String id, String newName, String newId, String newYearLevel, String newGender, String newCourse) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE student SET StudentName = ?, ID = ?, YearLevel = ?, Gender = ?, CourseID = ? WHERE ID = ?")) {
            stmt.setString(1, newName);
            stmt.setString(2, newId);
            stmt.setString(3, newYearLevel);
            stmt.setString(4, newGender);
            stmt.setString(5, newCourse);
            stmt.setString(6, id);
            stmt.executeUpdate();
        }
    }
    public static void updateCourseRecord(String id, String newName, String newId) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE course SET CourseName = ?, ID = ? WHERE ID = ?")) {
            stmt.setString(1, newName);
            stmt.setString(2, newId);
            stmt.setString(3, id);
            stmt.executeUpdate();
        }
    }


    // Delete operation
    public static void deleteStudentRecord(String id) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM student WHERE ID = ?")) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
    public static void deleteCourseRecord(String id) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM course WHERE ID = ?")) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public static List<String> getLinesList() {
        return studentLines;
    }
    public static List<String> getCourseList() {
        return courseLines;
    }
}