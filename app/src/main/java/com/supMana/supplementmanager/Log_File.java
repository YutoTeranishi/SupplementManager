package com.supMana.supplementmanager;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Log_File {
    private String file_path;
    private List<String> lineList;
    private Activity act;
    private File file;

    Log_File(Activity activity) throws IOException {
        act = activity;
        lineList = new ArrayList<String>();
        file = file = new File(act.getFilesDir() + "/" + "protein_log.txt");

    }

    public String getFile_path() {
        return file_path;
    }
    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public List<String> getLineList() {
        return lineList;
    }
    public void setLineList(List<String> lineList) {
        this.lineList = lineList;
    }

    public boolean write() throws FileNotFoundException {

        try{
            String str = "";
            for (int i = 0; i < lineList.size(); i++) {
                str += lineList.get(i);
             }
            FileOutputStream out = act.openFileOutput("protein_log.txt", MODE_PRIVATE);
            out.write(str.getBytes());

            str=""; //初期化
            return true;
        }catch( IOException e ) {
            e.printStackTrace();
            return false;
        }
    }
    public List<String> read() throws IOException {

        String line;
        List<String> datas;

        if(exist()) {
            try {
                FileInputStream in = act.openFileInput("protein_log.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String str = "";

                while ((str = reader.readLine()) != null) {
                    lineList.add(str+"\n"); //行ごとに格納
                }
                reader.close();
                datas = getData(lineList);
                return datas;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    private int add(String sentence){
        lineList.add(sentence);
        return 0;
    };
    public boolean exist(){

        if(file.exists()) {
            return true;
        }else{
            return false;
        }
    }
    public boolean save(ArrayList<Protein> new_ptnlist) throws FileNotFoundException {
        String log_sentence=null;
        lineList.clear();
        for(int i = 0;i<new_ptnlist.size();i++) {

            log_sentence = new_ptnlist.get(i).getName() + "," + new_ptnlist.get(i).getInit_amount() + ","
                    + new_ptnlist.get(i).getTotal_amount() + "," + new_ptnlist.get(i).getOne_time_serv() + "," + new_ptnlist.get(i).getIcon_num()
                    + "," + new_ptnlist.get(i).getIcon_num() + "\r\n";
            add(log_sentence);
            log_sentence=null;
        }
            if(write()) {
                return true;
            }else{
                return false;
            }
    }
    public boolean remove(int position) throws FileNotFoundException {

        if(position<lineList.size()){
            lineList.remove(position);
            write();
            return true;
        }else{
            return false;
        }
    }

    private List<String> getData(List<String> data) {
        List<String> datas;
        if (data.size()!=0) {
            datas = Arrays.asList(data.toString().split(",")); //名前の前とUnit_numの後に"["が入る
            for (int i = 0; i < datas.size(); i = i + 6) {
                datas.set(i, datas.get(i).toString().substring(1));   //名前の前と"["削除
                datas.set(i + 5, datas.get(i + 5).toString().substring(0, 1)); //Unit_numの後の"["削除
            }
            return datas;
        }
        return null;
    }
}
