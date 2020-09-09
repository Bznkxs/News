package com.example.duyufeng;

import java.util.Random;

public class SimplePandemicData implements PandemicData {
    Random rng;
    String name;
    long overallCases, activeCases, deaths, cured, newCases, newActiveCases, newDeaths, newCured;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getOverallCases() {
        return overallCases;
    }

    @Override
    public long getActiveCases() {
        return activeCases;
    }

    @Override
    public long getDeaths() {
        return deaths;
    }

    @Override
    public long getCured() {
        return cured;
    }

    @Override
    public long getNewCases() {
        return newCases;
    }

    @Override
    public long getNewActiveCases() {
        return newActiveCases;
    }

    @Override
    public long getNewDeaths() {
        return newDeaths;
    }

    @Override
    public long getNewCured() {
        return newCured;
    }

    public SimplePandemicData() {
        rng = new Random();
        name = "Place" + rng.nextDouble();
        overallCases = rng.nextInt();
        cured = rng.nextInt();
        deaths = rng.nextInt();
        activeCases = rng.nextInt();
        newCases = rng.nextInt();
        newActiveCases = rng.nextInt();
        newCured = rng.nextInt();
        newDeaths = rng.nextInt();
    }

    public SimplePandemicData(String name) {
        rng = new Random();
        this.name = name;
        overallCases = rng.nextInt(10000000);
        cured = rng.nextInt((int) overallCases);
        deaths = rng.nextInt((int) (overallCases - cured));
        activeCases = rng.nextInt((int) overallCases);
        newCases = rng.nextInt(50000);
        newActiveCases = rng.nextInt(50000);
        newCured = rng.nextInt(50000);
        newDeaths = rng.nextInt(50000);
    }
}
