package com.supMana.supplementmanager;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
/*
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
*/
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends ProteinAppCompatActivity {
    private ListView listView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        proteinslist = new ArrayList<>();
        List<String> strlist = new ArrayList<String>();
        List<String> tmp_strlist = new ArrayList<String>();
        //広告表示
        // Test App ID
        //MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111"); //テスト環境ID
        //MobileAds.initialize(this,"ca-app-pub-9493373763269069/2616454391"); //本番環境ID
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                                            //.addTestDevice("F2CBF2C3BE7FCE3C7DE5FC1AADAADA71") //テストデバイスの追加(リリース時削除)
                                            .build();
        mAdView.loadAd(adRequest);



        try {
            log_file = new Log_File(this);
            Protein file_pro;
            if(log_file.exist()) { //ファイルが存在する場合のみ読み込み
                strlist=log_file.read();
                if(strlist!=null) {

                    for (int i = 0; i < strlist.size(); i = i + 6) {
                        file_pro = new Protein(strlist.get(i).toString(), //名前
                                Double.parseDouble(strlist.get(i + 1).toString()), //初期量
                                Double.parseDouble(strlist.get(i + 2).toString()),//総量
                                Double.parseDouble(strlist.get(i + 3).toString()), //1回分の量
                                Integer.valueOf(strlist.get(i + 4).toString()), //アイコンのフラグ
                                Integer.valueOf(strlist.get(i + 5).toString()) //単位のフラグ
                        );
                        proteinslist.add(file_pro);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //+ボタンのイベントリスナー
        final FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                Intent intent = new Intent(getApplication(), Sign_up_Activity.class); //新規登録画面への移動
                intent.putExtra("new_flg",true);
                intent.putExtra("position",-1);
                startActivity(intent);
                //ダブルタップ禁止
                view.setEnabled(false);
                //new Handler().postDelayed( new Runnable() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                        public void run() {
                        view.setEnabled(true);
                    }
                }, 2000L);view.setEnabled(false);
                //new Handler().postDelayed(new Runnable() {
               new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                        public void run() {
                        view.setEnabled(true);
                    }
                }, 2000L);

            }
        });


        //画像つきリストの生成
        // レイアウトからリストビューを取得
        listView = (ListView)findViewById(R.id.listView);

        // リストビューに表示する要素を設定
        final ArrayList<SupplementListItem> sampleListItems = new ArrayList<>();
        Bitmap bmp =null;
        SupplementListItem item = null;
        String amount_str = null;
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
            bmp =null; //クリアする
            item=null;
            amount_str =null; //クリアする
        }


        // 出力結果をリストビューに表示
        adapter = new SupplementListAdapter(this, R.layout.list_item, sampleListItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(id!=R.id.thumbnail) {
                    double t_amount = 0.0;
                    double one_time_drink = 0.0;
                    int times =0;

                    t_amount = proteinslist.get(position).getTotal_amount();

                    one_time_drink = proteinslist.get(position).getOne_time_serv();//*倍率しないといけない(まだやってない)
                    times= Integer.parseInt(sampleListItems.get(position).getmTimes());

                    proteinslist.get(position).subTotal_amount(t_amount, one_time_drink,times);//残量,残率、残回数を更新

                    renew_View(rv_type.change); //listViewの更新

                    Snackbar.make(view, "残量" + String.format("%.2f",proteinslist.get(position).getTotal_amount()) +"kg/"+ "残回数" + proteinslist.get(position).getRemaining_times(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    try {
                        log_file.save(proteinslist);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }else{
                    show_Dailog(position);

                }
            }
        });

    }
    private void show_Dailog(final int position){
        final IconDialog idailog= new IconDialog(this);

        idailog.button_add_ptn.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                idailog.dismiss(); //ダイアログ削除

                Intent intent = new Intent(getApplication(), Sign_up_Activity.class); //新規登録画面への移動
                intent.putExtra("new_flg",false);
                intent.putExtra("position",position);
                startActivity(intent);
                //ダブルタップ禁止
                view.setEnabled(false);
                //new Handler().postDelayed(new Runnable() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public void run() {
                        view.setEnabled(true);
                    }
                }, 1000L);view.setEnabled(false);
                //new Handler().postDelayed(new Runnable() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public void run() {
                        view.setEnabled(true);
                    }
                }, 1000L);

            }
        });
        idailog.button_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                idailog.dismiss();
                try {
                    log_file.remove(position);
                    proteinslist.remove(position);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                renew_View(rv_type.change); //listViewの更新
            }
        });

        idailog.button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                idailog.dismiss();
            }
        });
        idailog.show();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
