package com.example.wangli.other;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wangli.myapplication.Constants;
import com.example.wangli.myapplication.FileUtils;
import com.example.wangli.myapplication.HelperOne;
import com.example.wangli.myapplication.R;

import java.io.File;
import java.io.IOException;

public class OtherActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperOne helperOne = new HelperOne();
                    helperOne.haha(OtherActivity.this);
            }
        });
        findViewById(R.id.fix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("OtherActivity","===onClick==");
                File sourceFile = new File(Environment.getExternalStorageDirectory(),Constants.DEX_NAME);
                File targetFile = new File(getDir(Constants.DEX_DIR,Context.MODE_PRIVATE).getAbsolutePath()+File.separator+Constants.DEX_NAME);
                try {
                    if (targetFile.exists()){
                        targetFile.delete();
                    }
                    FileUtils.copyFile(sourceFile,targetFile);
                    Toast.makeText(OtherActivity.this,"复制完成",Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
