import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Test3 {

    @Parameters({ "data3a_1" })
    @Test
    public void action3_a(String data3a_1) {
        System.out.println("Test3->action3_a->data3a_1");
    }

    @Parameters({ "data3b_1", "data3b_2"})
    @Test
    public void action3_b(String data3b_1, String data3b_2) {
        System.out.println("Test3->action3_a->data3b_1;data3b_2");
    }
}