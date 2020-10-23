package censusanalyser;

public class IndiaCensusDAO {

    public  String state;
    public   String stateCode;
    public  int population;
    public  int areaInSqKm;
    public  int densityPerSqKm;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public IndiaCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV){
        stateCode = indiaStateCodeCSV.stateCode;
    }
}
