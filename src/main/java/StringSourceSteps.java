import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class StringSourceSteps implements TestSourceSteps {

    @Override
    public List<TestModel> getTestsForExecution(String joinedTestParams) {
        List<TestModel> testsForExecution = new ArrayList<TestModel>();
        List<String> inputParameters = Arrays.asList(joinedTestParams.split(","));
        for (String inputParameter : inputParameters) {
            String testClassName = getStringByRegex(inputParameter, "Test\\d");
            String testMethodName = getStringByRegex(inputParameter, "action\\d_[abc]");
            List<String> methodParams = getStringsMatchedByRegex(inputParameter, "data\\d[abc]_\\d");
            TestModel testModel = new TestModel(testClassName, testMethodName, methodParams);
            testsForExecution.add(testModel);
        }
        return testsForExecution;
    }

    private static List<String> getStringsMatchedByRegex(String inputString, String regexp) {
        List<String> allMatches = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        return allMatches;
    }

    private static String getStringByRegex(String inputString, String regexp) {
        String matchedString = "";
        try {
            matchedString = getStringsMatchedByRegex(inputString, regexp).stream()
                    .findFirst()
                    .orElseThrow(() -> new Exception("String matched with provided regexp wasn't found"));
        } catch (Exception e) {
            e.printStackTrace();
            log.info(String.format("String matched with provided regexp %s wasn't found", regexp));
            throw new RuntimeException(e);
        }
        return matchedString;
    }
}