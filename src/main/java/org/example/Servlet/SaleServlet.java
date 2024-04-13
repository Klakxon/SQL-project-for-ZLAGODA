package org.example.Servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class SaleServlet extends HttpServlet {
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
            // Запит до бази даних для отримання продажів
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Sale");

            // Виведення результатів запиту
            out.println("<h2>Sales</h2>");
            while (resultSet.next()) {
                String upc = resultSet.getString("UPC");
                String checkNumber = resultSet.getString("check_number");
                int productNumber = resultSet.getInt("product_number");
                double sellingPrice = resultSet.getDouble("selling_price");

                out.println("UPC: " + upc + ", Check Number: " + checkNumber + ", Product Number: " + productNumber + ", Selling Price: " + sellingPrice + "<br>");
            }
        } catch (SQLException e) {
            throw new ServletException("Error fetching sales", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addSale(request, response);
                    break;
                case "update":
                    updateSale(request, response);
                    break;
                case "delete":
                    deleteSale(request, response);
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

    private void addSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String upc = request.getParameter("upc");
        String checkNumber = request.getParameter("checkNumber");
        int productNumber = Integer.parseInt(request.getParameter("productNumber"));
        double sellingPrice = Double.parseDouble(request.getParameter("sellingPrice"));

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Sale (UPC, check_number, product_number, selling_price) VALUES (?, ?, ?, ?)");
            statement.setString(1, upc);
            statement.setString(2, checkNumber);
            statement.setInt(3, productNumber);
            statement.setDouble(4, sellingPrice);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/sales"); // Перенаправлення користувача на сторінку зі списком продажів
        } catch (SQLException e) {
            throw new ServletException("Error adding sale", e);
        }
    }

    private void updateSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String upc = request.getParameter("upc");
        String checkNumber = request.getParameter("checkNumber");
        int productNumber = Integer.parseInt(request.getParameter("productNumber"));
        double sellingPrice = Double.parseDouble(request.getParameter("sellingPrice"));

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Sale SET selling_price = ? WHERE UPC = ? AND check_number = ? AND product_number = ?");
            statement.setDouble(1, sellingPrice);
            statement.setString(2, upc);
            statement.setString(3, checkNumber);
            statement.setInt(4, productNumber);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/sales"); // Перенаправлення користувача на сторінку зі списком продажів
        } catch (SQLException e) {
            throw new ServletException("Error updating sale", e);
        }
    }

    private void deleteSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String upc = request.getParameter("upc");
        String checkNumber = request.getParameter("checkNumber");
        int productNumber = Integer.parseInt(request.getParameter("productNumber"));

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Sale WHERE UPC = ? AND check_number = ? AND product_number = ?");
            statement.setString(1, upc);
            statement.setString(2, checkNumber);
            statement.setInt(3, productNumber);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/sales"); // Перенаправлення користувача на сторінку зі списком продажів
        } catch (SQLException e) {
            throw new ServletException("Error deleting sale", e);
        }
    }
}

