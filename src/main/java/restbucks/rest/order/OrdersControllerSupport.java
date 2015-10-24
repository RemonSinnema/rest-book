/*
 * Generated by RADL.
 */
package restbucks.rest.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restbucks.domain.menu.Drink;
import restbucks.domain.menu.DrinkRepository;
import restbucks.rest.impl.InvalidItemException;
import restbucks.rest.impl.MissingCustomerException;
import restbucks.rest.impl.MissingItemException;
import restbucks.rest.impl.PermittedActions;
import restbucks.rest.impl.UnknownItemException;
import restbucks.rest.item.ItemResource;


@Service
public class OrdersControllerSupport {

  @Autowired
  private DrinkRepository repository;
  
  public PermittedActions<OrderResource> post(OrderResource input) {
    if (StringUtils.isBlank(input.customer)) {
      throw new MissingCustomerException();
    }
    ItemResource[] items = input.items;
    if (items == null || items.length == 0) {
      throw new MissingItemException();
    }
    Collection<Drink> drinks = new ArrayList<>();
    Currency currency = null;
    double total = 0;
    for (ItemResource item : items) {
      Drink drink = repository.findByName(item.name);
      if (drink == null) {
        throw new UnknownItemException();
      }
      if (currency == null) {
        currency = drink.getCurrency();
      } else if (!drink.getCurrency().equals(currency)) {
        throw new InvalidItemException();
      }
      total += drink.getPrice();
      drinks.add(drink);
    }
    
    OrderResource result = new OrderResource();
    result.currency = currency.getCurrencyCode();
    result.total = total;
    result.customer = input.customer;
    result.items = items;
    
    return new PermittedActions<OrderResource>(result);
  }

}
