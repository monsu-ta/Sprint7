package Tests;

import Api.OrderApi;
import Data.OrderDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationColorSelectionTest {
    private OrderApi orderApi = new OrderApi();

    @Parameterized.Parameter
    public String testName;

    @Parameterized.Parameter(1)
    public String[] colors;

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Можно указать цвет BLACK", new String[]{"BLACK"}},
                {"Можно указать цвет GREY", new String[]{"GREY"}},
                {"Можно указать оба цвета", new String[]{"BLACK", "GREY"}},
                {"Можно не указывать цвет", null}
        });
    }

    @Test
    public void testCreateOrderWithDifferentColors() {
        OrderDto order = new OrderDto();
        order.setFirstName("Naruto");
        order.setLastName("Uzumaki");
        order.setAddress("Konoha, 142 apt.");
        order.setMetroStation("4");
        order.setPhone("+7 800 355 35 35");
        order.setRentTime(5);
        order.setDeliveryDate("2020-06-06");
        order.setComment("Saske, come back to Konoha");
        order.setColor(colors);

        Response response = orderApi.createOrder(order);
        verifySuccessfulOrderCreation(response);
    }

    @Step("Проверка успешного создания заказа")
    private void verifySuccessfulOrderCreation(Response response) {
        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}