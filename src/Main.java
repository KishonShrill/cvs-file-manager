import java.io.*;
import java.sql.SQLException;

public class Main {
    private static final String[] studentArr = {"Name", "Id", "Year Lvl", "Gender", "Course"};
    private static final String[] courseArr = {"Name", "Id"};
    private static final boolean activateGUI = true;
    private static boolean switchFile = false;
    private static final boolean usingSQL = true;
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        if (!usingSQL) {
            InitializeProgram initializer = new InitializeProgram();
            initializer.initialize(activateGUI);
        } else {
            new FileManagerGUI(studentArr, switchListener, DatabaseManager.readCourseIDs(), false, usingSQL, false);
        }
    }

    final static FileManagerSwitchedListener switchListener = new FileManagerSwitchedListener() {
        @Override
        public void onFileManagerSwitched() throws SQLException {
            switchFile = !switchFile;
            System.out.println("FileManagerGUI is switched!");
            if (!switchFile) {
                new FileManagerGUI(studentArr, switchListener, DatabaseManager.readCourseIDs(), false, usingSQL, false);
            }
            if (switchFile) {
                new FileManagerGUI(courseArr, switchListener, DatabaseManager.readCourseIDs(), false, usingSQL, true);
            }
        }
    };
}
