/*
 * Generated by RADL.
 */
package restbucks.rest.order;

import java.util.Currency;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restbucks.domain.menu.Drink;
import restbucks.domain.menu.DrinkRepository;
import restbucks.rest.item.ItemResource;


@Service
public class OrdersControllerHelper {

  @Autowired
  private DrinkRepository repository;

  public OrderResource post(OrderResource input) {
    if (input.customer == null || input.customer.trim().isEmpty()) {
      throw new IllegalArgumentException("Missing customer");
    }
    if (input.item == null || input.item.length == 0) {
      throw new IllegalArgumentException("Missing items");
    }
    OrderResource result = new OrderResource();
    result.customer = input.customer;
    result.item = input.item;
    result.total = 0;
    Currency currency = null;
    for (ItemResource item : input.item) {
      Drink drink = repository.findByName(item.name);
      if (drink == null) {
        throw new IllegalArgumentException("Unknown drink: " + item.name);
      }
      result.total += drink.getPrice();
      if (currency == null) {
        currency = drink.getCurrency();
      } else if (!currency.equals(drink.getCurrency())) {
        throw new IllegalArgumentException("Drinks with different currencies");
      }
    }
    result.currency = currency.getDisplayName(Locale.getDefault());
    return result;
  }

  public boolean isLinkEnabled(String linkRelation) {
    return true;// TODO: Implement
  }

}
