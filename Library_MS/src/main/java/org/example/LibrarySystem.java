package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LibrarySystem {
    private Map<String, User> users = new HashMap<>();
    private List<Book> books = new ArrayList<>();

    public LibrarySystem() {
        try {
            loadUsers();
            loadBooks();
        } catch (Exception e) {
            System.err.println("Failed to load initial data: " + e.getMessage());
        }
    }

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        User user = new User(username, password);
        users.put(username, user);
        try {
            saveUsers();
        } catch (IOException e) {
            System.err.println("Error saving users after registration: " + e.getMessage());
        }
        return true;
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.verifyPassword(password)) {
            return user;
        }
        return null;
    }

    public void addBook(Book book) {
        books.add(book);
        try {
            saveBooks();
        } catch (IOException e) {
            System.err.println("Error saving books after adding a new book: " + e.getMessage());
        }
    }

    public boolean borrowBook(User user, String bookTitle) {
        for (Book book : books) {
            if (book.getTitle().equals(bookTitle) && book.isAvailable()) {
                book.setAvailable(false);
                user.borrowBook(book);
                try {
                    saveBooks();
                    saveUsers();
                } catch (IOException e) {
                    System.err.println("Error saving data after borrowing a book: " + e.getMessage());
                }
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(User user, String bookTitle) {
        for (Book book : user.getBorrowedBooks()) {
            if (book.getTitle().equals(bookTitle)) {
                book.setAvailable(true);
                user.returnBook(book);
                try {
                    saveBooks();
                    saveUsers();
                } catch (IOException e) {
                    System.err.println("Error saving data after returning a book: " + e.getMessage());
                }
                return true;
            }
        }
        return false;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    private void saveUsers() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        }
    }

    private void loadUsers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
            users = (Map<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No user data found. Starting fresh.");
        }
    }

    private void saveBooks() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("books.dat"))) {
            oos.writeObject(books);
        }
    }

    private void loadBooks() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("books.dat"))) {
            books = (List<Book>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No book data found. Starting fresh.");
        }
    }
}
