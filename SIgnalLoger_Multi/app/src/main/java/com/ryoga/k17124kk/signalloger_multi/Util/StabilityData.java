package com.ryoga.k17124kk.signalloger_multi.Util;

import android.util.Log;

public class StabilityData {
    private int sumRssi = 0;
    private int count = 0;
    private int ave = 0;

    private int negaPosi = 0;//ネガティブ-0 ポジティブ-1

    public StabilityData() {
    }


    public void addRssi(int rssi) {
        sumRssi += rssi;
        count++;
    }


    public int getAve() {
        Log.d("MYE_St", (int) (sumRssi / count) + "");
        return (int) (sumRssi / count);
    }

    public void setNegaPosi(int negaPosi) {
        this.negaPosi = negaPosi;
    }

    public int getNegaPosi() {
        return negaPosi;
    }

    public void switch_NegaPosi() {
        if (getNegaPosi() == 0) {
            setNegaPosi(1);
        } else {
            setNegaPosi(0);
        }
    }


    public void resetData() {
        sumRssi = 0;
        ave = 0;
        count = 0;
    }


    @Override
    public String toString() {
        return "StabilityData{" +
                "sumRssi=" + sumRssi +
                ", count=" + count +
                ", ave=" + ave +
                ", negaPosi=" + negaPosi +
                '}';
    }
}
