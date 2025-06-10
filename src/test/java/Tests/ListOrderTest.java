package Tests;

import Api.ListOrderApi;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@Epic("API Scooter")
@Feature("Получение списка заказов")
public class ListOrderTest extends BaseTest {
    private ListOrderApi listOrderApi;

    @Before
    public void setUp() {
        super.setUp();
        listOrderApi = new ListOrderApi();
    }

    @Test
    @Story("Успешное получение списка заказов")
    @Description("Проверка, что ответ не пустой")
    public void testGetOrdersList() {
        Response response = listOrderApi.getOrdersList(null);

        assertNotNull("Ответ не должен быть null", response);
        assertNotNull("Тело ответа не должно быть null", response.getBody());
        assertFalse("Тело ответа не должно быть пустым", response.getBody().asString().isEmpty());

        response.then().statusCode(200);
    }

}