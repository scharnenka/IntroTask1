import org.testng.TestNG;
import org.testng.xml.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestNGRunner {

    public static void main(String[] args) throws Exception {

        List<String> inputParameters = Arrays.asList("Test3|action3_b|data3b_1; data3b_2", "Test1|action1_a|data1a_1;");

        XmlSuite suite = new XmlSuite();
        suite.setName("TmpSuite");
        for (int i=0; i<inputParameters.size(); i++) {
            addXmlTestToSuite(suite, "Test"+i, inputParameters.get(i));
        }
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();
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

    private static String getStringByRegex(String inputString, String regexp) throws Exception {
        return getStringsMatchedByRegex(inputString, regexp).stream()
                .findFirst()
                .orElseThrow(() -> new Exception("String matched with provided regexp wasn't found"));
    }

    private static XmlTest addXmlTestToSuite(XmlSuite suite, String xmlTestName, String params) throws Exception {
        XmlTest test = new XmlTest(suite);
        test.setName(xmlTestName);
        String testClassName = getStringByRegex(params, "Test\\d");
        String testMethodName = getStringByRegex(params, "action\\d_[abc]");
        List<String> methodParams = getStringsMatchedByRegex(params,"data\\d[abc]_\\d");
        List<XmlClass> classes = new ArrayList<XmlClass>();
        XmlClass testClass = new XmlClass(testClassName);
        List<XmlInclude> includeMethods = new ArrayList<>();
        includeMethods.add(new XmlInclude(testMethodName));
        testClass.setIncludedMethods(includeMethods);
        Map<String, String> testClassParameters = new HashMap<>();
        methodParams.forEach(methodParam -> testClassParameters.put(methodParam, methodParam));
        testClass.setParameters(testClassParameters);
        classes.add(testClass);
        test.setXmlClasses(classes);
        return test;
    }
}