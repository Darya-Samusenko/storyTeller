package com.example.storyteller;

import com.example.storyteller.models.User;
import com.example.storyteller.models.enums.Role;
import com.example.storyteller.repositories.UserRepository;
import com.example.storyteller.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void testGetUserByPrincipalAfterCreation(){
        // Создаем тестового пользователя
        User user = new User();
        user.setName("test_user_name");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.getRoles().add(Role.ROLE_USER);

        // Устанавливаем ожидаемое значение при вызове метода findByEmail
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // Создаем макет объекта Principal
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        // Вызываем метод createUser
        userService.createUser(user);

        // Вызываем метод getUserByPrincipal
        User retrievedUser = userService.getUserByPrincipal(principal);

        // Проверяем, что полученный пользователь соответствует ожидаемому
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getPassword(), retrievedUser.getPassword());
        assertEquals(user.getRoles(), retrievedUser.getRoles());
        assertEquals(user.getName(), retrievedUser.getName());

        // Проверяем, что метод findByEmail был вызван один раз с ожидаемым аргументом
        verify(userRepository, times(2)).findByEmail("test@example.com");
    }
}
