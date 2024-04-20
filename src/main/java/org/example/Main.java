package org.example;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Встановлення з'єднання з сервлетом
            URL url = new URL("http://localhost:8080/sql-zlagoda/сategory"); // Замініть URL на адресу вашого сервлета
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Параметри для додавання категорії
            String params = "action=add&name=something"; // Замініть NewCategoryName на бажану назву нової категорії

            // Відправка параметрів
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params.getBytes());
            outputStream.flush();

            // Отримання відповіді
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Зчитування відповіді
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Виведення відповіді
            System.out.println("Response: " + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}