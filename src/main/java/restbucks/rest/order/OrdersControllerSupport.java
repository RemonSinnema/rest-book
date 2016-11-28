/*
 * Copyright (c) 2016 Remon Sinnema. All rights reserved.
 */
package restbucks.rest.order;

import java.util.ArrayList;
import java.util.Collection;

import javax.money.MonetaryAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import restbucks.domain.menu.Drink;
import restbucks.domain.menu.DrinkRepository;
import restbucks.domain.order.Order;
import restbucks.domain.order.OrderRepository;
import restbucks.rest.impl.RestResponse;
import restbucks.rest.impl.UnknownItemException;
import restbucks.rest.item.ItemResource;


@Service
public class OrdersControllerSupport {

  @Autowired
  private DrinkRepository drinks;
  @Autowired
  private OrderRepository orders;

  public RestResponse<OrderResource> post(OrderResource input) {
    Order order = new Order(input.customer, toDrinks(input));
    order = orders.save(order);

    MonetaryAmount cost = order.getCost();
    OrderResource payload = new OrderResource();
    payload.customer = input.customer;
    payload.items = input.items;
    payload.total = cost.getNumber().doubleValue();
    payload.currency = cost.getCurrency().getCurrencyCode();

    RestResponse<OrderResource> result = new RestResponse<>(payload);
    result.setParameter("orderId", order.getId());
    result.setStatus(HttpStatus.CREATED);
    return result;
  }

  private Collection<Drink> toDrinks(OrderResource order) {
    Collection<Drink> result = new ArrayList<>();
    for (ItemResource item : order.items) {
      result.add(toDrink(item));
    }
    return result;
  }

  private Drink toDrink(ItemResource item) {
    Drink result = drinks.findByName(item.name);
    if (result == null) {
      throw new UnknownItemException();
    }
    return result;
  }

}
