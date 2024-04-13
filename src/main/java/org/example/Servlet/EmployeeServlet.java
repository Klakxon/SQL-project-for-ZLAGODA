package org.example.Servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeServlet extends HttpServlet {
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
            // Запит до бази даних для отримання працівників
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Employee");

            // Виведення результатів запиту
            while (resultSet.next()) {
                String id = resultSet.getString("id_employee");
                String surname = resultSet.getString("empl_surname");
                String name = resultSet.getString("empl_name");
                String patronymic = resultSet.getString("empl_patronymic");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String zipCode = resultSet.getString("zip_code");
                String telephone = resultSet.getString("phone_number");
                Date birthday = resultSet.getDate("date_of_birth");
                Date startWork = resultSet.getDate("date_of_start");
                double salary = resultSet.getDouble("salary");
                String role = resultSet.getString("empl_role");

                out.println("ID: " + id + ", Surname: " + surname + ", Name: " + name + ", Patronymic: " + patronymic +
                        ", City: " + city + ", Street: " + street + ", ZIP Code: " + zipCode + ", Telephone: " + telephone +
                        ", Birthday: " + formatDate(birthday) + ", Start Work: " + formatDate(startWork) + ", Salary: " +
                        salary + ", Role: " + role + "<br>");
            }
        } catch (SQLException e) {
            throw new ServletException("Error fetching employees", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addEmployee(request, response);
                    break;
                case "update":
                    updateEmployee(request, response);
                    break;
                case "delete":
                    deleteEmployee(request, response);
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

    private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Отримання даних з запиту
        String id = request.getParameter("id");
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String position = request.getParameter("position");
        double salary = Double.parseDouble(request.getParameter("salary"));
        String startWorkStr = request.getParameter("startWork");
        String birthdayStr = request.getParameter("birthday");
        String telephone = request.getParameter("telephone");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        int building = Integer.parseInt(request.getParameter("building"));
        String zipCode = request.getParameter("zipCode");

        try {
            // Підготовка дат для бази даних
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startWork = sdf.parse(startWorkStr);
            Date birthday = sdf.parse(birthdayStr);

            // Опрацювання SQL-запиту для додавання працівника в базу даних
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Employee (id_employee, empl_surname, empl_name, empl_patronymic, empl_role, salary, date_of_start, date_of_birthday, phone_number, city, street, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, id);
            statement.setString(2, surname);
            statement.setString(3, name);
            statement.setString(4, patronymic);
            statement.setString(5, position);
            statement.setDouble(6, salary);
            statement.setDate(7, new java.sql.Date(startWork.getTime()));
            statement.setDate(8, new java.sql.Date(birthday.getTime()));
            statement.setString(9, telephone);
            statement.setString(10, city);
            statement.setString(11, street);
            statement.setString(12, zipCode);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/employee"); // Перенаправлення користувача на сторінку зі списком працівників
        } catch (SQLException | ParseException e) {
            throw new ServletException("Error adding employee", e);
        }
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Отримання даних з запиту
        String id = request.getParameter("id");
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String patronymic = request.getParameter("patronymic");
        String position = request.getParameter("position");
        double salary = Double.parseDouble(request.getParameter("salary"));
        String startWorkStr = request.getParameter("startWork");
        String birthdayStr = request.getParameter("birthday");
        String telephone = request.getParameter("telephone");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        int building = Integer.parseInt(request.getParameter("building"));
        String zipCode = request.getParameter("zipCode");

        try {
            // Підготовка дат для бази даних
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startWork = sdf.parse(startWorkStr);
            Date birthday = sdf.parse(birthdayStr);

            // Опрацювання SQL-запиту для оновлення працівника в базі даних
            PreparedStatement statement = connection.prepareStatement("UPDATE Employee SET empl_surname = ?, empl_name = ?, empl_patronymic = ?, empl_role = ?, salary = ?, date_of_start = ?, date_of_birthday = ?, phone_number = ?, city = ?, street = ?, zip_code = ? WHERE id_employee = ?");
            statement.setString(1, surname);
            statement.setString(2, name);
            statement.setString(3, patronymic);
            statement.setString(4, position);
            statement.setDouble(5, salary);
            statement.setDate(6, new java.sql.Date(startWork.getTime()));
            statement.setDate(7, new java.sql.Date(birthday.getTime()));
            statement.setString(8, telephone);
            statement.setString(9, city);
            statement.setString(10, street);
            statement.setString(11, zipCode);
            statement.setString(12, id);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/employee"); // Перенаправлення користувача на сторінку зі списком працівників
        } catch (SQLException | ParseException e) {
            throw new ServletException("Error updating employee", e);
        }
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Отримання ID працівника з запиту
        String id = request.getParameter("id");

        try {
            // Опрацювання SQL-запиту для видалення працівника з бази даних
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Employee WHERE id_employee = ?");
            statement.setString(1, id);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/employee"); // Перенаправлення користувача на сторінку зі списком працівників
        } catch (SQLException e) {
            throw new ServletException("Error deleting employee", e);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
