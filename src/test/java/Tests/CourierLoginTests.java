package Tests;

import Data.CourierCredentials;
import Data.CourierDto;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;

@Epic("API Scooter")
@Feature("Авторизация курьера")
public class CourierLoginTests extends BaseTest {
    private String existingLogin;
    private String existingPassword = "1234";
    private String courierId;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        existingLogin = "naruto" + System.currentTimeMillis();

        CourierDto courier = new CourierDto();
        courier.setLogin(existingLogin);
        courier.setPassword(existingPassword);
        courier.setFirstName("naruto");

        courierApi.createCourier(courier);
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierApi.deleteCourier(courierId);
        }
    }

    @Test
    @Story("Успешная авторизация")
    @Description("Проверка успешной авторизации курьера с валидными данными")
    public void testSuccessfulCourierLogin() {
        CourierCredentials credentials = new CourierCredentials();
        credentials.setLogin(existingLogin);
        credentials.setPassword(existingPassword);

        Response response = courierApi.loginCourier(credentials);

        verifyStatusCode(response, 200);
        verifyResponseBodyContainsId(response);

        courierId = response.jsonPath().getString("id");
        assertCourierIdIsNotNull(courierId);
    }

    @Test
    @Story("Неуспешная авторизация")
    @Description("Проверка авторизации без пароля")
    public void testLoginWithoutPasswordField() {
        CourierCredentials credentials = new CourierCredentials();
        credentials.setLogin(existingLogin);
        // password не устанавливаем

        Response response = courierApi.loginCourier(credentials);
        verifyStatusCode(response, 400);
        verifyErrorMessage(response, "Недостаточно данных для входа");
    }

    @Test
    @Story("Неуспешная авторизация")
    @Description("Проверка авторизации с несуществующим логином")
    public void testLoginWithInvalidLogin() {
        CourierCredentials credentials = new CourierCredentials();
        credentials.setLogin("notnaruto");
        credentials.setPassword(existingPassword);

        Response response = courierApi.loginCourier(credentials);
        verifyStatusCode(response, 404);
        verifyErrorMessage(response, "Учетная запись не найдена");
    }

    @Test
    @Story("Неуспешная авторизация")
    @Description("Проверка авторизации с некорректным паролем")
    public void testLoginWithInvalidPassword() {
        CourierCredentials credentials = new CourierCredentials();
        credentials.setLogin(existingLogin);
        credentials.setPassword("5555");

        Response response = courierApi.loginCourier(credentials);
        verifyStatusCode(response, 404);
        verifyErrorMessage(response, "Учетная запись не найдена");
    }

    @Test
    @Story("Неуспешная авторизация")
    @Description("Проверка авторизации с пустым логином")
    public void testLoginWithEmptyLogin() {
        CourierCredentials credentials = new CourierCredentials();
        credentials.setLogin("");
        credentials.setPassword(existingPassword);

        Response response = courierApi.loginCourier(credentials);
        verifyStatusCode(response, 400);
        verifyErrorMessage(response, "Недостаточно данных для входа");
    }

    @Test
    @Story("Неуспешная авторизация")
    @Description("Проверка авторизации с пустым паролем")
    public void testLoginWithEmptyPassword() {
        CourierCredentials credentials = new CourierCredentials();
        credentials.setLogin(existingLogin);
        credentials.setPassword("");

        Response response = courierApi.loginCourier(credentials);
        verifyStatusCode(response, 400);
        verifyErrorMessage(response, "Недостаточно данных для входа");
    }

    @Step("Проверить код ответа: ожидается {expectedStatusCode}")
    private void verifyStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Step("Проверить, что тело ответа содержит ID курьера")
    private void verifyResponseBodyContainsId(Response response) {
        response.then().body("id", notNullValue());
    }

    @Step("Проверить сообщение об ошибке: ожидается '{expectedMessage}'")
    private void verifyErrorMessage(Response response, String expectedMessage) {
        response.then().body("message", equalTo(expectedMessage));
    }

    @Step("Проверить, что ID курьера не null")
    private void assertCourierIdIsNotNull(String courierId) {
        assertNotNull("ID курьера не должен быть null", courierId);
    }
}
