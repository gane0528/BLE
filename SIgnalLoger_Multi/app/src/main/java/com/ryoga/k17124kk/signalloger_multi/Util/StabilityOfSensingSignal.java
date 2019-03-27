package com.ryoga.k17124kk.signalloger_multi.Util;


//安定センシング区間
//
//  εチューブの考え方による安定センシング区間の判定
//

import android.util.Log;

public class StabilityOfSensingSignal {

    //安定センシング区間かどうか判断用の誤差範囲 +-
    private final int range = 4;
    private final int TimeThreshold = 5;//s　閾時間

    //安定センシング区間同士の比較差
    private final int stabilityRange = 5;
    private StabilityData stabilityData;

    private String baseTime = "0";
    private String startTime = "0";
    private String endTime = "0";


    private int baseRssi = 0;
    private int currentRssi = 0;

    private boolean isStability = false;


    private File_ReadWriter file_readWriter;
    private String memo;


    public StabilityOfSensingSignal() {
        file_readWriter = new File_ReadWriter();
    }

    public StabilityOfSensingSignal(String memo) {
        this();

        this.memo = memo;
    }

    public StabilityOfSensingSignal(int rssi, String memo) {
        this(memo);
        this.baseRssi = rssi;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
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


    public int getCurrentRssi() {
        return currentRssi;
    }

    public void setCurrentRssi(int currentRssi) {
        this.currentRssi = currentRssi;
    }

    //安定センシング区間の探索
    public void find_Stability_of_Sensing_Signal(String time, int rssi) {
        //現在のRssi更新
        setCurrentRssi(rssi);


        //誤差の範囲内であれば安定
        if (baseRssi - range < currentRssi && currentRssi < baseRssi + range) {
//            stabilityData.addRssi(rssi);

            isStability = true;

            Log.d("MYE_St", "安定 : " + time);


        } else {//範囲外なら不安定

            Log.d("MYE_St", "変化 : " + time);

//            if (rssi < stabilityData.getAve() - stabilityRange) {
//                stabilityData.setNegaPosi(1);
//                Log.d("MYE_St", "ポジ->ネガ");
//            } else if (stabilityData.getAve() + stabilityRange < rssi) {
//                stabilityData.setNegaPosi(0);
//                Log.d("MYE_St", "ネガ->ポジ");
//            } else {
//                Log.d("MYE_St", "何かの間違い");
//            }

            endTime = getBaseTime();
            writeLabel();

            startTime = time;
            isStability = false;
//            stabilityData.resetData();


        }

        Log.d("MYE_St", "===========================");
//        Log.d("MYE_St", stabilityData.toString());
        Log.d("MYE_St", "===========================");

        //現在のRssiを一つ前のRssiとする
        setBaseRssi(rssi);
        setBaseTime(time);

    }


    private void writeLabel() {
        String line = startTime + "," + endTime + ",";
        Log.d("MYE_St", line);

        file_readWriter.writeFile_Label(memo, line);
//        if (stabilityData.getNegaPosi() == 0) {
//            file_readWriter.writeFile_Label(memo, line + "ネガティブ");
//        } else {
//            file_readWriter.writeFile_Label(memo, line + "ポジティブ");
//        }

        Log.d("MYE_St", "書き出し");

    }

}
