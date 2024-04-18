import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class FileManagerGUI extends JFrame {
    private static PrintWriter fileObject;
    private static String fileName;
    public static List<String> lines = new ArrayList<>();
    public Set<String> courseIds;
    public static CsvFileManager CFM;
    private static String[] header;
    private final FileManagerSwitchedListener switchListener;
    final JLabel fileManager__title = new JLabel();
    private JPanel panelSideRight;
    private JTable dataTable;
    final JButton addItemButton = createButton("Add Item", Color.BLUE);
    final JButton editButton = createButton("Edit", Color.LIGHT_GRAY);
    final JButton finishButton = createButton("Finish", Color.GREEN);
    final JButton deleteButton = createButton("Delete", Color.RED);
    final JButton switchButton = createButton("Switch", Color.BLACK);
    final JTextField textName = new JTextField();
    final JTextField textId = new JTextField();
    final JTextField textYearLvl = new JTextField();
    final JTextField textGender = new JTextField();
    private JComboBox<String> comboBox;
    private static final int MIN_PANEL_WIDTH = 400;
    private static final int MIN_PANEL_HEIGHT = 50;
    private DefaultTableModel tableModel;
    private JComboBox<String> searchColumnComboBox;
    private JComboBox<String> sortColumnComboBox;
    private JTextField searchField;
    private static boolean usingSQL;
    private static boolean switchSQL;
    private static final String[] TUTORIAL_IMAGE_PATH = {
            "./src/components/application.png",
            "./src/components/editorPane.png",
            "./src/components/tablePane.png",
            "./src/components/editBtn.png",
            "./src/components/deleteBtn.png",
            "./src/components/switchBtn.png",
            "./src/components/finishBtn.png",
            "./src/components/closeBtn.png",
            "./src/components/github.png"
    };
    private static final String[] TUTORIAL_MESSAGES = {
            "Hello! Is this your first time using the program? Let me show you around.",
            "Here is where you input the necessary information to add data to the CSV",
            "Here is where the added data is displayed for easy management",
            "Click any row of the Display Table and click Edit to update mistakes and necessary information about the data",
            "Click any row of the Display Table and click Delete to remove data from the Table",
            "Click the switch button to change from Course.csv and Student.csv respectively",
            "Click the Finish button to save and exit. The program will save your data automatically and save when you are done.",
            "Warning!!! Exiting using the Close button on the upper right will NOT save your file.",
            "If you have any questions and feedback, chat us in the discussion board on Github.",
    };

    public FileManagerGUI(
            PrintWriter fileObject,
            String fileName,
            String[] header,
            String initialHead,
            FileManagerSwitchedListener switchListener,
            Set<String> courseIds,
            boolean firstTime
    ) throws SQLException {
        FileManagerGUI.fileObject = fileObject;
        FileManagerGUI.fileName = fileName;
        FileManagerGUI.header = header;
        this.switchListener = switchListener;
        this.courseIds = courseIds;

        if (!fileExists()) {CFM = new CsvFileManager(fileObject, fileName, initialHead, courseIds);}
        else {CFM = new CsvFileManager(fileObject, fileName, courseIds);}

        initializeUI();
        initializeTableModel();
        initializeListeners();

        // Set up JFrame properties
        setTitle("CSV File Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        if (firstTime) displayTutorial();
        tableModel.fireTableDataChanged();
    }
    public FileManagerGUI(
            String[] header,
            FileManagerSwitchedListener switchListener,
            Set<String> courseIds,
            boolean firstTime,
            boolean usingSQL,
            boolean switchSQL
    ) throws SQLException {
        FileManagerGUI.header = header;
        this.switchListener = switchListener;
        this.courseIds = courseIds;
        FileManagerGUI.usingSQL = usingSQL;
        FileManagerGUI.switchSQL = switchSQL;

        if (!switchSQL) fileName = "Student.csv"; else fileName = "Course.csv";

        initializeUI();
        initializeTableModel();
        initializeListeners();

        // Set up JFrame properties
        setTitle("CSV File Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        if (firstTime) displayTutorial();
        tableModel.fireTableDataChanged();
    }

    private void initializeUI() {
        JPanel panelMain = new JPanel(new BorderLayout());
        int margin = 20;
        panelMain.setBorder(BorderFactory.createEmptyBorder(margin, margin-10, margin, margin-10));
        panelMain.setMinimumSize(new Dimension(840, 500));
        setContentPane(panelMain);

        /* Editor Pane */
        JPanel panelSideLeft = new JPanel(null);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "File Editor");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        panelSideLeft.setBorder(titledBorder);
        panelSideLeft.setPreferredSize(new Dimension(220, 500));

        fileManager__title.setText("File Manager");
        panelSideLeft.add(fileManager__title);
        panelSideLeft.add(Box.createVerticalStrut(10));

        int X_AXIS = 15, TEXT_HEIGHT = 20, TEXT_WIDTH = 190;
        addTextField(X_AXIS,  30,TEXT_WIDTH, TEXT_HEIGHT, panelSideLeft, "Name", textName);
        addTextField(X_AXIS,  60,TEXT_WIDTH, TEXT_HEIGHT, panelSideLeft, "Id#", textId);
        if (Objects.equals(fileName, "Student.csv")) {
            addTextField(X_AXIS,  90,TEXT_WIDTH, TEXT_HEIGHT, panelSideLeft, "Year Lvl", textYearLvl);
            addTextField(X_AXIS, 120,TEXT_WIDTH, TEXT_HEIGHT, panelSideLeft, "Gender", textGender);
            comboBox = new JComboBox<>(setToArray(courseIds));
            addComboBox(X_AXIS, TEXT_WIDTH, TEXT_HEIGHT, panelSideLeft, comboBox);
        }
        addItemButton.setBounds(85, 180, 120, 70);

        panelSideLeft.add(addItemButton);
        panelMain.add(panelSideLeft, BorderLayout.WEST);

        /* Content Pane */
        panelSideRight = new JPanel(new BorderLayout());
        panelSideRight.setPreferredSize(new Dimension(500, 500));
        panelSideRight.setLayout(new BorderLayout());
        panelMain.add(panelSideRight, BorderLayout.CENTER);

        panelSideRight.setBorder(new EmptyBorder(0, 10, 0, 0));
        JPanel panelFooter = new JPanel(new BorderLayout());
            JPanel panelFooterLeft = new JPanel();
                panelFooterLeft.add(editButton);
                panelFooterLeft.add(deleteButton);
            JPanel panelFooterRight = new JPanel();
                panelFooterRight.add(switchButton);
                panelFooterRight.add(finishButton);
        panelFooter.add(panelFooterLeft, BorderLayout.WEST);
        panelFooter.add(panelFooterRight, BorderLayout.EAST);
        panelSideRight.add(panelFooter, BorderLayout.SOUTH);


        // Create search components
        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel for combo and text field
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel for combo and text field

        JLabel sortLabel = new JLabel("Sort By:");
        sortColumnComboBox = new JComboBox<>(header);

        JLabel searchLabel = new JLabel("Search By:");
        searchColumnComboBox = new JComboBox<>(header);
        searchField = new JTextField(15);

        // Add components to the respective panels
        sortPanel.add(sortLabel);
        sortPanel.add(sortColumnComboBox);
        searchInputPanel.add(searchLabel);
        searchInputPanel.add(searchColumnComboBox);
        searchInputPanel.add(searchField);

        // Add panels to the search panel
        searchPanel.add(sortPanel, BorderLayout.WEST);
        searchPanel.add(searchInputPanel, BorderLayout.EAST);

        // Add search panel to the main panel
        panelSideRight.add(searchPanel, BorderLayout.NORTH);
    }
    private void initializeTableModel() throws SQLException {
        if (Objects.equals(fileName, "Course.csv")) {
            tableModel = new DefaultTableModel(null, header) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make all cells non-editable
                    return false;
                }
            };
            dataTable = new JTable(tableModel);
            dataTable.setIntercellSpacing(new Dimension(20, 10));
            dataTable.setRowHeight(30);
            dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        else {
            tableModel = new DefaultTableModel(null, header) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return String.class;
                }
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make all cells non-editable
                    return false;
                }
            };
            dataTable = new JTable(tableModel);
            dataTable.setIntercellSpacing(new Dimension(20, 10));
            dataTable.setRowHeight(30);
            dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            DefaultTableCellRenderer courseRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                    Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    String courseId = value.toString().trim();


                    if (column == 4) {
                        if (!"Not Enrolled".equals(courseId)) {
                            if (isCourseAvailable(courseId)) {
                                cellComponent.setBackground(Color.GREEN);
                            } else {
                                cellComponent.setBackground(Color.YELLOW);
                            }
                        } else {
                            cellComponent.setBackground(Color.RED);
                        }
                    }
                    return cellComponent;
                }
            };
            dataTable.getColumnModel().getColumn(4).setCellRenderer(courseRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(dataTable);
        panelSideRight.add(scrollPane, BorderLayout.CENTER);
        if (!usingSQL) {setLinesListGUI(CFM.getLinesList());}
        else {
            if (!switchSQL) {
                DatabaseManager.readStudentRecords();
                setLinesListGUI(DatabaseManager.getLinesList());
            } else {
                DatabaseManager.readCourseRecords();
                setLinesListGUI(DatabaseManager.getCourseList());
            }
        }
    }
    private void updateTableModel() {
        // Clear the existing rows
        tableModel.setRowCount(0);
        int count;
        if (!usingSQL) count = 1; else count = 0;
        // Start the loop from index 1 to exclude the header row
        for (int i = count; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] rowData = line.split(",");
            Object[] updatedRowData = new Object[rowData.length];

            for (int j = 0; j < rowData.length; j++) {

                if (j == 4) {
                    String courseId = rowData[j];
                    if (("Not Enrolled".equals(courseId))) {
                        updatedRowData[j] = "Not Enrolled";
                    } else if (courseIds.contains(courseId)) {
                        updatedRowData[j] = courseId;
                    } else {
                        updatedRowData[j] = "N/A";
                    }
                } else {
                    updatedRowData[j] = rowData[j];
                }
            }
            tableModel.addRow(updatedRowData);
        }
        tableModel.fireTableDataChanged();
    }
    private void search() {
        String searchText = searchField.getText().toLowerCase();
        int columnIndex = searchColumnComboBox.getSelectedIndex();

        // Clear the existing rows
        tableModel.setRowCount(0);
        int count;
        if (!usingSQL) count = 1; else count = 0;
        // Start the loop from index 1 to exclude the header row
        for (int i = count; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] rowData = line.split(",");

            // Get the value of the cell in the selected column
            String cellValue = rowData[columnIndex].toLowerCase();

            // Check if the cell value contains the search text
            if (cellValue.contains(searchText)) {
                // If it matches, add the row to the table model
                Object[] updatedRowData = new Object[rowData.length];
                for (int j = 0; j < rowData.length; j++) {

                    if (j == 4) {
                        String courseId = rowData[j];
                        if (("Not Enrolled".equals(courseId))) {
                            updatedRowData[j] = "Not Enrolled";
                        } else if (courseIds.contains(courseId)) {
                            updatedRowData[j] = courseId;
                        } else {
                            updatedRowData[j] = "N/A";
                        }
                    } else {
                        updatedRowData[j] = rowData[j];
                    }
                }
                tableModel.addRow(updatedRowData);
            }
        }
        tableModel.fireTableDataChanged();
    }
    private void sortTableBySelectedColumn(String[] header) {
        String selectedColumn = (String) sortColumnComboBox.getSelectedItem();
        int columnIndex = -1;

        // Determine the index of the selected column
        for (int i = 0; i < header.length; i++) {
            if (header[i].equalsIgnoreCase(selectedColumn)) {
                columnIndex = i;
                break;
            }
        }

        if (columnIndex != -1) {
            // Sort the table based on the selected column
            switch (selectedColumn) {
                case "Name" -> sortTableByName();
                case "Id" -> sortTableById();
                case "Year Lvl" -> sortTableByYearLevel();
                case "Gender" -> sortTableByGender();
                case "Course" -> sortTableByCourse();
            }
        }
    }
    private void initializeListeners() {
        addItemButton.addActionListener(e -> {
            String yearLvlInput, genderInput;
            String nameInput = textName.getText().trim();
            String idInput = textId.getText().trim();
            if (Objects.equals(fileName, "Student.csv")) {
                yearLvlInput = textYearLvl.getText().trim();
                genderInput = textGender.getText().trim();
                if (yearLvlInput.isEmpty() || genderInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please input all required data!");
                    return;
                }
            }
            if (nameInput.isEmpty() || idInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please input all required data!");
                return;
            }

            // Gather all the ID data from column
            List<String> idColumnData = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String id = parts[1].trim();
                    idColumnData.add(id);
                }
            }

            String idToAdd = textId.getText().trim();
            boolean idExists = idColumnData.contains(idToAdd);

            if (idExists) {
                int option = JOptionPane.showConfirmDialog(null,
                        "This ID already exists in the system. Would you like to overwrite this ID instead?",
                        "ID Exists",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        String yearLvl = "", gender = "";
                        String idInTable = ((String) dataTable.getValueAt(i, 1)).trim(); // Assuming ID is in the second column, adjust index accordingly
                        if (idInTable.equals(idToAdd)) {
                            String IdToUpdate = (String) dataTable.getValueAt(i, 1);
                            String name = (String) dataTable.getValueAt(i, 0);
                            String id = (String) dataTable.getValueAt(i, 1);
                            if (Objects.equals(fileName, "Student.csv")) {
                                yearLvl = (String) dataTable.getValueAt(i, 2);
                                gender = (String) dataTable.getValueAt(i, 3);
                            }
                            showEditDialog(IdToUpdate, name, id, yearLvl, gender);
                            clearTextFields();
                            updateTableModel();
                            tableModel.fireTableDataChanged();
                            return;
                        }
                    }
                } else {clearTextFields();}
            } else {
                try {
                    addData();
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "The header of the data does not match the header of the Database. Please ensure that the column names in the CSV file match the column names in the Database.", "Column Name Mismatch", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
        addItemButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addItemButton.doClick();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        editButton.addActionListener(e -> editData());

        deleteButton.addActionListener(e -> {
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow != -1) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    String IdToDelete = (String) tableModel.getValueAt(selectedRow, 1);
                    tableModel.removeRow(selectedRow);
                    if (!usingSQL) {
                        CFM.deleteDataByID(IdToDelete);
                    } else {
                        lines.removeIf(line -> line.contains(IdToDelete));
                        if (!switchSQL) {
                            try {
                                DatabaseManager.deleteStudentRecord(IdToDelete);
                                setLinesListGUI(DatabaseManager.getLinesList());
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            try {
                                DatabaseManager.deleteCourseRecord(IdToDelete);
                                setLinesListGUI(DatabaseManager.getCourseList());
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        });


        switchButton.addActionListener(e -> {
            if (!usingSQL) setFinishedGUI(lines);
            lines.clear();
            try {
                onWindowSwitched();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispose();
            if (!usingSQL) fileObject.close();
        });

        finishButton.addActionListener(e -> {
            System.out.println("\nFile has been updated and written...");
            if (!usingSQL) setFinishedGUI(lines);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispose();
            if (!usingSQL) fileObject.close();
        });
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                search();
            }
        });
        sortColumnComboBox.addActionListener(e -> sortTableBySelectedColumn(header));
    }
    private void onWindowSwitched() throws SQLException {
        if (switchListener != null) {
            switchListener.onFileManagerSwitched();
        }
    }
    public void setFinishedGUI(List<String> newLines) {
        CFM.setLinesList(newLines);
        updateTableModel();
        CFM.updateFile();
    }
    private void displayTutorial() {
        int picture = 0;
        for (String message : TUTORIAL_MESSAGES) {
            showMessageDialog(message, picture);
            picture++;
        }
    }
    private void showMessageDialog(String message, int picture) {
        // Create a tutorial pane with images and messages
        JOptionPane.showMessageDialog(this,
                createTutorialPanel(message, picture),
                "Welcome to the FileManager!",
                JOptionPane.INFORMATION_MESSAGE);
    }
    private JPanel createTutorialPanel(String message, int picture) {
        JPanel tutorialPanel = new JPanel(new BorderLayout());

        // Add a JLabel with an image
        ImageIcon tutorialImage = new ImageIcon(TUTORIAL_IMAGE_PATH[picture]);
        JLabel imageLabel = new JLabel(tutorialImage);
        tutorialPanel.add(imageLabel, BorderLayout.NORTH);

        // Add JTextArea with tutorial messages
        JTextArea messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        tutorialPanel.add(scrollPane, BorderLayout.CENTER);

        int imageWidth = tutorialImage.getIconWidth();
        int imageHeight = tutorialImage.getIconHeight();
        int panelWidth = Math.max(imageWidth, MIN_PANEL_WIDTH);  // MIN_PANEL_WIDTH is a constant for minimum width
        int panelHeight = Math.addExact(imageHeight, MIN_PANEL_HEIGHT);  // MIN_PANEL_HEIGHT is a constant for minimum height

        tutorialPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        tutorialPanel.revalidate();

        return tutorialPanel;
    }
    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        return button;
    }
    private void addTextField(int x, int y, int width, int height, JPanel panel, String label, JTextField textField) {
        JPanel pack = new JPanel(new BorderLayout());
        JLabel text = new JLabel();
        text.setText(label);
        text.setPreferredSize(new Dimension(70, height));

        pack.setBounds(x, y, width, height);
        pack.add(text, BorderLayout.WEST);
        pack.add(textField,  BorderLayout.CENTER);
        panel.add(pack);
    }
    private void showEditDialog(String IdToUpdate, String name, String id, String yearLvl, String gender) {
        JDialog editDialog = new JDialog(this, "Edit Item", true);
        editDialog.setLayout(new BorderLayout());

        JPanel editPanel = new JPanel(new GridLayout(6, 2));
        JTextField editNameField = new JTextField(name);
        JTextField editIdField = new JTextField(id);
        JTextField editYearLvlField = new JTextField(yearLvl);
        JTextField editGenderField = new JTextField(gender);
        JComboBox<String> editCourseBox = new JComboBox<>(setToArray(courseIds));

        editPanel.add(new JLabel("Name:"));
        editPanel.add(editNameField);
        editPanel.add(new JLabel("Id#:"));
        editPanel.add(editIdField);
        if (Objects.equals(fileName, "Student.csv")) {
            editPanel.add(new JLabel("Year Lvl:"));
            editPanel.add(editYearLvlField);
            editPanel.add(new JLabel("Gender:"));
            editPanel.add(editGenderField);
            editPanel.add(new JLabel("Course:"));
            editPanel.add(editCourseBox);
        }

        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            String chosenCourse;
            if (editCourseBox.getSelectedItem() == "None") chosenCourse = "Not Enrolled"; else chosenCourse = (String) editCourseBox.getSelectedItem();
            String[] updateList;
            String newName = editNameField.getText(), newId = editIdField.getText(), newYearLevel = editYearLvlField.getText(), newGender = editGenderField.getText();
            if (!switchSQL) {
                updateList = new String[]{editNameField.getText(), editIdField.getText(), editYearLvlField.getText(), editGenderField.getText(), chosenCourse};
            } else {
                updateList = new String[]{editNameField.getText(), editIdField.getText()};
            }

            // Gather all the ID data from column
            List<String> idColumnData = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String getID = parts[1].trim();
                    idColumnData.add(getID);
                }
            }

            String idToAdd = editIdField.getText().trim();
            boolean idExists = false;
            if (switchSQL) {
                idExists = idColumnData.stream().anyMatch(existingId -> {
                    if (existingId.startsWith(idToAdd)) {
                        String remainingExistingId = existingId.substring(idToAdd.length()).trim();
                        return remainingExistingId.isEmpty();
                    }
                    return false;
                });
            }

            if (idExists) {
                JOptionPane.showMessageDialog(null,
                        "This ID already exists in the Database.",
                        "ID Exists",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!usingSQL) {CFM.updateDataByName(IdToUpdate, updateList);}
                else {
                    if (!switchSQL) {
                        try {
                            DatabaseManager.updateStudentRecord(IdToUpdate, newName, newId, newYearLevel, newGender, chosenCourse);                            DatabaseManager.readStudentRecords();
                            DatabaseManager.readStudentRecords();
                            setLinesListGUI(DatabaseManager.getLinesList());
                        }
                        catch (SQLException ex) {throw new RuntimeException(ex);}
                    } else {
                        try {
                            DatabaseManager.updateCourseRecord(IdToUpdate, editNameField.getText(), editIdField.getText());
                            DatabaseManager.readCourseRecords();
                            setLinesListGUI(DatabaseManager.getCourseList());
                        }
                        catch (SQLException ex) {throw new RuntimeException(ex);}
                    }
                }
                updateTableModel();
                tableModel.fireTableDataChanged();
                editDialog.dispose();
            }
        });

        editDialog.add(editPanel, BorderLayout.CENTER);
        editDialog.add(updateButton, BorderLayout.SOUTH);
        editDialog.setSize(300, 200);
        editDialog.setLocationRelativeTo(this);
        editDialog.setVisible(true);
    }
    private static boolean fileExists() {
        File file = new File(fileName);
        return file.exists() && !file.isDirectory() && file.length() > 0;
    }
    private boolean isCourseAvailable(String courseId) {
        return !"N/A".equals(courseId);
    }
    public void setLinesListGUI(List<String> lines) {
        FileManagerGUI.lines = lines;
        updateTableModel();
        tableModel.fireTableDataChanged();
    }

    /**
     * These methods already exists before Version 2.09
     * These are made into functions to make a more readable code
     **/
    private void editData () {
        String yearLvl = "", gender = "";
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            String IdToUpdate = (String) tableModel.getValueAt(selectedRow, 1);
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            String id = (String) tableModel.getValueAt(selectedRow, 1);
            if (Objects.equals(fileName, "Student.csv")) {
                yearLvl = (String) tableModel.getValueAt(selectedRow, 2);
                gender = (String) tableModel.getValueAt(selectedRow, 3);
            }

            showEditDialog(IdToUpdate, name, id, yearLvl, gender);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to edit.");
        }
    }
    private void addData() throws SQLException {
        String chosenCourse; boolean fileSwitch = Objects.equals(fileName, "Student.csv");
        if (fileSwitch) {
            if (comboBox.getSelectedItem() == "None") chosenCourse = "Not Enrolled"; else chosenCourse = (String) comboBox.getSelectedItem();
            if (!usingSQL) {CFM.create(textName.getText(), textId.getText(), textYearLvl.getText(), textGender.getText(), chosenCourse);}
            else {
                DatabaseManager.createStudentRecord(textName.getText(), textId.getText(), textYearLvl.getText(), textGender.getText(), chosenCourse);}
        } else {
            if (!usingSQL) {CFM.create(textName.getText(), textId.getText());}
            else {
                DatabaseManager.createCourseRecord(textName.getText(), textId.getText());}
        }
        if (usingSQL) {
            if (!switchSQL) {
                DatabaseManager.readStudentRecords();
                setLinesListGUI(DatabaseManager.getLinesList());
            }
            else {
                DatabaseManager.readCourseRecords();
                setLinesListGUI(DatabaseManager.getCourseList());
            }
        }
        clearTextFields();
        updateTableModel();
        tableModel.fireTableDataChanged();
    }

    /* Version 2.09 - After March 18 */
    /* Version 2.09 - After March 18 */
    /* Version 2.09 - After March 18 */
    private static void addComboBox(int x, int width, int height, JPanel panel, JComboBox<String> comboBox) {
        JPanel pack = new JPanel(new BorderLayout());
        JLabel text = new JLabel();
        text.setText("Course");
        text.setPreferredSize(new Dimension(70, height));

        pack.setBounds(x, 150, width, height);
        pack.add(text, BorderLayout.WEST);
        pack.add(comboBox,  BorderLayout.CENTER);
        panel.add(pack);
    }
    private static String[] setToArray(Set<String> set) {
        String[] array = new String[set.size() + 1];
        int index = 1;
        array[0] = "None";
        for (String item : set) {
            array[index++] = item;
        }
        return array;
    }
    private void sortTableByName() {
        lines.subList(1, lines.size()).sort(Comparator.comparing(line -> line.split(",")[0]));
        updateTableModel();
    }
    private void sortTableById() {
        lines.subList(1, lines.size()).sort(Comparator.comparing(line -> line.split(",")[1]));
        updateTableModel();
    }
    private void sortTableByYearLevel() {
        lines.subList(1, lines.size()).sort(Comparator.comparing(line -> line.split(",")[2]));
        updateTableModel();
    }
    private void sortTableByGender() {
        lines.subList(1, lines.size()).sort(Comparator.comparing(line -> line.split(",")[3]));
        updateTableModel();
    }
    private void sortTableByCourse() {
        lines.subList(1, lines.size()).sort(Comparator.comparing(line -> line.split(",")[4]));
        updateTableModel();
    }
    private void clearTextFields() {
        if (Objects.equals(fileName, "Student.csv")) {
            textName.setText(""); textId.setText(""); textYearLvl.setText(""); textGender.setText("");
        } else {
            textName.setText(""); textId.setText("");
        }
    }
}