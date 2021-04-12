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

    @Test
    public void testForItemStateAfterAddAndSubmitIsSUBMITTED() {
        order.addItem(new OrderItem());
        order.submit();

        assertThat(Order.State.SUBMITTED, is(order.getOrderState()));
    }

    @Test
    public void testForConfirmStateAfterOneMinuteIsConfirmed() {
        order.addItem(new OrderItem());
        order.submit();
        order.confirm(order.getSubbmitionDate().plusMinutes(1));

        assertThat(Order.State.CONFIRMED, is(order.getOrderState()));
    }

    @Test
    public void testForConfirmStateAfterTwoDaysIsCANCELLED() {
        order.addItem(new OrderItem());
        order.submit();

        try {
            order.confirm(order.getSubbmitionDate().plusDays(2));
        } catch (OrderExpiredException e) {
            e.printStackTrace();
        }

        assertThat(Order.State.CANCELLED, is(order.getOrderState()));
    }

    @Test
    public void testForStateAfterRealizeIsREALIZED() {
        order.addItem(new OrderItem());
        order.submit();
        order.confirm(order.getSubbmitionDate().plusHours(1));
        order.realize();

        assertThat(Order.State.REALIZED, is(order.getOrderState()));
    }

    @Test
    public void testForThrowExceptionWithWrongOrder() {
        try {
            order.realize();
        } catch (OrderStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testForThrowExceptionWithWrongDate() {
        try {
            order.addItem(new OrderItem());
            order.submit();
            order.confirm(order.getSubbmitionDate().plusHours(27));
            order.realize();
        } catch (OrderExpiredException e) {
            e.printStackTrace();
        }

    }
}
