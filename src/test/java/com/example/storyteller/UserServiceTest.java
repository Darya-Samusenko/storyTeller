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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void testCreateUserWhenEmailExists() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(new User());
        boolean result = userService.createUser(user);
        assertFalse(result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verifyNoMoreInteractions(passwordEncoder, userRepository);
    }

    @Test
    void testCreateListOfUsers() {
        List<User> userList = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.list();
        assertEquals(userList.size(), result.size());
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(passwordEncoder, userRepository);
    }

    @Test
    void testBanUserWhenUserExistsAndIsActive(){
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setActive(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.banUser(1L);

        assertFalse(user.isActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testBanUserWhenUserExistsAndIsInactive() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setActive(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.banUser(1L);

        assertTrue(user.isActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testBanUserWhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.banUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testChangeUserRoles() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        Map<String, String> form = new HashMap<>();
        form.put("ROLE_ADMIN", "on");
        form.put("ROLE_USER", "on");
        when(userRepository.save(user)).thenReturn(user);

        userService.changeUserRoles(user, form);

        Set<Role> expectedRoles = new HashSet<>(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER));
        assertEquals(expectedRoles, user.getRoles());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserByPrincipalNull() {
        User result = userService.getUserByPrincipal(null);

        assertNotNull(result);
        assertEquals("", result.getEmail());
        verifyNoInteractions(userRepository);
    }

    @Test
    void testGetUserByPrincipalNotNull() {
        Principal principal = () -> "test@example.com";
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userService.getUserByPrincipal(principal);

        assertEquals(user, result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testGetUserByMailAndPassWhenUserExistsAndPasswordMatches() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User result = userService.getUserByMailAndPass("test@example.com", "password");

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).encode("password");
    }

    @Test
    void testGetUserByMailAndPassWhenUserExistsButPasswordDoesNotMatch() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User result = userService.getUserByMailAndPass("test@example.com", "wrongPassword");

        assertNull(result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).encode("wrongPassword");
    }

    @Test
    void testGetProfileInfo() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");

        String result = userService.getProfileInfo(user);

        assertEquals("John Doe : johndoe@example.com", result);
    }
}
