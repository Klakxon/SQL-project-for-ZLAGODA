package org.example.Servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class CustomerCardServlet extends HttpServlet {
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
            // Запит до бази даних для отримання клієнтських карт
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT card_number, cust_surname, cust_name, cust_patronymic, city, street, zip_code, phone_number, percent FROM Customer_Card");

            // Виведення результатів запиту
            while (resultSet.next()) {
                String id = resultSet.getString("card_number");
                String surname = resultSet.getString("cust_surname");
                String name = resultSet.getString("cust_name");
                String patronymic = resultSet.getString("cust_patronymic");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String zipCode = resultSet.getString("zip_code");
                String telephone = resultSet.getString("phone_number");
                int percent = resultSet.getInt("percent");

                out.println("Card Number: " + id + ", Surname: " + surname + ", Name: " + name + ", Patronymic: " + patronymic +
                        ", City: " + city + ", Street: " + street + ", ZIP Code: " + zipCode + ", Telephone: " + telephone +
                        ", Percent: " + percent + "<br>");
            }
        } catch (SQLException e) {
            throw new ServletException("Error fetching customer cards", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addCustomerCard(request, response);
                    break;
                case "update":
                    updateCustomerCard(request, response);
                    break;
                case "delete":
                    deleteCustomerCard(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
        }
    }

    private void addCustomerCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Отримуємо дані з запиту
        String id = request.getParameter("id");
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String zipCode = request.getParameter("zipCode");
        String telephone = request.getParameter("telephone");
        int percent = Integer.parseInt(request.getParameter("percent"));

        // Виконуємо перевірки на коректність даних
        if (id == null || id.isEmpty() || surname == null || surname.isEmpty() || name == null || name.isEmpty() ||
                telephone == null || telephone.isEmpty() || percent < 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing parameters");
            return;
        }

        // Виконуємо додавання клієнтської карти до бази даних
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Customer_Card (card_number, cust_surname, cust_name, cust_patronymic, city, street, zip_code, phone_number, percent) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, id);
            statement.setString(2, surname);
            statement.setString(3, name);
            statement.setString(4, patronymic);
            statement.setString(5, city);
            statement.setString(6, street);
            statement.setString(7, zipCode);
            statement.setString(8, telephone);
            statement.setInt(9, percent);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/customerCard"); // Перенаправлення користувача на потрібну сторінку
        } catch (SQLException e) {
            throw new ServletException("Error adding customer card", e);
        }
    }

    private void updateCustomerCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Отримуємо дані з запиту
        String id = request.getParameter("id");
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String zipCode = request.getParameter("zipCode");
        String telephone = request.getParameter("telephone");
        int percent = Integer.parseInt(request.getParameter("percent"));

        // Виконуємо перевірки на коректність даних
        if (id == null || id.isEmpty() || surname == null || surname.isEmpty() || name == null || name.isEmpty() ||
                telephone == null || telephone.isEmpty() || percent < 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing parameters");
            return;
        }

        // Виконуємо оновлення клієнтської карти в базі даних
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Customer_Card SET cust_surname = ?, cust_name = ?, cust_patronymic = ?, city = ?, street = ?, zip_code = ?, phone_number = ?, percent = ? WHERE card_number = ?");
            statement.setString(1, surname);
            statement.setString(2, name);
            statement.setString(3, patronymic);
            statement.setString(4, city);
            statement.setString(5, street);
            statement.setString(6, zipCode);
            statement.setString(7, telephone);
            statement.setInt(8, percent);
            statement.setString(9, id);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/customerCard"); // Перенаправлення користувача на потрібну сторінку
        } catch (SQLException e) {
            throw new ServletException("Error updating customer card", e);
        }
    }


    private void deleteCustomerCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Отримуємо ID картки з запиту
        String id = request.getParameter("id");

        // Виконуємо перевірки на коректність даних
        if (id == null || id.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing ID parameter");
            return;
        }

        // Виконуємо видалення клієнтської карти з бази даних
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Customer_Card WHERE card_number = ?");
            statement.setString(1, id);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/customerCard"); // Перенаправлення користувача на потрібну сторінку
        } catch (SQLException e) {
            throw new ServletException("Error deleting customer card", e);
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

