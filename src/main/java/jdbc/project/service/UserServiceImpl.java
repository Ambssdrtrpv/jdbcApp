package jdbc.project.service;

import jdbc.project.dao.UserDAO;
import jdbc.project.dao.UserDaoJDBCImpl;
import jdbc.project.model.User;

import java.util.List;

public class UserServiceImpl extends UserDaoJDBCImpl implements UserService {
    UserDAO user = new UserDaoJDBCImpl();
    public void createUsersTable() {
        user.createUsersTable();
    }

    public void dropUsersTable() {
        user.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        user.saveUser(name, lastName, age);

    }

    public void removeUserById(long id) {
        user.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return user.getAllUsers();
    }

    public void cleanUsersTable() {
        user.cleanUsersTable();
    }
}
