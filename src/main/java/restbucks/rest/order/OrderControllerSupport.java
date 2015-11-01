/*
 * Generated by RADL.
 */
package restbucks.rest.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import restbucks.rest.impl.RestResponse;
import restbucks.rest.serving.ServingResource;


@Service
public class OrderControllerSupport {

  public ResponseEntity<Void> delete(String orderId) {
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }

  public RestResponse<ServingResource> get(String orderId) {
    ServingResource result = new ServingResource();
    // result.setXxx();
    RestResponse<ServingResource> RestResponse = new RestResponse<ServingResource>(result);
    // RestResponse.exclude(Actions.YYY);
    return RestResponse;
  }

  public RestResponse<OrderResource> put(String orderId, OrderResource input) {
    OrderResource result = new OrderResource();
    // result.setXxx();
    RestResponse<OrderResource> RestResponse = new RestResponse<OrderResource>(result);
    // RestResponse.exclude(Actions.YYY);
    return RestResponse;
  }

}
