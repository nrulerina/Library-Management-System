import java.util.Scanner;

public class Main {
    static Scanner s;
    static Database database;

    public static void main(String[] args) throws Exception {
        database = new Database();
        System.out.println("Welcome to Library Management System!!");
        int num;
        s = new Scanner(System.in);
        
        do {
          
            System.out.println("1. Login\n2. NewUser\n0. Exit");
            num = s.nextInt();

            switch (num) {
                case 1:
                    login();
                    break;
                case 2:
                    newUser();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        } while (num != 0);
        
        s.close();
    }

    private static void login() {
        System.out.println("Enter phone number");
        String phoneNumber = s.next();
        System.out.println("Enter email");
        String email = s.next();
        int n = database.login(phoneNumber, email);
        if (n != -1) {
            User user = database.getUser(n);
            user.menu();
        } else {
            System.out.println("Invalid Credentials");
        }
    }

    private static void newUser() {
        System.out.println("Enter name");
        String name = s.next();
        System.out.println("Enter phone number");
        String phoneNumber = s.next();
        System.out.println("Enter email");
        String email = s.next();
        System.out.println("1. Admin\n2. Normal User");
        int n2 = s.nextInt();
        User user;

        if (n2 == 1) {
            user= new Admin(name, email, phoneNumber);
            
        } else {
            user = new NormalUser(name, email, phoneNumber);
            
        }
        database.AddUser(user);
        user.menu();
    }
}
