package com.jinandaxue.zy.songs;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

public class AddItemActivity extends AppCompatActivity {
    EditText editTextName;
    Button btn1;
    private int REQUEST_CODE_SCAN = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Bundle bundle=this.getIntent().getExtras();
        String name= bundle.getString("name");
        editTextName = (EditText) this.findViewById(R.id.edittext_songs_name);
        editTextName.setText(name);
        Button buttonAdd= (Button) this.findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new ButtonAddClick1());
        btn1= (Button) this.findViewById(R.id.code);
        btn1.setOnClickListener(new ButtonAddClick2());
    }

    private class ButtonAddClick1 implements OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.putExtra("name",String.valueOf(editTextName.getText()));
            Log.i("text", String.valueOf(editTextName.getText()));
            setResult(RESULT_OK,intent);
            AddItemActivity.this.finish();
        }
    }

    private class ButtonAddClick2 implements OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AddItemActivity.this, CaptureActivity.class);
            /*ZxingConfig是配置类
             *可以设置是否显示底部布局，闪光灯，相册，
             * 是否播放提示音  震动
             * 设置扫描框颜色等
             * 也可以不传这个参数
             * */
            ZxingConfig config = new ZxingConfig();
            config.setPlayBeep(true);//是否播放扫描声音 默认为true
            config.setShake(true);//是否震动  默认为true
            config.setDecodeBarCode(true);//是否扫描条形码 默认为true
            config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
            config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
            config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
            config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                final BookCollection bookCollection = new BookCollection(content);

                Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        editTextName.setText(bookCollection.getBook());
                    };
                };
                bookCollection.download(handler,content);

            }
        }
    }
}

