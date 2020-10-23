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
    List<IndiaStateCodeCSV> censusCSVList1 = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBulider csvBulider = CSVBuliderFactory.createCSVBulider();
            //Iterator<IndiaCensusCSV> csvFileIterator = csvBulider.getCSVFileIterator(reader,IndiaCensusCSV.class);
            //Iterable<IndiaCensusCSV> csvIterable = () -> csvFileIterator;
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
            Iterator<IndiaStateCodeCSV>  stateCSVFileIterator = csvBulider.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVFileIterator;
           // censusCSVList1 =  csvBulider.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return  censusCSVList1.size();
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
        this.sort(censusCSVList,censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;

    }

    private void sort(List<IndiaCensusCSV> censusCSVList,Comparator<IndiaCensusCSV> censusCSVComparator) {
        for (int i = 0; i < this.censusCSVList.size() - 1; i++) {
            for (int j = 0; j < this.censusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV censusCSV1 = this.censusCSVList.get(j);
                IndiaCensusCSV censusCSV2 =this.censusCSVList.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    this.censusCSVList.set(j, censusCSV1);
                    this.censusCSVList.set(j + 1, censusCSV2);
                }
            }
        }
    }

    public String getstateCodeWiswSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        if (censusCSVList1 == null || censusCSVList1.size() == 0) {
            throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        Comparator<IndiaStateCodeCSV> stateComparator = Comparator.comparing(census -> census.state);
        this.sortStateCode(censusCSVList1, stateComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    private void sortStateCode(List<IndiaStateCodeCSV> censusCSVList1, Comparator<IndiaStateCodeCSV> stateComparator) {
        for (int i = 0; i < this.censusCSVList1.size() - 1; i++) {
            for (int j = 0; j < this.censusCSVList1.size() - i - 1; j++) {
                IndiaStateCodeCSV censusCSV1 = this.censusCSVList1.get(j);
                IndiaStateCodeCSV censusCSV2 = this.censusCSVList1.get(j + 1);
                if (stateComparator.compare(censusCSV1, censusCSV2) > 0) {
                    this.censusCSVList1.set(j, censusCSV1);
                    this.censusCSVList1.set(j + 1, censusCSV2);
                }
            }
        }
    }

    public String getStateCensusPopulationSortedData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        if(censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(censusCSVList, csvComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }
}