package jm.task.core.jdbc.util;

/*import java.sql.Connection;
import java.sql.DriverManager;*/

import java.sql.*;

public class Util {
    private static Connection connectPost;//static для упрощенного вызова метода.
    private static String loginDb = "postgres";
    private static String paswwdDb = "3444";
    private static String selectDb = "learnpp1";
    private static String pathDb = "jdbc:postgresql://localhost:5432/" + selectDb + "?user=" + loginDb + "&password=" + paswwdDb;
    //не красиво, но и хрен с ним.

    public static void openConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connectPost = DriverManager.getConnection(pathDb);
            System.out.println("Подключение с бд успешно установленно.");
        } catch (Exception e) {
            System.out.println("Подключение с бд не было установленно.");
            System.out.println(e);
        }
    }

    public static void closeConnection() {
        try {
            connectPost.close();
            System.out.println("Соединение с бд закрыто.");
        } catch (Exception e) {
            System.out.println("Ошибка закрытия соединения с бд.");
            System.out.println(e);
        }
    }

    public static String sendQuery(String query) throws SQLException {
        String result = "";

        Statement statement = connectPost.createStatement();

        if (query != null && query.length() != 0) {
            switch (query.charAt(0)) {

                case 's':
                    try {
                        ResultSet res = statement.executeQuery(query);
                        while (res.next()) {
                            result += res.toString();
                        }
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                    break;

                default:
                    try {
                        statement.executeUpdate(query);
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                    break;
            }
        } else {
            return "Empty query.";
        }
        statement.close();
        if (result != null && result.length() != 0) {
            query = result;
        } else {
            query = "noresultata";
        }
        return query;

    }

}