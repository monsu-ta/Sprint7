package Tests;

import Data.CourierCredentials;
import Data.CourierDto;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;

@Epic("API Scooter")
@Feature("Создание курьера")
public class CourierApiTests extends BaseTest {

    @Test
    @Story("Успешное создание курьера")
    public void testCreateCourier() {
        Response response = courierApi.createCourier(
                new Data.CourierDto(testLogin, testPassword, "naruto"));

        response.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = courierApi.loginCourier(
                        new CourierCredentials(testLogin, testPassword))
                .then()
                .extract()
                .path("id")
                .toString();

        assertNotNull(courierId);
    }

    @Test
    @Story("Неуспешное создание курьера")
    public void testCreateTwoCourier() {
        courierApi.createCourier(new CourierDto(testLogin, testPassword, "naruto"));
        Response response = courierApi.createCourier(
                new CourierDto(testLogin, testPassword, "naruto"));

        response.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется."));
    }

    @Test
    @Story("Неуспешное создание курьера")
    public void testNotCreateCourierWithoutLogin() {
        Response response = courierApi.createCourier(
                new CourierDto(null, testPassword, "naruto"));

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Story("Неуспешное создание курьера")
    public void testNotCreateCourierWithoutPassword() {
        Response response = courierApi.createCourier(
                new CourierDto(testLogin, null, "naruto"));

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}