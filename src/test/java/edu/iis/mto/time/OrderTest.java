package edu.iis.mto.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
    }

    @Test
    public void testForItemStateAfterAddIsCREATED() {
        order.addItem(new OrderItem());

        assertThat(Order.State.CREATED, is(order.getOrderState()));
    }

}
