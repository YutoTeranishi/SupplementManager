package com.supMana.supplementmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ProteinAppCompatActivity extends AppCompatActivity {
    protected enum rv_type {add,del,change};
    protected static ArrayList<Protein> proteinslist;
    protected static Log_File log_file;
    protected static SupplementListAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //残率の変化に応じた画像の変更
    protected Bitmap get_icon(int remaining_rate){
        Bitmap bmp =null;
        switch(remaining_rate) {
            case 0:bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zero_white);break; //0%
            case 10:bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ten_red);break; //10%
            case 25:bmp = BitmapFactory.decodeResource(getResources(), R.drawable.twenty_five_yellow);break; //25%
            case 50:bmp = BitmapFactory.decodeResource(getResources(), R.drawable.fifty_orenge);break; //50%
            case 75:bmp = BitmapFactory.decodeResource(getResources(), R.drawable.seventy_five_blue);break; //75%
            case 100:bmp = BitmapFactory.decodeResource(getResources(), R.drawable.one_hundred_green);break; //100%
            default:break;
        }
        return bmp;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu); //ツールバーメニューの削除
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        //        // automatically handle clicks on the Home/Up button, so long
        //        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void renew_View(rv_type type){

        // リストビューに表示する要素を設定
        ArrayList<SupplementListItem> sampleListItems = new ArrayList<>();
        Bitmap bmp = null;
        String amount_str = null;
        SupplementListItem item = null;
        for (int i = 0; i < proteinslist.size(); i++) { //配列の要素数分画像の割り当てを行なう リスト版
            bmp = get_icon(proteinslist.get(i).getrRemaingRate());

            if (proteinslist.get(i).getUnit_num()==0) {
                amount_str= "残"+String.format("%2d",proteinslist.get(i).getRemaining_times())+"回"+"\n"
                        +String.format("%.2f", proteinslist.get(i).getTotal_amount()) + "kg /"
                        + String.format("%.2f", proteinslist.get(i).getInit_amount()) + "kg";
            }else{
                amount_str= "残"+String.format("%2d",proteinslist.get(i).getRemaining_times())+"回"+"\n"
                        +String.format("%.1f", proteinslist.get(i).getTotal_amount()) + "錠 /"
                        + String.format("%.1f", proteinslist.get(i).getInit_amount()) + "錠";
            }

            item = new SupplementListItem(this, bmp, proteinslist.get(i).getName(), proteinslist.get(i).getUnit(),amount_str);

            sampleListItems.add(item);
            bmp = null; //クリアする
            item =null;
            amount_str =null; //クリアする
        }

        if(type == rv_type.add) {
            adapter.add(sampleListItems.get(sampleListItems.size() - 1));
        }else if(type== rv_type.change){
            adapter.clear(); //アダプターの削除
            adapter.addAll(sampleListItems); //全要素書き換え
        }
        adapter.notifyDataSetChanged();//Viewの更新
    }

    public static ArrayList<Protein> getProteinslist() {
        return proteinslist;
    }
}
