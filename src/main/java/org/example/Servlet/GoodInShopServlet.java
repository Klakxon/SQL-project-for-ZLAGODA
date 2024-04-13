package org.example.Servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class GoodInShopServlet extends HttpServlet {
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
            // Запит до бази даних для отримання товарів у магазині
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Store_product");

            // Виведення результатів запиту
            out.println("<h2>Products in Shop</h2>");
            while (resultSet.next()) {
                String upc = resultSet.getString("UPC");
                int idGood = resultSet.getInt("id_product");
                double price = resultSet.getDouble("selling_price");
                int amount = resultSet.getInt("product_number");
                boolean isPromotional = resultSet.getBoolean("promotional_product");

                out.println("UPC: " + upc + ", ID: " + idGood + ", Price: " + price + ", Amount: " + amount + ", Promotional: " + isPromotional + "<br>");
            }
        } catch (SQLException e) {
            throw new ServletException("Error fetching products in shop", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addProductInShop(request, response);
                    break;
                case "update":
                    updateProductInShop(request, response);
                    break;
                case "delete":
                    deleteProductInShop(request, response);
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

    private void addProductInShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String upc = request.getParameter("upc");
        int idGood = Integer.parseInt(request.getParameter("idGood"));
        double price = Double.parseDouble(request.getParameter("price"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        boolean isPromotional = Boolean.parseBoolean(request.getParameter("isPromotional"));

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Store_product (UPC, id_product, selling_price, product_number, promotional_product) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, upc);
            statement.setInt(2, idGood);
            statement.setDouble(3, price);
            statement.setInt(4, amount);
            statement.setBoolean(5, isPromotional);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/products-in-shop"); // Перенаправлення користувача на сторінку зі списком товарів у магазині
        } catch (SQLException e) {
            throw new ServletException("Error adding product in shop", e);
        }
    }

    private void updateProductInShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String upc = request.getParameter("upc");
        int idGood = Integer.parseInt(request.getParameter("idGood"));
        double price = Double.parseDouble(request.getParameter("price"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        boolean isPromotional = Boolean.parseBoolean(request.getParameter("isPromotional"));

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Store_product SET selling_price = ?, product_number = ?, promotional_product = ? WHERE UPC = ? AND id_product = ?");
            statement.setDouble(1, price);
            statement.setInt(2, amount);
            statement.setBoolean(3, isPromotional);
            statement.setString(4, upc);
            statement.setInt(5, idGood);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/products-in-shop"); // Перенаправлення користувача на сторінку зі списком товарів у магазині
        } catch (SQLException e) {
            throw new ServletException("Error updating product in shop", e);
        }
    }

    private void deleteProductInShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String upc = request.getParameter("upc");
        int idGood = Integer.parseInt(request.getParameter("idGood"));

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Store_product WHERE UPC = ? AND id_product = ?");
            statement.setString(1, upc);
            statement.setInt(2, idGood);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/products-in-shop"); // Перенаправлення користувача на сторінку зі списком товарів у магазині
        } catch (SQLException e) {
            throw new ServletException("Error deleting product in shop", e);
        }
    }
}

