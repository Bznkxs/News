package com.example.duyufeng;

public interface PandemicData {
    String getName();
    long getOverallCases();
    long getActiveCases();
    long getDeaths();
    long getCured();
    long getNewCases();
    long getNewActiveCases();
    long getNewDeaths();
    long getNewCured();
    default long getOverallImportedCases() { return 0; }
    default long getNewImportedCases() { return 0; }
    default long getSuspectedCases() { return 0; }
    default long getNewSuspectedCases() { return 0; }

}
