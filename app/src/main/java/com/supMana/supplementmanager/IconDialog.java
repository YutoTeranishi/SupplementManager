package com.supMana.supplementmanager;

import android.app.Dialog;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class IconDialog extends Dialog {


    public Button button_add_ptn;
    public Button button_cancel;
    public Button button_delete;

    private AdView mAdView_iDialog;

    public IconDialog(MainActivity context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.icon_dialog);

        button_add_ptn = findViewById(R.id.add_ptn_btn);
        button_cancel = findViewById(R.id.cancel_btn);
        button_delete = findViewById(R.id.delete_btn);


        // Test App ID
        //MobileAds.initialize(context,"ca-app-pub-3940256099942544/6300978111"); //テスト用バナーID
        //MobileAds.initialize(context,"ca-app-pub-9493373763269069/2616454391"); //本番ID
        mAdView_iDialog = findViewById(R.id.adView_iDialog);
        /*
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("F2CBF2C3BE7FCE3C7DE5FC1AADAADA71") //テストデバイスの追加(リリース時削除)
                .build();
        mAdView_iDialog.loadAd(adRequest);
        */


    }
}
