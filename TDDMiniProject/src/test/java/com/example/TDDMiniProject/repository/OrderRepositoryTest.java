package com.example.TDDMiniProject.repository;

import jakarta.persistence.criteria.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest // This sets up spring app context with a database to test jpa applications
public class OrderRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;


    // Part 3: Test-Driven Development: Test for save (create)
    @Test
    public void testSaveOrder() {
        // Create an Order object
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);

        // Save the Order using the repository
        Order savedOrder = orderRepository.save(order);

        // Verify that the Order is saved with a non-null ID
        Assertions.assertNotNull(savedOrder.getId());
        Assertions.assertEquals(order.getCustomerName(), savedOrder.getCustomerName());
        Assertions.assertEquals(order.getShippingAddress(), savedOrder.getShippingAddress());
        Assertions.assertEquals(order.getTotal(), savedOrder.getTotal());
    }

    @Test
    public void testSaveOrder_Fail() {
        // Create an Order object without required fields
        Order order = new Order(); // Empty Order object

        // Save the Order using the repository
        Order savedOrder = orderRepository.save(order);

        // Verify that the Order is not saved due to validation failure
        Assertions.assertNull(savedOrder.getId());
    }

    // Part 3: Test-Driven Development: Test for read (retrieve)
    @Test
    public void testGetOrder() {
        // Create an Order object and save it to the database
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = entityManager.persistAndFlush(order);

        // Retrieve the Order from the database using its ID
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrder.getId());

        // Verify that the retrieved Order matches the original Order
        Assertions.assertTrue(retrievedOrder.isPresent());
        Assertions.assertEquals(savedOrder.getId(), retrievedOrder.get().getId());
        Assertions.assertEquals(savedOrder.getCustomerName(), retrievedOrder.get().getCustomerName());
        Assertions.assertEquals(savedOrder.getShippingAddress(), retrievedOrder.get().getShippingAddress());
        Assertions.assertEquals(savedOrder.getTotal(), retrievedOrder.get().getTotal());
    }

    @Test
    public void testGetOrder_NonExistingId_Fail() {
        // Attempt to retrieve an Order with a non-existing ID
        Long nonExistingId = 999L;

        // Retrieve the Order from the database using the non-existing ID
        Optional<Order> retrievedOrder = orderRepository.findById(nonExistingId);

        // Verify that the retrieved Order is empty
        Assertions.assertFalse(retrievedOrder.isPresent());
    }

    // Part 3: Test-Driven Development: Test for update
    @Test
    public void testUpdateOrder() {
        // Create an Order object and save it to the database
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = entityManager.persistAndFlush(order);

        // Modify the Order object
        savedOrder.setCustomerName("Jane Smith");
        savedOrder.setTotal(300.0);

        // Update the Order using the repository
        Order updatedOrder = orderRepository.save(savedOrder);

        // Verify that the Order is updated with the new values
        Assertions.assertEquals(savedOrder.getCustomerName(), updatedOrder.getCustomerName());
        Assertions.assertEquals(savedOrder.getTotal(), updatedOrder.getTotal());
    }

    @Test
    public void testUpdateOrder_NonExistingId_Fail() {
        // Create an Order object with a non-existing ID
        Order order = new Order();
        order.setId(999L);

        // Attempt to update the Order using the repository
        Assertions.assertThrows(RuntimeException.class, () -> orderRepository.save(order));
    }

    // Part 3: Test-Driven Development: Test for delete
    @Test
    public void testDeleteOrder() {
        // Create an Order object and save it to the database
        Order order = new Order("John Doe", LocalDate.now(), "123 Street, City, State", 200.0);
        Order savedOrder = entityManager.persistAndFlush(order);

        // Delete the Order using the repository
        orderRepository.deleteById(savedOrder.getId());

        // Verify that the Order is no longer present in the database
        boolean orderExists = orderRepository.existsById(savedOrder.getId());
        Assertions.assertFalse(orderExists);
    }


    @Test
    public void testDeleteOrder_NonExistingId_Fail() {
        // Attempt to delete an Order with a non-existing ID
        Long nonExistingId = 999L;

        // Attempt to delete the Order using the repository
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> orderRepository.deleteById(nonExistingId));
    }

}
