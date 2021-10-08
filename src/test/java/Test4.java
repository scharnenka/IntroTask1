import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test4 {

    @Parameters({ "data4a_1" })
    @Test
    public void action4_a(String data4a_1) {
        System.out.println("Test4->action4_a->data4a_1");
    }

    @Parameters({ "data4b_1", "data4b_2"})
    @Test
    public void action4_b(String data4b_1, String data4b_2) {
        System.out.println("Test4->action4_a->data4b_1;data4b_2");
    }
}