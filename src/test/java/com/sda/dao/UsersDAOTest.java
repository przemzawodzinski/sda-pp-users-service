package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UsersDAOTest {

    private final UsersDAO usersDAO = new UsersDAO();

    @Test
    void testCreateHappyPath() {
        // given
        String expectedUsername = UUID.randomUUID().toString();

        User expectedUser = User.builder()
                .username(expectedUsername)
                .password("password")
                .name("name")
                .surname("surname")
                .email("example@email.com")
                .age(30).build();

        // when
        usersDAO.create(expectedUser);

        // then
        User actualUser;

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            actualUser = session.get(User.class, expectedUsername);
        }

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());

    }

    @Test
    void testDeleteByUsernameUserNotExist() {
        // given
        String nonExistingUsername = "NON EXISTING USER";

        // when
        boolean deleted = usersDAO.deleteByUsername(nonExistingUsername);

        // then
        Assertions.assertFalse(deleted);
    }

    @Test
    void testDeleteByUsernameUserExist() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = User.builder()
                .username(username)
                .password("password")
                .name("name")
                .surname("surname")
                .email("example@email.com")
                .age(30).build();

        usersDAO.create(expectedUser);

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            User user = session.get(User.class, username);
            Assertions.assertNotNull(user);
        }

        // when
        boolean deleted = usersDAO.deleteByUsername(username);

        // then
        Assertions.assertTrue(deleted);

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            User user = session.get(User.class, username);
            Assertions.assertNull(user);
        }
    }

}
