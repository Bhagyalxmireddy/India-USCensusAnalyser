package censusanalyser;

public class IndiaCensusDAO {

    public  String state;
    public  int population;
    public  int areaInSqKm;
    public  int densityPerSqKm;


    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;


    }
}
