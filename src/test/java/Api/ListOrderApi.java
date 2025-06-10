package Api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ListOrderApi extends BaseApi {
    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Получить список заказов")
    public Response getOrdersList(Integer courierId) {
        return given(getBaseSpec())
                .when()
                .get(ORDERS_PATH);
    }
}
