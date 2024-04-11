package org.example.Servlet;

import org.example.Entity.Category;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CategoryServlet extends HttpServlet {
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
            // Запит до бази даних для отримання категорій
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT category_number, category_name FROM Category");

            // Виведення результатів запиту
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                out.println("Category ID: " + id + ", Name: " + name + "<br>");
            }
        } catch (SQLException e) {
            throw new ServletException("Error fetching categories", e);
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
}
