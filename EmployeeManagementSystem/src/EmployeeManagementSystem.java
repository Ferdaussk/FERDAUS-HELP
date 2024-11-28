import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/EmployeeDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private JFrame frame;
    private JTextField nameField, positionField, salaryField;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                EmployeeManagementSystem window = new EmployeeManagementSystem();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public EmployeeManagementSystem() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 10, 100, 25);
        frame.getContentPane().add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 10, 150, 25);
        frame.getContentPane().add(nameField);

        JLabel positionLabel = new JLabel("Position:");
        positionLabel.setBounds(10, 50, 100, 25);
        frame.getContentPane().add(positionLabel);

        positionField = new JTextField();
        positionField.setBounds(120, 50, 150, 25);
        frame.getContentPane().add(positionField);

        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(10, 90, 100, 25);
        frame.getContentPane().add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setBounds(120, 90, 150, 25);
        frame.getContentPane().add(salaryField);

        JButton addButton = new JButton("Add");
        addButton.setBounds(10, 130, 100, 30);
        addButton.addActionListener(e -> addEmployee());
        frame.getContentPane().add(addButton);

        JButton viewButton = new JButton("View");
        viewButton.setBounds(120, 130, 100, 30);
        viewButton.addActionListener(e -> viewEmployees());
        frame.getContentPane().add(viewButton);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(230, 130, 100, 30);
        updateButton.addActionListener(e -> updateEmployee());
        frame.getContentPane().add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(340, 130, 100, 30);
        deleteButton.addActionListener(e -> deleteEmployee());
        frame.getContentPane().add(deleteButton);

        String[] columnNames = {"ID", "Name", "Position", "Salary"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setBounds(10, 170, 560, 180);
        frame.getContentPane().add(scrollPane);
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void addEmployee() {
        String name = nameField.getText();
        String position = positionField.getText();
        String salary = salaryField.getText();

        if (name.isEmpty() || position.isEmpty() || salary.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields!");
            return;
        }

        String query = "INSERT INTO Employees (name, position, salary) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, position);
            pst.setString(3, salary);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Employee added successfully!");
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding employee.");
        }
    }

    private void viewEmployees() {
        String query = "SELECT * FROM Employees";
        try (Connection conn = connect(); PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            tableModel.setRowCount(0); // clear existing data
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("position"),
                    rs.getDouble("salary")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error retrieving employees.");
        }
    }

    private void updateEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an employee to update.");
            return;
        }

        int id = (int) employeeTable.getValueAt(selectedRow, 0);
        String name = nameField.getText();
        String position = positionField.getText();
        String salary = salaryField.getText();

        String query = "UPDATE Employees SET name = ?, position = ?, salary = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, name);
            pst.setString(2, position);
            pst.setString(3, salary);
            pst.setInt(4, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Employee updated successfully!");
            clearFields();
            viewEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating employee.");
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an employee to delete.");
            return;
        }

        int id = (int) employeeTable.getValueAt(selectedRow, 0);
        String query = "DELETE FROM Employees WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Employee deleted successfully!");
            viewEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error deleting employee.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        positionField.setText("");
        salaryField.setText("");
    }
}
