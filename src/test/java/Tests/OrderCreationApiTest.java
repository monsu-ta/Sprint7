package Tests;

import Data.OrderDto;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

@Epic("API Scooter")
@Feature("Создание заказа")
public class OrderCreationApiTest extends BaseTest {

    @Test
    @Story("Успешное создание заказа")
    public void testOrderCreation() {
        OrderDto order = new OrderDto();
        order.setFirstName("Naruto");
        order.setLastName("Uzumaki");
        order.setAddress("Konoha, 142 apt.");
        order.setMetroStation("4");
        order.setPhone("+7 800 355 35 35");
        order.setRentTime(5);
        order.setDeliveryDate("2020-06-06");
        order.setComment("Saske, come back to Konoha");

        Response response = orderApi.createOrder(order);

        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
