package org.example.Servlet;

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
            // Connecting to the SQLite database
            String url = "jdbc:sqlite:src/main/Database/ZLAGODA.sqlite3";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new ServletException("Error connecting to database", e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Querying the database to get categories
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT category_number, category_name FROM Category");

            // Outputting the results of the query
            while (resultSet.next()) {
                int id = resultSet.getInt("category_number");
                String name = resultSet.getString("category_name");
                out.println("Category ID: " + id + ", Name: " + name + "<br>");
            }
        } catch (SQLException e) {
            throw new ServletException("Error fetching categories", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addCategory(request, response);
                    break;
                case "update":
                    updateCategory(request, response);
                    break;
                case "delete":
                    deleteCategory(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name != null && !name.isEmpty()) {
            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Category (category_name) VALUES (?)");
                statement.setString(1, name);
                statement.executeUpdate();
                response.sendRedirect(request.getContextPath() + "/category");
            } catch (SQLException e) {
                throw new ServletException("Error adding category", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name parameter is missing or empty");
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String name = request.getParameter("name");
        if (idString != null && name != null && !name.isEmpty()) {
            try {
                int id = Integer.parseInt(idString);
                PreparedStatement statement = connection.prepareStatement("UPDATE Category SET category_name = ? WHERE category_number = ?");
                statement.setString(1, name);
                statement.setInt(2, id);
                statement.executeUpdate();
                response.sendRedirect(request.getContextPath() + "/category");
            } catch (NumberFormatException | SQLException e) {
                throw new ServletException("Error updating category", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID or name parameter is missing or empty");
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        if (idString != null) {
            try {
                int id = Integer.parseInt(idString);
                PreparedStatement statement = connection.prepareStatement("DELETE FROM Category WHERE category_number = ?");
                statement.setInt(1, id);
                statement.executeUpdate();
                response.sendRedirect(request.getContextPath() + "/category");
            } catch (NumberFormatException | SQLException e) {
                throw new ServletException("Error deleting category", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID parameter is missing");
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
