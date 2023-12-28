package com.example.storyteller;

import com.example.storyteller.models.Part;
import com.example.storyteller.models.User;
import com.example.storyteller.models.Work;
import com.example.storyteller.repositories.PartRepository;
import com.example.storyteller.repositories.UserRepository;
import com.example.storyteller.repositories.WorkRepository;
import com.example.storyteller.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;

    @Mock
    private PartRepository partRepository;

    @Mock
    private WorkRepository workRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListProductsWhenTitleIsNull() {
        List<Work> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Work());
        expectedProducts.add(new Work());
        when(workRepository.findAll()).thenReturn(expectedProducts);
        List<Work> result = productService.listProducts(null);
        assertEquals(expectedProducts, result);
        verify(workRepository, times(1)).findAll();
    }

    @Test
    void testListProductsWhenTitleIsNotNull() {
        String title = "example";
        List<Work> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Work());
        when(workRepository.findByTitle(title)).thenReturn(expectedProducts);
        List<Work> result = productService.listProducts(title);
        assertEquals(expectedProducts, result);
        verify(workRepository, times(1)).findByTitle(title);
    }

    @Test
    void testSaveProduct() throws IOException {
        Principal principal = mock(Principal.class);
        Work product = new Work();
        User author = new User();
        author.setEmail("test@example.com");
        when(userRepository.findByEmail(principal.getName())).thenReturn(author);
        when(workRepository.save(product)).thenReturn(product);
        productService.saveProduct(principal, product);
        assertSame(author, product.getAuthor());
        verify(userRepository, times(1)).findByEmail(principal.getName());
        verify(workRepository, times(1)).save(product);
    }

    @Test
    void testGetUserByPrincipalNull() {
        User result = productService.getUserByPrincipal(null);
        assertNotNull(result);
    }

    @Test
    void getUserByPrincipal_WhenPrincipalIsNotNull_ShouldReturnUserByEmail() {
        Principal principal = mock(Principal.class);
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail(principal.getName())).thenReturn(user);
        User result = productService.getUserByPrincipal(principal);
        assertSame(user, result);
        verify(userRepository, times(1)).findByEmail(principal.getName());
    }

    @Test
    void testAddPartToWork() throws IOException {
        Principal principal = mock(Principal.class);
        Part addedPart = new Part();
        Work srcWork = new Work();
        addedPart.setSrc(srcWork);
        when(partRepository.save(addedPart)).thenReturn(addedPart);
        productService.addPartToWork(principal, addedPart);
        assertTrue(srcWork.getParts().contains(addedPart));
        verify(partRepository, times(1)).save(addedPart);
    }

    @Test
    void testDeleteProductWhenProductExistsAndUserIsAuthor() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        Work product = new Work();
        product.setId(1L);
        product.setAuthor(user);
        List<Part> parts = new ArrayList<>();
        Part part1 = new Part();
        part1.setId_part(1L);
        parts.add(part1);
        Part part2 = new Part();
        part2.setId_part(2L);
        parts.add(part2);
        product.setParts(parts);
        when(workRepository.findById(1L)).thenReturn(Optional.of(product));
        when(partRepository.findById(1L)).thenReturn(Optional.of(part1));
        when(partRepository.findById(2L)).thenReturn(Optional.of(part2));
        productService.deleteProduct(user, 1L);
        verify(workRepository, times(1)).findById(1L);
        verify(partRepository, times(1)).findById(1L);
        verify(partRepository, times(1)).findById(2L);
        verify(partRepository, times(1)).delete(part1);
        verify(partRepository, times(1)).delete(part2);
        verify(workRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProductWhenProductExistsButUserIsNotAuthor() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        Work product = new Work();
        product.setId(1L);
        User author = new User();
        author.setId(2L);
        product.setAuthor(author);
        when(workRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.deleteProduct(user, 1L);
        verify(workRepository, times(1)).findById(1L);
        verify(partRepository, never()).findById(anyLong());
        verify(partRepository, never()).delete(any());
        verify(workRepository, never()).delete(any());
    }

    @Test
    void testDeleteProductWhenProductDoesNotExist() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        when(workRepository.findById(1L)).thenReturn(Optional.empty());
        productService.deleteProduct(user, 1L);
        verify(workRepository, times(1)).findById(1L);
        verify(partRepository, never()).findById(anyLong());
        verify(partRepository, never()).delete(any());
        verify(workRepository, never()).delete(any());
    }

    @Test
    void testGetWorkByIdWhenWorkExists() {
        Work expectedWork = new Work();
        when(workRepository.findById(1L)).thenReturn(Optional.of(expectedWork));
        Work result = productService.getWorkById(1L);
        assertSame(expectedWork, result);
        verify(workRepository, times(1)).findById(1L);
    }

    @Test
    void testGetWorkByIdWhenWorkDoesNotExist() {
        when(workRepository.findById(1L)).thenReturn(Optional.empty());
        Work result = productService.getWorkById(1L);
        assertNull(result);
        verify(workRepository, times(1)).findById(1L);
    }

    @Test
    void getPartByIdWhenPartExists() {
        Part expectedPart = new Part();
        when(partRepository.findById(1L)).thenReturn(Optional.of(expectedPart));
        Part result = productService.getPartById(1L);
        assertSame(expectedPart, result);
        verify(partRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPartByIdWhenPartDoesNotExist() {
        when(partRepository.findById(1L)).thenReturn(Optional.empty());
        Part result = productService.getPartById(1L);
        assertNull(result);
        verify(partRepository, times(1)).findById(1L);
    }
}

