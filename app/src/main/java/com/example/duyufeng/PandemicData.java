package com.example.duyufeng;

public interface PandemicData {
    String getName();
    long getOverallCases();
    long getActiveCases();
    long getDeaths();
    long getCured();
    default long getNewCases() { return 0; }
    default long getNewActiveCases() {return 0; }
    default long getNewDeaths() { return 0; }
    default long getNewCured() { return 0;}
    default long getOverallImportedCases() { return 0; }
    default long getNewImportedCases() { return 0; }
    default long getSuspectedCases() { return 0; }
    default long getNewSuspectedCases() { return 0; }

}
