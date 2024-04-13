package org.example.Servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ReceiptServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Підключення до бази даних SQLite
            String url = "jdbc:sqlite:src/main/Database/ZLAGODA.sqlite3";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new ServletException("Error connecting to database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Запит до бази даних для отримання чеків
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer_Сheck");

            // Виведення результатів запиту
            out.println("<h2>Receipts</h2>");
            while (resultSet.next()) {
                String id = resultSet.getString("check_number");
                String idEmployee = resultSet.getString("id_employee");
                String idCard = resultSet.getString("card_number");
                double total = resultSet.getDouble("sum_total");
                double vat = resultSet.getDouble("vat");
                Date date = resultSet.getDate("print_date");

                out.println("Receipt ID: " + id + ", Employee ID: " + idEmployee + ", Card ID: " + idCard + ", Total: " + total + ", VAT: " + vat + ", Date: " + date + "<br>");
            }
        } catch (SQLException e) {
            throw new ServletException("Error fetching receipts", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addReceipt(request, response);
                    break;
                case "update":
                    updateReceipt(request, response);
                    break;
                case "delete":
                    deleteReceipt(request, response);
                    break;
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addReceipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String idEmployee = request.getParameter("idEmployee");
        String idCard = request.getParameter("idCard");
        double total = Double.parseDouble(request.getParameter("total"));
        double vat = Double.parseDouble(request.getParameter("vat"));
        String dateStr = request.getParameter("date");
        Date date = null;
        try {
            date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Customer_Сheck (check_number, id_employee, card_number, sum_total, vat, print_date) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, id);
            statement.setString(2, idEmployee);
            statement.setString(3, idCard);
            statement.setDouble(4, total);
            statement.setDouble(5, vat);
            statement.setDate(6, new java.sql.Date(date.getTime()));
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/receipts"); // Перенаправлення користувача на сторінку зі списком чеків
        } catch (SQLException e) {
            throw new ServletException("Error adding receipt", e);
        }
    }

    private void updateReceipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String idEmployee = request.getParameter("idEmployee");
        String idCard = request.getParameter("idCard");
        double total = Double.parseDouble(request.getParameter("total"));
        double vat = Double.parseDouble(request.getParameter("vat"));
        String dateStr = request.getParameter("date");
        Date date = null;
        try {
            date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Customer_Сheck SET id_employee = ?, card_number = ?, sum_total = ?, vat = ?, print_date = ? WHERE check_number = ?");
            statement.setString(1, idEmployee);
            statement.setString(2, idCard);
            statement.setDouble(3, total);
            statement.setDouble(4, vat);
            statement.setDate(5, new java.sql.Date(date.getTime()));
            statement.setString(6, id);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/receipts"); // Перенаправлення користувача на сторінку зі списком чеків
        } catch (SQLException e) {
            throw new ServletException("Error updating receipt", e);
        }
    }

    private void deleteReceipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Customer_Сheck WHERE check_number = ?");
            statement.setString(1, id);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/receipts"); // Перенаправлення користувача на сторінку зі списком чеків
        } catch (SQLException e) {
            throw new ServletException("Error deleting receipt", e);
        }
    }
}

