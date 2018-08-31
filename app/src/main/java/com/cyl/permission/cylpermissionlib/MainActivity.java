package com.cyl.permission.cylpermissionlib;
/**
 *
 *  author  :   Chia Yuan Lin (林家源)
 *
 *  email   :   lo919201@gmail.com
 *
 * **/
import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cyl.permission.cylpermission.CYLPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CYLPermission.PermissionCallback
{

    TextView textView;
    Button one;
    Button some;
    private final int CallNumber = 100;
    private static final String[] ArrayPer = {Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txt);

        one = (Button) findViewById(R.id.onecheck);
        one.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (Onecheck())
                    textView.setText("單一權限取得");
                else
                {
                    textView.setText("單一權限未取得");
                    OneRequest();
                }
            }

        });

        some = (Button) findViewById(R.id.somecheck);
        some.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (Somecheck())
                    textView.setText("多權限取得");
                else
                {
                    textView.setText("多權限未取得");
                    SomeRequest();
                }
            }
        });
    }

    public boolean Onecheck()
    {
        return  CYLPermission.CheckPermissions(this, Manifest.permission.CAMERA);
    }

    public void OneRequest()
    {
        CYLPermission.RequestPermission(this, CallNumber ,"無此權限會無法使用", new String[]{Manifest.permission.CAMERA});
    }

    public boolean Somecheck()
    {
        return  CYLPermission.CheckPermissions(this,ArrayPer);
    }

    public void SomeRequest()
    {
        CYLPermission.RequestPermission(this,CallNumber ,"無此權限會無法使用", ArrayPer);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        CYLPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    // 返回獲取權限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms)
    {
        String Msg = "";
        for (int i = 0; i<perms.size();i++)
            Msg = Msg + perms.get(i) + " , ";
        Log.w("onPermissionsGranted", requestCode + ", " +Msg);
    }

    // 返回獲取失敗的權限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms)
    {
        String Msg = "";
        for (int i = 0; i<perms.size();i++)
            Msg = Msg + perms.get(i) + " , ";
        Log.w("onPermissionsDenied", requestCode + ", " +Msg);
    }
}
