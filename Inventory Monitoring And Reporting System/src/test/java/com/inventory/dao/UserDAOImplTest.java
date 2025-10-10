package com.inventory.dao;

import com.inventory.model.Product;
import com.inventory.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class UserDAOImplTest {
    public static UserDAOImpl dao;
    @BeforeAll
    static void setUp() throws Exception{
        dao=new UserDAOImpl();
    }
    @Test
    public void testAddUser() throws Exception {
        User u = new User( "test", "test", "admin");
        dao.addUser(u);

        User users = dao.getUserByUsername("test");
        assertNotNull(users);
        assertEquals("test", users.getUsername());
    }

    @Test
    public void testGetUserByName() throws Exception {
        User u = new User( "test", "test", "admin");
        dao.addUser(u);

        User users = dao.getUserByUsername("test");
        assertNotNull(users);
        assertEquals("test", users.getUsername());
    }

}