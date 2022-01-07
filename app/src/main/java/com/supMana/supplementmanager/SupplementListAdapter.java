package com.supMana.supplementmanager;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SupplementListAdapter extends ArrayAdapter<SupplementListItem> {

    private int mResource;
    private List<SupplementListItem> mItems;
    private LayoutInflater mInflater;
    private Button mButton;
    private  TextView mTextView;
    private  EditText mTimes;
    private int times_val;
    /**
     * コンストラクタ
     * @param context コンテキスト
     * @param resource リソースID
     * @param items リストビューの要素
     */
    public SupplementListAdapter(Context context, int resource, List<SupplementListItem> items) {
        super(context, resource, items);
        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        times_val = 1;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        ViewHolder holder;

        // リストビューに表示する要素を取得
        final SupplementListItem item = mItems.get(position);


        if (convertView != null) {
            view = convertView;
        }
        else {
            view = mInflater.inflate(mResource, null);
        }


        // サムネイル画像を設定
        ImageView thumbnail = (ImageView)view.findViewById(R.id.thumbnail);
        thumbnail.setImageBitmap(item.getThumbnail());
        thumbnail.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ((ListView) parent).performItemClick(view, position, R.id.thumbnail);
             }
        });


        // タイトルを設定
        TextView title = (TextView)view.findViewById(R.id.title);
        title.setText(item.getTitle());

        // タイトルを設定
        TextView unit = (TextView)view.findViewById(R.id.unit);
        unit.setText(item.getUnit());

        TextView amount_v = (TextView)view.findViewById(R.id.amountView);
        amount_v.setText(item.getmAmount());


        mButton = view.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.button);
            }
        });

        mTimes = (EditText)view.findViewById(R.id.times);

           item.setmTimes(String.valueOf(times_val)); //各アイテムの倍率のところに代入 1に戻るのでなぞ

        mTimes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //item.setmTimes(Integer.toString(i1));
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!editable.toString().equals("")) {
                    item.setmTimes(editable.toString());
                    times_val = Integer.parseInt(editable.toString());
                }

            }
        });

        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getmResource() {
        return mResource;
    }
    private static class ViewHolder{
        public Button button;
    }
    public void setTimes_val(int t_val){
        times_val = t_val;
    }






}