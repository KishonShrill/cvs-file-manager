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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FileManagerGUI extends JFrame {
    public static List<String> lines = new ArrayList<>();
    Set<String> courseIds;
    public static CsvFileManager CFM;
    final FileManagerSwitchedListener switchListener;
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
    final JTextField textCourse = new JTextField();
    private static final int MIN_PANEL_WIDTH = 400;
    private static final int MIN_PANEL_HEIGHT = 50;
    private DefaultTableModel tableModel;
    private JComboBox<String> searchColumnComboBox;
    private JTextField searchField;
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
    ) {
        this.switchListener = switchListener;
        this.courseIds = courseIds;
        if (!fileExists(fileName)) {
            CFM = new CsvFileManager(fileObject, fileName, initialHead, courseIds);
        } else {
            CFM = new CsvFileManager(fileObject, fileName, courseIds);
        }

        initializeUI(fileName, header);
        initializeTableModel(header, fileName);
        initializeListeners(fileName, fileObject);

        // Set up JFrame properties
        setTitle("CSV File Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        if (firstTime) displayTutorial();
        tableModel.fireTableDataChanged();
    }

    private void initializeUI(String fileName, String[] header) {
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
            addTextField(X_AXIS, 150,TEXT_WIDTH, TEXT_HEIGHT, panelSideLeft, "Course", textCourse);
        }
        addItemButton.setBounds(100, 180, 105, 20);

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

        JLabel searchLabel = new JLabel("Search By:");
        searchColumnComboBox = new JComboBox<>(header);
        searchField = new JTextField(20);

        // Add components to the respective panels
        searchInputPanel.add(searchLabel);
        searchInputPanel.add(searchColumnComboBox);
        searchInputPanel.add(searchField);

        // Add panels to the search panel
        searchPanel.add(searchInputPanel, BorderLayout.CENTER);

        // Add search panel to the main panel
        panelSideRight.add(searchPanel, BorderLayout.NORTH);
    }
    private void initializeTableModel(String[] header, String fileName) {
        if (Objects.equals(fileName, "Course.csv")) {
            tableModel = new DefaultTableModel(null, header) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make all cells non-editable
                    return false;
                }
            };
            dataTable = new JTable(tableModel);
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
        setLinesListGUI(CFM.getLinesList());
    }
    private void updateTableModel() {
        // Clear the existing rows
        tableModel.setRowCount(0);

        // Start the loop from index 1 to exclude the header row
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] rowData = line.split(",");
            Object[] updatedRowData = new Object[rowData.length];

            for (int j = 0; j < rowData.length; j++) {
                if (j == 4) {
                    String courseId = rowData[j].trim();
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
        String searchText = searchField.getText().toLowerCase(); // Convert search text to lowercase for case-insensitive search
        int columnIndex = searchColumnComboBox.getSelectedIndex(); // Get the index of the selected column to search

        // Clear the existing rows
        tableModel.setRowCount(0);

        // Start the loop from index 1 to exclude the header row
        for (int i = 1; i < lines.size(); i++) {
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
                        String courseId = rowData[j].trim();
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

    private void initializeListeners(String fileName, PrintWriter fileObject) {
        addItemButton.addActionListener(e -> {
            if (textCourse.getText().isEmpty()) {
                textCourse.setText("Not Enrolled");
            }
            if (Objects.equals(fileName, "Student.csv")) {
                CFM.create(textName.getText(), textId.getText(), textYearLvl.getText(), textGender.getText(), textCourse.getText());
                textName.setText(""); textId.setText(""); textYearLvl.setText(""); textGender.setText(""); textCourse.setText("");
            } else {
                CFM.create(textName.getText(), textId.getText());
                textName.setText(""); textId.setText("");
            }
            updateTableModel();
            tableModel.fireTableDataChanged();
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

        editButton.addActionListener(e -> {
            String yearLvl = "", gender = "", course = "";
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow != -1) {
                String nameToUpdate = (String) tableModel.getValueAt(selectedRow, 0);
                String name = (String) tableModel.getValueAt(selectedRow, 0);
                String id = (String) tableModel.getValueAt(selectedRow, 1);
                if (Objects.equals(fileName, "Student.csv")) {
                    yearLvl = (String) tableModel.getValueAt(selectedRow, 2);
                    gender = (String) tableModel.getValueAt(selectedRow, 3);
                    course = (String) tableModel.getValueAt(selectedRow, 4);
                }

                showEditDialog(nameToUpdate, name, id, yearLvl, gender, course, fileName);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to edit.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow != -1) {
                String nameToDelete = (String) tableModel.getValueAt(selectedRow, 0);
                tableModel.removeRow(selectedRow);
                CFM.deleteDataByName(nameToDelete);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        });

        switchButton.addActionListener(e -> {
            setFinishedGUI(lines);
            lines.clear();
            onWindowSwitched();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispose();
            fileObject.close();
        });

        finishButton.addActionListener(e -> {
            System.out.println("\nFile has been updated and written...");
            setFinishedGUI(lines);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispose();
            fileObject.close();
        });
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                search();
            }
        });
    }
    private void onWindowSwitched() {
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
    private void showEditDialog(String nameToUpdate, String name, String id, String yearLvl, String gender, String course, String fileName) {
        JDialog editDialog = new JDialog(this, "Edit Item", true);
        editDialog.setLayout(new BorderLayout());

        JPanel editPanel = new JPanel(new GridLayout(6, 2));
        JTextField editNameField = new JTextField(name);
        JTextField editIdField = new JTextField(id);
        JTextField editYearLvlField = new JTextField(yearLvl);
        JTextField editGenderField = new JTextField(gender);
        JTextField editCourseField = new JTextField(course);

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
            editPanel.add(editCourseField);
        }

        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            if (editCourseField.getText().isEmpty()) {
                editCourseField.setText("Not Enrolled");
            }
            String[] updateList = {editNameField.getText(), editIdField.getText(), editYearLvlField.getText(), editGenderField.getText(), editCourseField.getText()};
            CFM.updateDataByName(nameToUpdate, updateList);
            updateTableModel();
            tableModel.fireTableDataChanged();
            search();
            editDialog.dispose();
        });

        editDialog.add(editPanel, BorderLayout.CENTER);
        editDialog.add(updateButton, BorderLayout.SOUTH);
        editDialog.setSize(300, 200);
        editDialog.setLocationRelativeTo(this);
        editDialog.setVisible(true);
    }
    private static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists() && !file.isDirectory() && file.length() > 0;
    }
    private boolean isCourseAvailable(String courseId) {
        return !"N/A".equals(courseId);
    }
    public void setLinesListGUI(List<String> lines) {
        FileManagerGUI.lines = lines;
        updateTableModel();
    }
}