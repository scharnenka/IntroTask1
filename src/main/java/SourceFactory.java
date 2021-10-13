import lombok.experimental.UtilityClass;

@UtilityClass
public class SourceFactory {

    public TestSourceSteps getSource(String[] args) {
        String joinedTestParams = String.join("", args);
        if (joinedTestParams.contains(".xls")) {
            return new ExcelSourceSteps();
        } else {
            return new StringSourceSteps();
        }
    }
}