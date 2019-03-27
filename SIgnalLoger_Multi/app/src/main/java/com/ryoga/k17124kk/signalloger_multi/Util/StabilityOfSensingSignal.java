package com.ryoga.k17124kk.signalloger_multi.Util;


//安定センシング区間
public class StabilityOfSensingSignal {

    //安定センシング区間かどうか判断用の誤差範囲 +-
    private final int range = 5;
    private final int TimeThreshold = 5;//s　閾時間

    //安定センシング区間同士の比較差
    private final int stabilityRange = 5;

    private String startTime = "";
    private String endTime = "";

    private int baseRssi = 0;

    private boolean isStability = false;


    public StabilityOfSensingSignal() {
    }

    public StabilityOfSensingSignal(int rssi) {
        this.baseRssi = rssi;
    }

    public StabilityOfSensingSignal(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public StabilityOfSensingSignal(String startTime, String endTime, int rssi) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.baseRssi = rssi;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getBaseRssi() {
        return baseRssi;
    }

    public void setBaseRssi(int rssi) {
        this.baseRssi = rssi;
    }


    //安定センシング区間の探索
    public void find_Stability_of_Sensing_Signal(String time, int rssi) {
        if (rssi - range <= baseRssi && baseRssi <= rssi + range) {


            isStability = true;
            endTime = time;


        } else {

            startTime = time;
            isStability = false;
            baseRssi = rssi;

        }

    }


}
