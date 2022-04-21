package peaksoft;

import peaksoft.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Spider","Man", (byte) 20);
        userService.saveUser("Super","Man", (byte) 30);
        userService.saveUser("Ip","Man", (byte) 40);
        userService.saveUser("Aqua","Man", (byte) 25);
        System.out.println(userService.getAllUsers());
        System.out.println(userService.existsByFirstName("Terminator"));
        System.out.println(userService.existsByFirstName("Spider"));
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
