package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibrarySystemTest {
    private LibrarySystem librarySystem;
    private User testUser;

    @BeforeEach
    public void setup() {
        librarySystem = new LibrarySystem();
        testUser = new User("testuser", "password123");
        librarySystem.registerUser("testuser", "password123");
        librarySystem.addBook(new Book("1984", "George Orwell"));
    }

    @Test
    public void testUserRegistration() {
        assertTrue(librarySystem.registerUser("newuser", "newpass"));
        assertFalse(librarySystem.registerUser("testuser", "password123"));
    }

    @Test
    public void testLogin() {
        assertNotNull(librarySystem.loginUser("testuser", "password123"));
        assertNull(librarySystem.loginUser("testuser", "wrongpassword"));
    }

    @Test
    public void testBorrowBook() {
        assertTrue(librarySystem.borrowBook(testUser, "1984"));
        assertFalse(librarySystem.borrowBook(testUser, "1984")); // Book should now be unavailable
    }

    @Test
    public void testReturnBook() {
        librarySystem.borrowBook(testUser, "1984");
        assertTrue(librarySystem.returnBook(testUser, "1984"));
        assertFalse(librarySystem.returnBook(testUser, "1984")); // Book already returned
    }
}
