import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileManagerGUI extends JFrame {
    public static List<String> lines = new ArrayList<>();
    public static CsvFileManager CFM;
    final FileManagerSwitchedListener switchListener;
    final JLabel fileManager__title = new JLabel();
    private JPanel panelMain;
    private JPanel panelFooter;
    private JPanel panelSideLeft;
    private JPanel panelSideRight;
    final JButton addItemButton = createButton("Add Item", Color.BLUE);
    final JButton editButton = createButton("Edit", Color.LIGHT_GRAY);
    final JButton finishButton = createButton("Finish", Color.GREEN);
    final JButton deleteButton = createButton("Delete", Color.RED);
    final JButton switchButton = createButton("Switch", Color.BLACK);
    private JTable dataTable;
    final JTextField textName = new JTextField();
    final JTextField textId = new JTextField();
    final JTextField textYearLvl = new JTextField();
    final JTextField textGender = new JTextField();
    final JTextField textCourse = new JTextField();
    private DefaultTableModel tableModel;

    public FileManagerGUI(
            PrintWriter fileObject,
            String fileName,
            String[] header,
            String initialHead,
            FileManagerSwitchedListener switchListener
    ) {
        this.switchListener = switchListener;
        if (!fileExists(fileName)) {
            CFM = new CsvFileManager(fileObject, fileName, initialHead);
        } else {
            CFM = new CsvFileManager(fileObject, fileName);
        }

        initializeUI(fileName);
        initializeTableModel(header);
        initializeListeners(fileObject);

        // Set up JFrame properties
        setTitle("CSV File Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        tableModel.fireTableDataChanged();
    }

    private void initializeUI(String fileName) {
        panelMain = new JPanel(new BorderLayout());
        int margin = 20; // Set the margin size
        panelMain.setBorder(BorderFactory.createEmptyBorder(margin, margin-10, margin, margin-10));
        panelMain.setMinimumSize(new Dimension(840, 500));
        setContentPane(panelMain);

        /* Editor Pane */
        panelSideLeft = new JPanel(null);
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
        if (fileName == "Student.csv") {
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
        panelFooter = new JPanel(new BorderLayout());
            JPanel panelFooterLeft = new JPanel();
                panelFooterLeft.add(editButton);
                panelFooterLeft.add(deleteButton);
            JPanel panelFooterRight = new JPanel();
                panelFooterRight.add(switchButton);
                panelFooterRight.add(finishButton);
        panelFooter.add(panelFooterLeft, BorderLayout.WEST);
        panelFooter.add(panelFooterRight, BorderLayout.EAST);
        panelSideRight.add(panelFooter, BorderLayout.SOUTH);
    }
    private void initializeTableModel(String[] header) {
        // Initialize the table model with column names
        tableModel = new DefaultTableModel(null, header);
        dataTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(dataTable);
        panelSideRight.add(scrollPane, BorderLayout.CENTER);
        setLinesListGUI(CFM.getLinesList());
    }
    private void updateTableModel() {
        // Clear the existing rows
        tableModel.setRowCount(0);
        // Populate the model with data from the updated lines list
        for (String line : lines) {
            String[] rowData = line.split(",");
            tableModel.addRow(rowData);
        }
        tableModel.fireTableDataChanged();
    }
    private void initializeListeners(PrintWriter fileObject) {
        addItemButton.addActionListener(e -> {
            // Use the existing tableModel instead of creating a new one
            tableModel.addRow(new Object[]{textName.getText(), textId.getText(), textYearLvl.getText(), textGender.getText(), textCourse.getText()});
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

        switchButton.addActionListener(e -> {
            CFM.clearCsvFile();
            for (int i = 1; i < tableModel.getRowCount(); i++) {
                StringBuilder csvLine = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    csvLine.append(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) {
                        csvLine.append(",");
                    }
                }
                CFM.create(csvLine.toString());
            }
            setFinishedGUI(lines);
            lines.clear();
            onWindowSwitched();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispose();
            fileObject.close();
        });

        finishButton.addActionListener(e -> {
            CFM.clearCsvFile();
            for (int i = 1; i < tableModel.getRowCount(); i++) {
                StringBuilder csvLine = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    csvLine.append(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) {
                        csvLine.append(",");
                    }
                }
                CFM.create(csvLine.toString());
            }
            System.out.println("\nFile has been updated and written...");
            setFinishedGUI(lines);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dispose();
            fileObject.close();
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
    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);  // Assuming you want white text on colored buttons
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

    public void setLinesListGUI(List<String> lines) {
        FileManagerGUI.lines = lines;
        updateTableModel();
    }
}