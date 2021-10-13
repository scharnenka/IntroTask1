import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestNGRunner {

    public static void main(String[] args) throws Exception {

        TestSource source = SourceFactory.getSource(args);
        List<TestModel> testsForExecution = source.getSteps();

        XmlSuite suite = new XmlSuite();
        suite.setName("TmpSuite");
        testsForExecution.forEach(testModel -> {
            try {
                addXmlTestToSuite(suite, testModel);
            } catch (Exception e) {
                log.error(cannot parse)
                throw new RuntimeException(e);
            }
        });
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();
    }

    private static XmlTest addXmlTestToSuite(XmlSuite suite, TestModel testToAdd)  {
        XmlTest test = new XmlTest(suite);
        test.setName(testToAdd.testClassName);
        List<XmlClass> classes = new ArrayList<XmlClass>();
        XmlClass testClass = new XmlClass(testToAdd.testClassName);
        List<XmlInclude> includeMethods = new ArrayList<>();
        includeMethods.add(new XmlInclude(testToAdd.testMethodName));
        testClass.setIncludedMethods(includeMethods);
        Map<String, String> testClassParameters = new HashMap<>();
        testToAdd.methodParams.forEach(methodParam -> testClassParameters.put(methodParam, methodParam));
        testClass.setParameters(testClassParameters);
        classes.add(testClass);
        test.setXmlClasses(classes);
        return test;
    }
}
