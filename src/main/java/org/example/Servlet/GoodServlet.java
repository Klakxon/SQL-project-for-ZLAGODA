package org.example.Servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class GoodServlet extends HttpServlet {
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
            // Запит до бази даних для отримання товарів
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Product");

            // Виведення результатів запиту
            out.println("<h2>Products</h2>");
            while (resultSet.next()) {
                int id = resultSet.getInt("id_product");
                int categoryId = resultSet.getInt("category_number");
                String name = resultSet.getString("product_name");
                String description = resultSet.getString("description");

                out.println("ID: " + id + ", Name: " + name + ", Description: " + description + "<br>");
            }
        } catch (SQLException e) {
            throw new ServletException("Error fetching products", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addProduct(request, response);
                    break;
                case "update":
                    updateProduct(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
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

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Product (product_name, description) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/goods"); // Перенаправлення користувача на сторінку зі списком товарів
        } catch (SQLException e) {
            throw new ServletException("Error adding product", e);
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Product SET product_name = ?, description = ? WHERE id_product = ?");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, id);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/goods"); // Перенаправлення користувача на сторінку зі списком товарів
        } catch (SQLException e) {
            throw new ServletException("Error updating product", e);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Product WHERE id_product = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/goods"); // Перенаправлення користувача на сторінку зі списком товарів
        } catch (SQLException e) {
            throw new ServletException("Error deleting product", e);
        }
    }
}
