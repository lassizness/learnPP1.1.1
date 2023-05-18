package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.Main;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "";

        if (Main.getQueryList("createTable") == null) {
            query = "CREATE TABLE IF NOT EXISTS Users (id SERIAL PRIMARY KEY, name varchar(255), lastname varchar(255), age smallint);";
        } else {
            query = Main.getQueryList("createTable");
        }

        if (Util.openConnection()) {
            try {
                Util.sendQuery(query);
            } catch (Exception e) {
                System.out.println(e);
            }
            Util.closeConnection();
        }
    }

    public void dropUsersTable() {
        String query = "";

        if (Main.getQueryList("dropTable") == null) {
            query = "DROP TABLE IF EXISTS Users;";
        } else {
            query = Main.getQueryList("dropTable");
        }

        if (Util.openConnection()) {
            try {
                Util.sendQuery(query);
            } catch (Exception e) {
                System.out.println(e);
            }
            Util.closeConnection();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        String query = "";

        if (Main.getQueryList("addUser") == null) {
            query = "INSERT INTO Users (name, lastname, age) VALUES ";
        } else {
            query = Main.getQueryList("addUser");
        }

        if (Util.openConnection()) {
            try {
                Util.sendQuery(query + "('" + name + "','" + lastName + "'," + age + ");");
            } catch (Exception e) {
                System.out.println(e);
            }
            Util.closeConnection();
        }
    }

    public void removeUserById(long id) {
        String query = "";

        if (Main.getQueryList("delUser") == null) {
            query = "DELETE FROM Users WHERE ";
        } else {
            query = Main.getQueryList("delUser");
        }

        if (Util.openConnection()) {
            try {
                Util.sendQuery(Main.getQueryList(query) + "id=" + id);
            } catch (Exception e) {
                System.out.println(e);
            }
            Util.closeConnection();
        }
    }

    public List<User> getAllUsers() {

        List<User> listUsers = new ArrayList<>();
        String[][] str = new String[0][0];
        String query = "";
        String temp = "";

        if (Main.getQueryList("allUser") == null) {
            query = "SELECT * FROM Users;";
        } else {
            query = Main.getQueryList("allUser");
        }

        if (Util.openConnection()) {
            try {
                temp = Util.sendQuery(query);
                if (temp.indexOf(":") == -1) {
                    temp = "";
                }
                str = Main.splitString(temp, "\n", ":");
            } catch (SQLException e) {
                System.out.println(e);
            }
            Util.closeConnection();
            if (str.length > 0) {

                for (int i = 0; i < str.length; i++) {
                    User user = new User(str[i][0], str[i][1], (byte) Integer.parseInt(str[i][2]));
                    listUsers.add(user);
                }
            }
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        String query = "";

        if (Main.getQueryList("clearTable") == null) {
            query = "TRUNCATE TABLE Users;";
        } else {
            query = Main.getQueryList("clearTable");
        }

        if (Util.openConnection()) {
            try {
                Util.sendQuery(query);
            } catch (Exception e) {
                System.out.println(e);
            }
            Util.closeConnection();
        }
    }
}
