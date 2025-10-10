package com.inventory.dao;
import com.inventory.model.Product;
import com.inventory.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserDAOImplMockitoTest {
    public static UserDao userDao;

    @Before
    public void setUp() throws Exception{
        userDao= Mockito.mock(UserDao.class);
    }
    @Test
    public void testAddUserSuccess() throws Exception{
        User u=new User("ram","123","admin");
        doNothing().when(userDao).addUser(u);
        userDao.addUser(u);
        verify(userDao,times(1)).addUser(u);

    }
    @Test
    public void testGetUserByUsername() throws Exception {
        UserDao userDao = mock(UserDao.class);
        User user = new User("ram", "123", "admin");
        when(userDao.getUserByUsername("ram")).thenReturn(user);
        User result = userDao.getUserByUsername("ram");
        assertNotNull(result);
        assertEquals("ram", result.getUsername());
        assertEquals("123", result.getPassword());
        assertEquals("admin", result.getRole());
        verify(userDao, times(1)).getUserByUsername("ram");
    }
}
