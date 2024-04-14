package jdbc.project.util;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jdbc.project.model.User;
import jdbc.project.service.UserService;
import jdbc.project.service.UserServiceImpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/createTable", new CreateHandler());
        server.createContext("/allUsers", new GetAllUsersHandler());
        server.createContext("/saveUser", new AddUserHandler());
        server.createContext("/deleteUser", new DeleteUserHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello! It's my server." +
                    "\n Available urls for working with the database: " +
                    "\n Creating new table : /createTable" +
                    "\n Get all information from table : /allUsers" +
                    "\n Add new user: /saveUser (Вы должны отправить POST запрос со строкой, где через пробел указаны Имя Фамилия Возвраст" +
                    "\n Delete user: /deleteUser (Вы должны отправить POST запрос со строкой, где указан Id для удаления";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class CreateHandler implements HttpHandler {
        UserService userService = new UserServiceImpl();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            userService.createUsersTable();
            String response = "Table was created.";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    static class GetAllUsersHandler implements HttpHandler {
        UserService userService = new UserServiceImpl();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            List<User> users = userService.getAllUsers();
            StringBuilder response = new StringBuilder();
            for (User user : users) {
                response.append("User ID: ").append(user.getId());
                response.append(" Name: ").append(user.getName());
                response.append(" Last name: ").append(user.getLastName());
                response.append(" Age: ").append(user.getAge());
                response.append("\n");
            }
            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }

    static class AddUserHandler implements HttpHandler {
        UserService userService = new UserServiceImpl();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream requestBody = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
            String requestBodyString = reader.lines().collect(Collectors.joining("\n"));

            String[] words = requestBodyString.split("\\s+");
            userService.saveUser(words[0], words[1], (byte) Integer.parseInt(words[2]));

            String response = "Данные успешно получены и сохранены в базе данных";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class DeleteUserHandler implements HttpHandler {
        UserService userService = new UserServiceImpl();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream requestBody = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
            String requestBodyString = reader.lines().collect(Collectors.joining("\n"));

            userService.removeUserById(Long.parseLong(requestBodyString));
            String response = "Пользователь с id:" + requestBodyString + " был удален";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
