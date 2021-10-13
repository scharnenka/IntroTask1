import lombok.Data;

import java.util.List;

@Data
public class TestModel {

    private final String testClassName;
    private final String testMethodName;
    private final List<String> methodParams;

    public TestModel(String className, String methodName, List<String> params) {
        this.testClassName = className;
        this.testMethodName = methodName;
        this.methodParams = params;
    }
}