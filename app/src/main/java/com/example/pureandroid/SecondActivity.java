package com.example.pureandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);
        TextView mTv = findViewById(R.id.test_tv);
        ImageView mIv = findViewById(R.id.test_iv);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                BitmapBinder imageBinder = (BitmapBinder) bundle.getBinder("bitmap");
                Bitmap bitmap = imageBinder.getBitmap();

                mTv.setText(String.format(("bitmap大小为%dkB"), bitmap.getByteCount() / 1024));
                mIv.setImageBitmap(bitmap);
            }

        }

    }
}
