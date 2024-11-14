package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManagementApp {
    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        Scanner scanner = new Scanner(System.in);

        //  sample books to the library system
        librarySystem.addBook(new Book("1984", "George Orwell"));
        librarySystem.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));
        librarySystem.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        librarySystem.addBook(new Book("Moby Dick", "Herman Melville"));
        librarySystem.addBook(new Book("Pride and Prejudice", "Jane Austen"));

        System.out.println("Welcome to the Library Management System!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");

        int choice = -1;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.err.println("Invalid input. Please enter a number.");
            scanner.nextLine();
            return;
        }

        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            boolean registered = librarySystem.registerUser(username, password);
            System.out.println(registered ? "User registered successfully!" : "Username already exists.");
        } else if (choice == 2) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            User user = librarySystem.loginUser(username, password);
            if (user != null) {
                System.out.println("Login successful!");
                System.out.println("1. Borrow a Book");
                System.out.println("2. Return a Book");
                System.out.print("Choose an option: ");

                int action = -1;
                try {
                    action = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                    return;
                }

                if (action == 1) {
                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.nextLine();
                    boolean borrowed = librarySystem.borrowBook(user, bookTitle);
                    System.out.println(borrowed ? "Book borrowed successfully!" : "Book is not available.");
                } else if (action == 2) {
                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.nextLine();
                    boolean returned = librarySystem.returnBook(user, bookTitle);
                    System.out.println(returned ? "Book returned successfully!" : "You haven't borrowed this book.");
                }
            } else {
                System.out.println("Login failed. Please check your credentials.");
            }
        } else {
            System.out.println("Invalid choice. Please restart the application and choose 1 or 2.");
        }

        scanner.close();
    }
}
