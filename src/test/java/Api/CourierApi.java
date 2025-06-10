package Api;

import Data.CourierCredentials;
import Data.CourierDto;
import Data.DeleteCourierRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi extends BaseApi {
    private static final String CREATE_COURIER = "/api/v1/courier";
    public static final String LOGIN_COURIER = "/api/v1/courier/login";
    private static final String DELETE_COURIER = "/api/v1/courier/";

    @Step("Создание курьера")
    public Response createCourier(CourierDto courier) {
        return given(getBaseSpec())
                .body(GSON.toJson(courier))
                .post(CREATE_COURIER);
    }

    @Step("Авторизация курьера")
    public Response loginCourier(CourierCredentials credentials) {
        return given(getBaseSpec())
                .body(GSON.toJson(credentials))
                .post(LOGIN_COURIER);
    }

    @Step("Удаление курьера")
    public void deleteCourier(String id) {
        given(getBaseSpec())
                .body(GSON.toJson(new DeleteCourierRequest(id)))
                .delete(DELETE_COURIER + id);
    }
}
