import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExcelSourceSteps implements TestSourceSteps {

    @Override
    public List<TestModel> getTestsForExecution(String excelFilePAth) {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(excelFilePAth);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(String.format("Excel file wasn't found at path %s", excelFilePAth));
            throw new RuntimeException(e);
        }
        HSSFWorkbook workbook;
        try {
            workbook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            log.info(String.format("Can't import Excel workbook from path %s", excelFilePAth));
            throw new RuntimeException(e);
        }
        HSSFSheet excelSheet = workbook.getSheet("Regression");
        Iterator<Row> rowIterator = excelSheet.iterator();
        HSSFRow row = (HSSFRow) rowIterator.next();
        List<TestModel> listOfTestForExecution = new ArrayList<>();

        while (rowIterator.hasNext()) {
            row = (HSSFRow) rowIterator.next();
            String testClassName = row.getCell(0).getStringCellValue();
            String testMethodName = row.getCell(1).getStringCellValue();
            String methodParams = row.getCell(2).getStringCellValue();
            TestModel testModel = new TestModel(testClassName, testMethodName, getListOfParamsFromString(methodParams));
            listOfTestForExecution.add(testModel);
        }
        return listOfTestForExecution;
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