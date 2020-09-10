package com.example.duyufeng;

public class StatInfo {
    String name,CONFIRMED,SUSPECTED,CURED,DEAD;
    StatInfo(String name,String CONFIRMED,String SUSPECTED,String CURED,String DEAD){
        this.name=name;
        this.CONFIRMED=CONFIRMED;
        this.SUSPECTED=SUSPECTED;
        this.CURED=CURED;
        this.DEAD=DEAD;
    }
    public String getName(){
        return name;
    }
    public String getCONFIRMED(){
        return CONFIRMED;
    }
    public String getSUSPECTED(){
        return SUSPECTED;
    }
    public String getCURED(){
        return CURED;
    }
    public String getDEAD(){
        return DEAD;
    }
}
