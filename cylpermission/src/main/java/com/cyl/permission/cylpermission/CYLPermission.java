package com.cyl.permission.cylpermission;
/**
 *
 *  author  :   Chia Yuan Lin (林家源)
 *
 *  email   :   lo919201@gmail.com
 *
 * **/
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CYLPermission
{
    private static final String ClassName = "CYLPermission";

    /**
     * 確認請求權限是否可行
     *
     * @param context           呼叫 context
     * @param perms             一個或多個權限,如 {@link Manifest.permission#CAMERA}
     * @return true 權限可行, false 權限不可行
     */
    public static boolean CheckPermissions(@NonNull Context context, @Size(min = 1) @NonNull String... perms)
    {
        // 即使API < M 也可以執行
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            Log.w(ClassName, "API 版本 < M, 預設為true");
            return true;
        }

        // 檢查帶入的所有權限 是否已取得 (已取得為false)
        for (String perm : perms)
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    /**
     * 發出確認請求權限Dialog
     *
     * @param context           呼叫 context
     * @param requestCode       呼叫用的辨識代號
     * @param Msg               呼叫 被拒絕一次後顯示的介面
     * @param perms             一個或多個權限,如 {@link Manifest.permission#CAMERA}
     * @return true 權限可行, false 權限不可行
     */
    public static void RequestPermission(@NonNull Context context,@NonNull int requestCode, @NonNull String Msg, @Size(min = 1) @NonNull String... perms)
    {
        //  請求之所有權限皆擁有
        if(CheckPermissions(context,perms))
            return;

        final String[] perm = perms;
        final Context cot = context;
        final int code = requestCode;

        boolean check = false;

        for (String per : perm)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) cot, per))
            {
                check = true;
                break;
            }
        }

        if(check)
        {
            final String msg = Msg;
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) cot);
            alertDialog.setMessage(msg)
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions((Activity) cot, perm, code);
                        }
                    })
                    .setNegativeButton("拒絕", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        else
            ActivityCompat.requestPermissions((Activity) cot, perms, code);
    }



    /**
     * 接收 Callback 的 interface (CYLPermission.requestPermissions())
     */
    public interface PermissionCallback extends ActivityCompat.OnRequestPermissionsResultCallback
    {
        // 返回獲取權限
        void onPermissionsGranted(int requestCode, @NonNull List<String> perms);
        // 返回獲取失敗的權限
        void onPermissionsDenied(int requestCode, @NonNull List<String> perms);
    }


    /**
     * 用callback回傳處理結果,將拒絕與確認的權限回傳
     *
     * @param requestCode  呼叫用的辨識代號
     * @param permissions  一個或多個權限,如 {@link Manifest.permission#CAMERA}
     * @param grantResults 權限結果回傳
     * @param context      回傳的context
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, @NonNull Context context)
    {
        // 收集處理結果
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++)
        {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                granted.add(perm);
            else
                denied.add(perm);
        }

        // iterate through all receivers

        // 把允許的傳入 onPermissionsGranted
        if (!granted.isEmpty())
            if (context instanceof PermissionCallback)
                ((PermissionCallback) context).onPermissionsGranted(requestCode, granted);

        // 把拒絕的的傳入 onPermissionsDenied
        if (!denied.isEmpty())
            if (context instanceof PermissionCallback)
                ((PermissionCallback) context).onPermissionsDenied(requestCode, denied);
    }
}
