import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestModel {

    String testClassName, testMethodName;
    List<String> methodParams;

    public TestModel(String className, String methodName, List<String> params) {
        testClassName = className;
        testMethodName = methodName;
        methodParams = params;
    }

    public TestModel(String className, String methodName, String params) {
        testClassName = className;
        testMethodName = methodName;
        methodParams = getListOfParamsFromString(params);
    }

    private static List<String> getListOfParamsFromString(String inputString) {
        List<String> allMatches = new ArrayList<String>();
        Pattern pattern = Pattern.compile("data\\d[abc]_\\d");
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        return allMatches;
    }
}