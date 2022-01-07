package com.supMana.supplementmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.FileNotFoundException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class Sign_up_Activity extends ProteinAppCompatActivity {
    protected EditText name; //名前入力フォーム
    protected EditText amount; //総量入力フォーム
    protected TextView amTextView; //"AMOUNT:"表示のテキストビュー
    protected EditText one_time_serv; //1回分の量入力フォーム
    protected TextView otsTextView; //"one_time_serv:"表示のテキストビュー
    protected TextView i_amount; //総量表示部分(編集時)
    protected TextView t_amount; //現残量表示部分(編集時)
    protected Button sign_up_btn;
    protected Button back_btn;
    protected int unit = 0; //単位の種類 //の0ときkg,1のとき錠
    protected  TextView a_unitView; //amountの単位表示
    protected TextView o_unitView; //one_time_servの単位表示
    protected TextView t_unitView; //t_amountの単位表示
    protected TextView i_unitView; //i_amountの単位表示
    protected boolean new_flg;
    protected int position;

    private AdView mAdView_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Intent intent = getIntent();
        new_flg = intent.getBooleanExtra("new_flg",true);
        position=intent.getIntExtra("position",-1);
        setContentView( R.layout.activity_sign_up_);


        // Test App ID
        //MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111"); //テスト環境ID
        //MobileAds.initialize(this,"ca-app-pub-9493373763269069/2616454391"); //本番環境ID
        mAdView_sign_up = findViewById(R.id.adView_sign_up);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("F2CBF2C3BE7FCE3C7DE5FC1AADAADA71") //テストデバイスの追加(リリース時削除)
                .build();
        mAdView_sign_up.loadAd(adRequest);



        //-------------インスタンスの生成とidの割付--------------------
        name = new EditText(this);
        name = (EditText) findViewById(R.id.protein_name);

        t_amount = new TextView(this);
        t_amount =(TextView)findViewById(R.id.t_amount);

        t_unitView = new TextView(Sign_up_Activity.this);
        t_unitView = (TextView) findViewById(R.id.tamount_unit);

        i_amount = new TextView(this);
        i_amount =(TextView)findViewById(R.id.i_amount);

        i_unitView = new TextView(Sign_up_Activity.this);
        i_unitView = (TextView) findViewById(R.id.iamount_unit);

        amTextView = new TextView(this);
        amTextView = (TextView) findViewById(R.id.amTextView);

        amount = new EditText(this);
        amount= (EditText) findViewById(R.id.amount);

        one_time_serv = new EditText(this);
        one_time_serv = (EditText) findViewById(R.id.one_time_serv);

        otsTextView = new TextView(this);
        otsTextView= (TextView) findViewById(R.id.otsTextView);

        a_unitView = new TextView(this);
        a_unitView = (TextView) findViewById(R.id.amount_unit);

        o_unitView = new TextView(this);
        o_unitView = (TextView) findViewById(R.id.one_time_serv_unit);

        sign_up_btn = new Button(this);
        sign_up_btn = (Button)findViewById(R.id.sign_up_btn);
        //----------------------------------------------------------------
        if(new_flg||(position==-1)) {
            t_amount.setVisibility( View.INVISIBLE);
            i_amount.setVisibility(View.INVISIBLE);
            t_unitView.setVisibility(View.INVISIBLE);
            i_unitView.setVisibility(View.INVISIBLE);

        }else{
            name.setText(proteinslist.get(position).getName()); //名前の設定
            t_amount.setText(Double.toString(proteinslist.get(position).getTotal_amount())); //現残量の表示
            i_amount.setText(Double.toString(proteinslist.get(position).getInit_amount())); //初期残量の表示
            sign_up_btn.setText(R.string.add_btn);
            amTextView.setText(R.string.add_amount);
            otsTextView.setVisibility(View.INVISIBLE); //"one_time_serv:"表示のテキストビュー非表示
            one_time_serv.setVisibility(View.INVISIBLE); //1回の量の非表示
            o_unitView.setVisibility(View.INVISIBLE); //単位非表示
        }

        // idがgroupのRadioGroupを取得
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.pro_or_sup_btn);
        if((new_flg==false)&&(proteinslist.get(position).getUnit_num()==1)){
            radioGroup.check(R.id.supplement_btn);
            t_unitView.setText("錠/");
            i_unitView.setText("錠");
            a_unitView.setText("錠");
            o_unitView.setText("錠");
            unit = 1;
        }
        // radioGroupの選択値が変更された時の処理を設定
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            public void onCheckedChanged(RadioGroup group, int checkedId){

                // checkedIdには選択された項目のidがわたってくるので、そのidのRadioButtonを取得
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);

                if(new_flg) { //新規作成の場合
                    // 表示する文字列を選択値によって変える
                    switch (checkedId) {
                        case R.id.protein_btn:
                            a_unitView.setText("kg");
                            o_unitView.setText("g");
                            unit = 0;
                            break;
                        case R.id.supplement_btn:
                            a_unitView.setText("錠");
                            o_unitView.setText("錠");
                            unit = 1;
                            break;
                    }
                }else{ //追加購入の場合
                    // 表示する文字列を選択値によって変える
                    switch (checkedId) {
                        case R.id.protein_btn:
                            a_unitView.setText("kg");
                            t_unitView.setText("kg/");
                            i_unitView.setText("kg");
                            //o_unitView.setText("g");
                            unit = 0;
                            break;
                        case R.id.supplement_btn:
                            a_unitView.setText("錠");
                            t_unitView.setText("錠/");
                            i_unitView.setText("錠");
                            //o_unitView.setText("錠");
                            unit = 1;
                            break;

                    }
                }

            }
        });
    }

    public void onClick(View v) throws FileNotFoundException {
        if (v.getId()==R.id.sign_up_btn){
            String ptn_name = name.getText().toString();
            double new_amt;
            if(amount.getText().toString().equals("")||name.getText().toString().equals("")) { //未入力時の処理
                if (amount.getText().toString().equals("")) {
                    amount.setHint("入力してください");
                    amount.setHintTextColor( Color.RED);
                }
                if (name.getText().toString().equals("")){
                    name.setHint("入力してください");
                    name.setHintTextColor(Color.RED);
                }
                if (one_time_serv.getText().toString().equals("")) { //1回の使用量が入力されていないとき
                    one_time_serv.setHint("入力してください");
                    one_time_serv.setHintTextColor(Color.RED);
                }
            }else {
                new_amt = Double.parseDouble(amount.getText().toString());

                Protein new_pro;

                if (new_flg) { //新規追加の場合
                    double new_ots;
                    if (one_time_serv.getText().toString().equals("")) { //1回の使用量が入力されていないとき
                        one_time_serv.setHint("1回の使用量を入力してください");
                    } else {
                        new_ots = Double.parseDouble(one_time_serv.getText().toString());

                        new_pro = new Protein(ptn_name, new_amt, new_amt, new_ots, unit, unit); //プロテインオブジェクトの生成

                        proteinslist.add(new_pro); //新規登録

                        log_file.save(proteinslist);

                        renew_View(rv_type.add); //ListViewの更新

                        finish(); //アクティビティの終了
                    }
                } else {
                    double i_amt = proteinslist.get(position).getInit_amount(); //元の初期容量の取得
                    double t_amt = proteinslist.get(position).getTotal_amount(); //元現残量の取得
                    proteinslist.get(position).setName(name.getText().toString()); //名前の設定
                    proteinslist.get(position).setInit_amount(i_amt + new_amt); //初期容量に追加
                    proteinslist.get(position).setTotal_amount(t_amt + new_amt); //現残量に追加

                    double t_amount = 0.0;
                    t_amount = proteinslist.get(position).getTotal_amount();
                    proteinslist.get(position).setInit_amount(t_amount);
                    proteinslist.get(position).subTotal_amount(t_amount, proteinslist.get(position).getOne_time_serv(),0);//残量,残率、残回数を更新

                    log_file.save(proteinslist);
                    renew_View(rv_type.change);
                    finish(); //アクティビティの終了
                }
            }
        }else{ //backボタン
            finish();//アクティビティの終了
        }
    }
}
