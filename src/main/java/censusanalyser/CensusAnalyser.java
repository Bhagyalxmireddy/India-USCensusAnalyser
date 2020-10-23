package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import csvbulider.CSVBuliderException;
import csvbulider.CSVBuliderFactory;
import csvbulider.ICSVBulider;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusCSV> censusCSVList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBulider csvBulider = CSVBuliderFactory.createCSVBulider();
            censusCSVList = csvBulider.getCSVFileList(reader, IndiaCensusCSV.class);
            return  censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.RUNTIME_EXCEPTION);
        } catch (CSVBuliderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        }
    }

    public int loadIndiastateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBulider csvBulider = CSVBuliderFactory.createCSVBulider();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBulider.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            return  getCount(stateCodeCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.RUNTIME_EXCEPTION);
        } catch (CSVBuliderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type.name());
        }
    }
    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numofEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numofEateries;
    }
    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        if(censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No census data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndiaCensusCSV> censusCSVComparator = Comparator.comparing(census -> census.state);
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;

    }

    private void sort(Comparator<IndiaCensusCSV> censusCSVComparator) {
        for (int i = 0; i < censusCSVList.size() - 1; i++) {
            for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV censusCSV1 = censusCSVList.get(j);
                IndiaCensusCSV censusCSV2 =censusCSVList.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                   censusCSVList.set(j, censusCSV2);
                    censusCSVList.set(j + 1, censusCSV1);
                }
            }
        }
    }
}