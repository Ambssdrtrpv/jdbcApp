package jdbc.project.dao;

import jdbc.project.model.User;

import java.util.List;

public interface UserDAO {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
