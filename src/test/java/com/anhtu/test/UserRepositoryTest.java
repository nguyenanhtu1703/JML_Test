package com.anhtu.test;

import com.anhtu.entity.User;
import com.anhtu.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findAll_SizeCompare_Equals2Exepected() {
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    public void contain_ContainLoginName_TrueExpected() {
        assertTrue(userRepository.findAll().stream().anyMatch(x -> x.getLogin().equals("jsmith")));
    }

    @Test
    public void contain_ContainUser_TrueExpected() {
        User testUser = new User("jsmith", "John2", "Smith", "qwerty");

        assertTrue(userRepository.findAll().stream().anyMatch(x -> x.getLogin().equals(testUser.getLogin()) && x.getPassword().equals(testUser.getPassword())));
    }

    @Test
    public void contain_NotContainLoginName_FalseExpected() {
        assertFalse(userRepository.findAll().stream().anyMatch(x -> x.getLogin().equals("jsmith2")));
    }
}

