package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBulider<E> {

    public  Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CSVBuliderException;


    }
