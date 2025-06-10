package Api;

import Data.OrderDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi extends BaseApi {
    private static final String CREATE_ORDER_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public Response createOrder(OrderDto order) {
        return given(getBaseSpec())
                .body(GSON.toJson(order))
                .post(CREATE_ORDER_PATH);
    }
}
