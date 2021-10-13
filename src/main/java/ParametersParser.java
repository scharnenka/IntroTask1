import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParametersParser {

    public static List<TestModel> getListOfTests(String[] args) throws Exception {
        String joinedTestParams = String.join("", args);
        List<TestModel> testsForExecution = new ArrayList<TestModel>();
        if (joinedTestParams.contains(".xls")) {
            return testsForExecution = getTestFromExcel(joinedTestParams);
        } else {
            return testsForExecution = getTestFromStringParams(joinedTestParams);
        }
    }

    public static List<TestModel> getTestFromExcel(String excelFilePAth) throws IOException {
        FileInputStream inputStream = new FileInputStream(excelFilePAth);
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet excelSheet = workbook.getSheet("Regression");
        Iterator<Row> rowIterator = excelSheet.iterator();
        HSSFRow row = (HSSFRow) rowIterator.next();
        List<TestModel> listOfTestForExecution = new ArrayList<>();

        while (rowIterator.hasNext()) {
            row = (HSSFRow) rowIterator.next();
            String testClassName = row.getCell(0).getStringCellValue();
            String testMethodName = row.getCell(1).getStringCellValue();
            String methodParams = row.getCell(2).getStringCellValue();
            TestModel testModel = new TestModel(testClassName, testMethodName, methodParams);
            listOfTestForExecution.add(testModel);
        }
        return listOfTestForExecution;
    }

    public static List<TestModel> getTestFromStringParams(String joinedTestParams) throws Exception {
        List<TestModel> testsForExecution = new ArrayList<TestModel>();
        List<String> inputParameters = Arrays.asList(joinedTestParams.split(","));
        for (int i = 0; i < inputParameters.size(); i++) {
            String testClassName = getStringByRegex(inputParameters.get(i), "Test\\d");
            String testMethodName = getStringByRegex(inputParameters.get(i), "action\\d_[abc]");
            List<String> methodParams = getStringsMatchedByRegex(inputParameters.get(i), "data\\d[abc]_\\d");
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

    private static String getStringByRegex(String inputString, String regexp) throws Exception {
        return getStringsMatchedByRegex(inputString, regexp).stream()
                .findFirst()
                .orElseThrow(() -> new Exception("String matched with provided regexp wasn't found"));
    }
}