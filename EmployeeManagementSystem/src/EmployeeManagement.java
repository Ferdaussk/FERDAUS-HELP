import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeManagement extends JFrame {
    private JTextField nameField, positionField, salaryField;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EmployeeManagement() {
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Position:"));
        positionField = new JTextField();
        inputPanel.add(positionField);

        inputPanel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonListener());
        inputPanel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateButtonListener());
        inputPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteButtonListener());
        inputPanel.add(deleteButton);

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(new ViewButtonListener());
        inputPanel.add(viewButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table for displaying employee records
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Position", "Salary"}, 0);
        employeeTable = new JTable(tableModel);
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try (Connection conn = EmployeeDB.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employees");

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String position = positionField.getText();
            String salary = salaryField.getText();

            try (Connection conn = EmployeeDB.getConnection()) {
                String query = "INSERT INTO Employees (name, position, salary) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, position);
                pstmt.setDouble(3, Double.parseDouble(salary));
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(EmployeeManagement.this, "Employee added successfully!");
                refreshTable();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(EmployeeManagement.this, "Error: " + ex.getMessage());
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(EmployeeManagement.this, "Please select an employee to update.");
                return;
            }

            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = nameField.getText();
            String position = positionField.getText();
            String salary = salaryField.getText();

            try (Connection conn = EmployeeDB.getConnection()) {
                String query = "UPDATE Employees SET name = ?, position = ?, salary = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, position);
                pstmt.setDouble(3, Double.parseDouble(salary));
                pstmt.setInt(4, id);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(EmployeeManagement.this, "Employee updated successfully!");
                refreshTable();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(EmployeeManagement.this, "Error: " + ex.getMessage());
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(EmployeeManagement.this, "Please select an employee to delete.");
                return;
            }

            int id = (int) tableModel.getValueAt(selectedRow, 0);

            try (Connection conn = EmployeeDB.getConnection()) {
                String query = "DELETE FROM Employees WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(EmployeeManagement.this, "Employee deleted successfully!");
                refreshTable();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(EmployeeManagement.this, "Error: " + ex.getMessage());
            }
        }
    }

    private class ViewButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refreshTable();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagement app = new EmployeeManagement();
            app.setVisible(true);
        });
    }
}
