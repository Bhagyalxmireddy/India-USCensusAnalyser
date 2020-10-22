package censusanalyser;

public class CSVBuliderException extends Exception {



    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE,RUNTIME_EXCEPTION;
    }
    ExceptionType type;

    public CSVBuliderException(String message, ExceptionType type) {
        super(message);
        this.type=type;
    }

}
