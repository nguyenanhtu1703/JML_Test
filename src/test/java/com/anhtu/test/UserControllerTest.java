package com.anhtu.test;

import com.anhtu.entity.User;
import com.anhtu.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository mockUserRepository;

    static User user1 = new User("jsmith", "John", "Smith", "qwerty");

    @Before
    public void init() {
        List<User> returnList = new ArrayList();

        returnList.add(user1);

        when(mockUserRepository.findAll()).thenReturn(returnList);

        when(mockUserRepository.save(user1)).thenReturn(user1);

        when(mockUserRepository.findById("jsmith")).thenReturn(java.util.Optional.ofNullable(user1));

        returnList.remove(user1);
        when(mockUserRepository.deleteByLogin("jsmith")).thenReturn(returnList);
    }

    @Test
    public void findAll_CompareSize_EqualExpected() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("John")));

        verify(mockUserRepository, times(1)).findAll();
    }

    @Test
    public void post_AddMoreBookAndCheckExist_ExistExpected() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user1))).andExpect(status().is(201)).andExpect(jsonPath("$.login", is("jsmith")));

    }

    @Test
    public void patch_UpdateBookAndCheckReturnTheBook_EqualsExpected() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(patch("/users/1/jsmith/").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user1))).andExpect(status().isOk()).andExpect(jsonPath("$.login", is("jsmith")));

    }

    @Test
    public void delete_DeleteABookAndCompare_EmptyListExpected() throws Exception {
        mockMvc.perform(delete("/users/1/jsmith/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}

