package jdbc.project;

import jdbc.project.service.UserServiceImpl;
import jdbc.project.util.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
        UserServiceImpl service = new UserServiceImpl();
       /* service.createUsersTable();
        service.saveUser("Ivan", "Ivanov", (byte) 32);
        service.saveUser("Nikita", "Zakurdaev", (byte) 24);
        service.saveUser("Vladimir", "Kuznetcov", (byte) 41);
        service.saveUser("Eldar", "Pravdin", (byte) 18);
         service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();*/
    }
}
