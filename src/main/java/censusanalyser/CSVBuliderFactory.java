package censusanalyser;

public class CSVBuliderFactory {
    public static ICSVBulider createCSVBulider() {
      return  new OpenCSVBulider();
    }
}
