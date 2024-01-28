import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvFileManager {
    final PrintWriter csvFilePath;
    final String fileName;
    public List<String> lines = new ArrayList<>();
    public String headerTemplate;

    /* Constructors */
    public CsvFileManager(PrintWriter filePath, String fileName) {
        /* Initialize GUI *//* Initialize GUI *//* Initialize GUI */
//        FileManagerGUI FMG = new FileManagerGUI();
        /* Initialize GUI *//* Initialize GUI *//* Initialize GUI */
        this.csvFilePath = filePath;
        this.fileName = fileName;
        this.headerTemplate = getHeader();
        populateLinesList();
        /* Initialize GUI *//* Initialize GUI *//* Initialize GUI */
//        FMG.setLinesListGUI(this.lines);
        /* Initialize GUI *//* Initialize GUI *//* Initialize GUI */
    }

    public CsvFileManager(PrintWriter filePath, String fileName, String headerTemplate) {
        /* Initialize GUI *//* Initialize GUI *//* Initialize GUI */
//        FileManagerGUI FMG = new FileManagerGUI();
        /* Initialize GUI *//* Initialize GUI *//* Initialize GUI */
        this.csvFilePath = filePath;
        this.fileName = fileName;
        this.headerTemplate = headerTemplate;
        lines.add(headerTemplate);
        csvFilePath.println(headerTemplate);
        csvFilePath.flush();
        /* Initialize GUI *//* Initialize GUI *//* Initialize GUI */
//        FMG.setLinesListGUI(this.lines);
        /* Initialize GUI *//* Initialize GUI *//* Initialize GUI */
    }

    /* Create-Read-Update-Delete-List Methods */
    public void create(String data_name, String data_id, String data_yrLvl, String data_gender, String data_course) {
        String csvLine = String.join(",", data_name, data_id, data_yrLvl, data_gender, data_course);
        csvFilePath.println(csvLine);
        csvFilePath.flush();
        lines.add(csvLine);
    }

    public void read(int lineNumber) {
        List<String> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int currentLineNumber = 0;

            while ((line = reader.readLine()) != null) {
                currentLineNumber++;

                if (currentLineNumber == lineNumber + 1) {
                    // Found the desired line
                    String[] columns = line.split(",");
                    for (String column : columns) {
                        result.add(column.trim());
                    }
                    break; // No need to continue reading
                }
            }
        } catch (IOException e) {
            // Handle the exception based on your requirements
            System.out.println("An error occurred:" + e);
        }

        /*TODO: Change this later for readability */
        if (result != null && !result.isEmpty()) {
            for (String value : result) {
                System.out.print(value + " ");
            }
            System.out.println("\n");
        } else {
            System.out.println("Line not found or empty.");
        }
    }

    public void update(int lineNumber, int column, String newData) {
        // Code to update a specific line in the CSV file
        List<String> currentData = readLine(lineNumber);

        if (currentData != null && column >= 0 && column < currentData.size()) {
            currentData.set(column, newData);
            updateLine(lineNumber, currentData);
            updateFile();
        }
    }

    public void delete(int lineNumber) {
        // Code to delete a specific line from the CSV file
        //TODO: MUST ALSO DELETE DATA IN CSV FILE
        lines.remove(lineNumber);
        updateFile();
    }

    public void list() {
        // Code to retrieve and return a list of all entries in the CSV file
        // Read existing lines from the file and populate the lines list
        lines.clear();
        populateLinesList();

        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println("");

        //TODO: Make a GUI framework for visual representation
        //return new ArrayList<>(); // Placeholder, you need to implement this
    }

    /**
     * Private Additional Methods
     * Private Additional Methods
     * Private Additional Methods
     **/

    private String getHeader() {
        if (lines.size() > 0) {
            // The first element in the 'lines' list is the header
            return lines.get(0);
        } else {
            // Return an empty string or handle the case where there is no header
            return "";
        }
    }
    private void populateLinesList() {
        // Read existing lines from the file and populate the lines list
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines or lines with only whitespace
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            // Handle the exception based on your requirements
            System.out.println("An error occurred:" + e);
        }
    }
    private List<String> readLine(int lineNumber) {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int currentLineNumber = 0;

            while ((line = reader.readLine()) != null) {
                currentLineNumber++;

                if (currentLineNumber == lineNumber + 1) {
                    // Found the desired line
                    String[] columns = line.split(",");
                    for (String column : columns) {
                        result.add(column.trim());
                    }
                    break; // No need to continue reading
                }
            }
        } catch (IOException e) {
            // Handle the exception based on your requirements
            System.out.println("An error occurred:" + e);
        }
        return result;
    }
    private void updateLine(int lineNumber, List<String> newData) {
        // Code to update a specific line in the CSV file
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int currentLineNumber = 0;

            for (int i = 0; i < lines.size(); i++) {
                currentLineNumber++;

                if (currentLineNumber == lineNumber + 1) {
                    // Update the line with new data
                    lines.set(i, String.join(",", newData));
                    break;
                }
            }
        } catch (IOException e) {
            // Handle the exception based on your requirements
            System.out.println("An error occurred:" + e);
        }

        // Write the updated lines back to the file
        updateFile();
    }

    private void updateFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write the lines to the file
            for (String line : lines) {
                writer.println(line);
                csvFilePath.flush();
            }
        } catch (IOException e) {
            // Handle the exception based on your requirements
            System.out.println("An error occurred:" + e);
        }
    }

    public void clearCsvFile() {
        headerTemplate = getHeader();
        lines.clear();
        lines.add(headerTemplate);
        updateFile();
    }

    public List<String> getLinesList() {
        return this.lines;
    }
}