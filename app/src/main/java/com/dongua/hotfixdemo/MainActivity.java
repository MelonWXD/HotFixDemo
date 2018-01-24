package com.dongua.hotfixdemo;

import android.Manifest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.dongua.hotfixdemo.util.SharedPreferenceUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

import static com.dongua.hotfixdemo.MyApp.SP_KEY_HOTFIX;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    Switch mHotFixSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_main).setOnClickListener(this);
        mHotFixSwitch = findViewById(R.id.sw_fix);
        mHotFixSwitch.setOnCheckedChangeListener(this);
        mHotFixSwitch.setChecked((Boolean) SharedPreferenceUtil.get(getApplicationContext(),SP_KEY_HOTFIX,true));

        checkPermission();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main:
                MyToast toast = new MyToast();
                toast.show(this);
                break;
            default:
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            SharedPreferenceUtil.put(getApplicationContext(),SP_KEY_HOTFIX,true);
        else
            SharedPreferenceUtil.put(getApplicationContext(),SP_KEY_HOTFIX,false);

    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean grant) throws Exception {
                            if (!grant)
                                Toast.makeText(MainActivity.this, "SD卡权限有问题", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
