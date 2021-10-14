import com.google.inject.Inject;
import driverManger.DriverModule;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Guice;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Guice(modules = {
        DriverModule.class
})
public class Test2 {

    @Inject
    WebDriver driver;

    @Parameters({"data2a_1"})
    @Test
    public void action2_a(String data2a_1) {
        driver.get("https://yandex.by/");
        System.out.println("Test2->action2_a->data2a_1");
    }

    @Parameters({"data2b_1", "data2b_2"})
    @Test
    public void action2_b(String data2b_1, String data2b_2) {
        System.out.println("Test2->action2_a->data2b_1;data2b_2");
    }
}