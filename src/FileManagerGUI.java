import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class FileManagerGUI extends JFrame {
    public static List<String> lines = new ArrayList<>();
    final JLabel fileManager__title = new JLabel();
    private JPanel panelMain;
    private JPanel panelFooter;
    private JPanel panelSideLeft;
    private JPanel panelSideRight;
    final JButton addItemButton = createButton("Add Item", Color.BLUE);
    final JButton editButton = createButton("Edit", Color.LIGHT_GRAY);
    final JButton finishButton = createButton("Finish", Color.GREEN);
    final JButton deleteButton = createButton("Delete", Color.RED);
    private JTable dataTable;
    final JTextField textName = new JTextField();
    final JTextField textId = new JTextField();
    final JTextField textYearLvl = new JTextField();
    final JTextField textGender = new JTextField();
    final JTextField textCourse = new JTextField();
    private DefaultTableModel tableModel;

    public FileManagerGUI(String[] header) {
        initializeUI();
        initializeTableModel(header);
        initializeListeners();
        panelSettings();

        // Set up JFrame properties
        setTitle("CSV File Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        tableModel.fireTableDataChanged();
    }
    private void panelSettings() {
//        panelMain.setBackground(Color.BLUE);
        panelFooter.setBackground(Color.RED);
    }
    private void initializeUI() {
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
            addTextField(X_AXIS,  90,TEXT_WIDTH, TEXT_HEIGHT, panelSideLeft, "Year Lvl", textYearLvl);
            addTextField(X_AXIS, 120,TEXT_WIDTH, TEXT_HEIGHT, panelSideLeft, "Gender", textGender);
            addTextField(X_AXIS, 150,TEXT_WIDTH, TEXT_HEIGHT, panelSideLeft, "Course", textCourse);
            addItemButton.setBounds(100, 180, 105, 20);

            panelSideLeft.add(addItemButton);
        panelMain.add(panelSideLeft, BorderLayout.WEST);

        /* Content Pane */
        panelSideRight = new JPanel(new BorderLayout());
        panelSideRight.setPreferredSize(new Dimension(500, 500));
        panelSideRight.setLayout(new BorderLayout());
        panelMain.add(panelSideRight, BorderLayout.CENTER);

        panelSideRight.setBorder(new EmptyBorder(0, 10, 0, 0));
            panelFooter = new JPanel();
            panelFooter.add(editButton);
            panelFooter.add(deleteButton);
            panelFooter.add(finishButton);
        panelSideRight.add(panelFooter, BorderLayout.SOUTH);
    }
    private void initializeTableModel(String[] header) {
        // Initialize the table model with column names
        tableModel = new DefaultTableModel(null, header);
        dataTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(dataTable);
        panelSideRight.add(scrollPane, BorderLayout.CENTER);
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
    private void initializeListeners() {
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

        finishButton.addActionListener(e -> dispose());
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

    public void setLinesListGUI(List<String> lines) {
        FileManagerGUI.lines = lines;
        updateTableModel();
    }
}
