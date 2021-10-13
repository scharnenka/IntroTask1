import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test1 {

    @Parameters({"data1a_1"})
    @Test
    public void action1_a(String data1a_1) {
        System.out.println("Test1->action1_a->data1a_1");
    }

    @Parameters({"data1b_1", "data1b_2"})
    @Test
    public void action1_b(String data1b_1, String data1b_2) {
        System.out.println("Test1->action1_a->data1b_1;data1b_2");
    }
}