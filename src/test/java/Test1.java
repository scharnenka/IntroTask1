import com.google.inject.Inject;
import driverManger.DriverModule;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Guice;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Guice(modules = {
        DriverModule.class
})
public class Test1 {

    @Inject
    WebDriver driver;

    @Parameters({"data1a_1"})
    @Test
    public void action1_a(String data1a_1) {
        driver.get("https://www.google.com/");
        System.out.println("Test1->action1_a->data1a_1");
    }

    @Parameters({"data1b_1", "data1b_2"})
    @Test
    public void action1_b(String data1b_1, String data1b_2) {
        System.out.println("Test1->action1_a->data1b_1;data1b_2");
    }
}