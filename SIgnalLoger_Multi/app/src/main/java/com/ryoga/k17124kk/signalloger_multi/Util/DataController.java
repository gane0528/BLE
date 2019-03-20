package com.ryoga.k17124kk.signalloger_multi.Util;

import java.util.ArrayList;

public class DataController {

    //サンプル数
    private int FILTER_SIZE = 5;

    //中央値のインデックスがどこか
    //FILTER_SIZEが各中央値のindexに対応   1   2  3  4  5  6  7 8  9  10 11  偶数は n/2 +1の場所
    private final int FILTER_CENTER[] = {1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6};


    private DataSet dataSet;
    private int filterd_rssi = 0;
    private File_ReadWriter file_readWriter;
    //サンプルがFILTER_SIZE分だけ入る
    private ArrayList<DataSet> dataSetArrayList;


    public DataController() {
        file_readWriter = new File_ReadWriter();
        dataSetArrayList = new ArrayList<>();
    }

    public DataController(DataSet dataSet) {
        this();
        this.dataSet = dataSet;
    }


    public int getFILTER_SIZE() {
        return FILTER_SIZE;
    }

    public void setFILTER_SIZE(int FILTER_SIZE) {
        this.FILTER_SIZE = FILTER_SIZE;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }


    public void setDataSetRssi(String time, int rssi) {
        this.dataSet.setRssi(rssi);

        lowpathFilter(time, rssi);
    }


    public int getFilterd_rssi() {
        return filterd_rssi;
    }

    public void setFilterd_rssi(int filterd_rssi) {
        this.filterd_rssi = filterd_rssi;
    }


    public void resetDatasetArrayList() {
        dataSetArrayList.clear();
    }


    //ローパスを中央値でかける
    public void lowpathFilter(String time, int rssi) {
        dataSet.setRssi(rssi);

        //仮登録用にnewインスタンス
        DataSet ds = new DataSet(dataSet.getUuid(), dataSet.getMajor(), dataSet.getMinor(), dataSet.getMemo());
        ds.setRssi(rssi);
        dataSetArrayList.add(ds);


        //リストのサイズでソート用の配列確保
        int rssi_i[] = new int[dataSetArrayList.size()];

        //ソート用に数値取得
        for (int i = 0; i < dataSetArrayList.size(); i++) {
            rssi_i[i] = dataSetArrayList.get(i).getRssi();
        }


        //サイズ上限なら
        if (dataSetArrayList.size() >= FILTER_SIZE) {
            //数値ソート 昇順
            for (int i = 0; i < FILTER_SIZE; ++i) {
                for (int j = i + 1; j < FILTER_SIZE; ++j) {
                    if (rssi_i[i] < rssi_i[j]) {
                        rssi_i[i] = rssi_i[i] + rssi_i[j];
                        rssi_i[j] = rssi_i[i] - rssi_i[j];
                        rssi_i[i] = rssi_i[i] - rssi_i[j];

                    }
                }
            }


            //中央値取得してセット
            setFilterd_rssi(rssi_i[FILTER_CENTER[FILTER_SIZE - 1] - 1]);


            //リストの先頭を削除して詰める
            dataSetArrayList.remove(0);

        } else {

            if (dataSetArrayList.size() >= 2) {
                //数値ソート 昇順
                for (int i = 0; i < dataSetArrayList.size(); ++i) {
                    for (int j = i + 1; j < dataSetArrayList.size(); ++j) {
                        if (rssi_i[i] < rssi_i[j]) {
                            rssi_i[i] = rssi_i[i] + rssi_i[j];
                            rssi_i[j] = rssi_i[i] - rssi_i[j];
                            rssi_i[i] = rssi_i[i] - rssi_i[j];

                        }
                    }
                    //中央値取得してセット
                    setFilterd_rssi(rssi_i[FILTER_CENTER[dataSetArrayList.size() - 1] - 1]);

                }
            } else {
                //そのままセット
                setFilterd_rssi(ds.getRssi());
            }

        }

    }


    //安定センシング区間の探索
    public void find_Stability_of_Sensing_Signal() {

    }


    @Override
    public String toString() {
        return "DataContoroller{" +
                "dataSet=" + dataSet +
                ", file_readWriter=" + file_readWriter +
                '}';
    }
}
