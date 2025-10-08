package com.inventory.dao;

import com.inventory.model.Product;
import com.inventory.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserDAOImplTest {
    public static UserDAOImpl dao;
    @BeforeAll
    static void setUp() throws Exception{
        dao=new UserDAOImpl();
    }
    @Test
    public void testAddUser() throws Exception {
        User u = new User(1, "test", "test", "admin");
        dao.addUser(u);

        List<User> users = dao.getUserByUsername("test");
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("test", users.get(0).getUsername());
    }

    @Test
    public void testGetUserByName() throws Exception {
        User u = new User(1, "test", "test", "admin");
        dao.addUser(u);

        List<User> users = dao.getUserByUsername("test");
        assertNotNull(users);
        assertEquals(1, users.get(0).getId());
        assertEquals("test", users.get(0).getUsername());
    }
}