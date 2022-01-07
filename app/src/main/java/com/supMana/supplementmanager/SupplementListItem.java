package com.supMana.supplementmanager;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ListView;


public class SupplementListItem extends ListView {
        private Bitmap mThumbnail = null;
        private String mTitle = null;
        private String mUnit= null;
        private String mAmount = null;
        private String mTimes;
        /**
         * コンストラクタ
         * @param thumbnail サムネイル画像
         * @param title タイトル
         * @param unit 単位
         */

        public SupplementListItem(Context ctx, Bitmap thumbnail, String title, String unit, String amount) {
            super(ctx);
            mThumbnail = thumbnail;
            mTitle = title;
            mUnit = unit;
            mAmount = amount;

        }

        /**
         * サムネイル画像を設定
         * @param thumbnail サムネイル画像
         */
        public void setThumbnail(Bitmap thumbnail) {
            mThumbnail = thumbnail;
        }

        /**
         * タイトルを設定
         * @param title タイトル
         */
        public void setTitle(String title) {
            mTitle = title;
        }



        public void setmUnit(String unit) {
            this.mUnit = unit;
        }


    /**
         * サムネイル画像を取得
         * @return サムネイル画像
         */
        public Bitmap getThumbnail() {
            return mThumbnail;
        }

        /**
         * タイトルを取得
         * @return タイトル
         */
        public String getTitle() {
            return this.mTitle;
        }

        public String getUnit() {
            return mUnit;
        }

    public void setmAmount(String mAmount) {
        this.mAmount = mAmount;
    }

    public String getmAmount() {
        return mAmount;
    }
    public String getmTimes(){
            return mTimes;
    }

    public void setmTimes(String mTimes) {
        this.mTimes = mTimes;
    }
}

