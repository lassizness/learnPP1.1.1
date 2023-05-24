package jm.task.core.jdbc.service;

import jm.task.core.jdbc.Main;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoJDBCImpl();//
    UserDao userDaoHib = new UserDaoHibernateImpl();

    Main checker = new Main();

    public void createUsersTable() {
        if (checker.getSwitcher() == false) {
            userDao.createUsersTable();
        } else {
            userDaoHib.createUsersTable();
        }
    }

    public void dropUsersTable() {
        if (checker.getSwitcher() == false) {
            userDao.dropUsersTable();
        } else {
            userDaoHib.dropUsersTable();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        if (checker.getSwitcher() == false) {
            userDao.saveUser(name, lastName, age);
        } else {
            userDaoHib.saveUser(name, lastName, age);
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        if (checker.getSwitcher() == false) {
            userDao.removeUserById(id);
        } else {
            userDaoHib.removeUserById(id);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        if (checker.getSwitcher() == false) {
            users = userDao.getAllUsers();
        } else {
            users = userDaoHib.getAllUsers();
        }
        return users;
    }

    public void cleanUsersTable() {
        if (checker.getSwitcher() == false) {
            userDao.cleanUsersTable();
        } else {
            userDaoHib.cleanUsersTable();
        }
    }
}
