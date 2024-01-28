import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName1 = "Student.csv", fileName2 = "Course.csv";

        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */
        /*TODO: MAKE A GUI FOR WELCOME WINDOW */

        FileOutputStream fos1, fos2;
        PrintWriter students, course;
        CsvFileManager cfm1, cfm2;
        if (!fileExists(fileName1)) {
            createNewFile(fileName1);
            fos1 = new FileOutputStream(fileName1);
            students = new PrintWriter(fos1);
            cfm1 = new CsvFileManager(students, fileName1,"Name, Id#, Year Lvl, Gender, Course");
        } else {
            fos1 = new FileOutputStream(fileName1, true);
            students = new PrintWriter(fos1);
            cfm1 = new CsvFileManager(students, fileName1);
        }

        /* PlayGround */
        /* PlayGround */
        /* PlayGround */

//        cfm1.create("John Doe", "2022-0456", "3", "male", "BS Computer Engineering");
//        cfm1.create("Alice Johnson", "2022-0789", "2", "female", "BS Electrical Engineering");
//        cfm1.create("Bob Smith", "2021-0123", "4", "male", "BS Mechanical Engineering");
//        cfm1.create("Emily Davis", "2023-0567", "1", "female", "BS Information Technology");
//        cfm1.create("Michael Clark", "2022-0890", "3", "male", "BS Civil Engineering");

        cfm1.list();
//        cfm1.update(3, 0, "Bob Mayers");
        
//        cfm1.read(3);
//        cfm1.update(3,0, "Sponge Bob");
//        System.out.println();
//        cfm1.list();
//        cfm1.clearCsvFile();
//        cfm1.list();

        /* PlayGround */
        /* PlayGround */
        /* PlayGround */

        students.close();
        System.out.println("\nFile /Data.csv/ has been written...");
    }

    private static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && !file.isDirectory() && file.length() > 0;
    }
    private static void createNewFile(String fileName) {
        // Create a new file and write the header
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(fileName))) {
            String header = "Name, Id#, Year Lvl, Gender, Course";
            writer.println(header);
            writer.flush();
        } catch (FileNotFoundException e) {
            // Handle the exception based on your requirements
            System.out.println("An error occurred:" + e);
        }
    }
}