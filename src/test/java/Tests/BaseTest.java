package Tests;

import Api.CourierApi;
import Api.OrderApi;
import org.junit.After;
import org.junit.Before;

public class BaseTest {
    protected CourierApi courierApi;
    protected OrderApi orderApi;
    protected String testLogin;
    protected String testPassword;
    protected String courierId;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
        orderApi = new OrderApi();
        testLogin = "naruto" + System.currentTimeMillis();
        testPassword = "1234";
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierApi.deleteCourier(courierId);
        }
    }
}
