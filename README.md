# CYLPermissionLib
一個簡單使用的權限設定

## Installation
### build.gradle
```
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
``` 
### dependency
``` 
dependencies{
  compile 'com.github.LinChiaYuan:CYLPermissionLib:v1.0'
  }
```

## 權限種類

### AndroidManifest.xml 設定
```
<uses-permission android:name="android.permission.CAMERA" />
```

| 功能 | 代碼 | 備註 |
| -------- | -------- | -------- |
|CAMERA|CAMERA|相機功能|
|位置|ACCESS_FINE_LOCATION|取得位置|
||ACCESS_COARSE_LOCATION|取得位置|
|麥克風|RECORD_AUDIO|錄音|
|儲存|READ_EXTERNAL_STORAGE|讀取|
||WRITE_EXTERNAL_STORAGE|寫入|
|日曆|READ_CALENDAR|讀取日曆|
||WRITE_CALENDAR|寫入日曆|
|聯絡人|READ_CONTACTS|讀取聯絡人|
||WRITE_CONTACTS|寫入聯絡人|
||GET_ACCOUNTS|取得手機|
|電話|READ_PHONE_STATE|讀通話狀態|
||CALL_PHONE|打電話|
||READ_CALL_LOG|讀通話記錄|
||WRITE_CALL_LOG|寫入通話記錄|
||ADD_VOICEMAIL|新增語音留言|
||USE_SIP|網路電話|
||PROCESS_OUTGOING_CALLS|存取打過電話|
|感應器|BODY_SENSORS|讀取資料|
|簡訊|SEND_SMS|傳送簡訊|
||RECEIVE_SMS|接收簡訊|
||READ_SM|讀取簡訊|
||RECEIVE_WAP_PUSH|接收WAP推播|
||RECEIVE_MMS|接收多媒體簡訊|


## 使用方法

#### MainActivity
```
public class MainActivity extends AppCompatActivity implements CYLPermission.PermissionCallback
```
#### Override
```
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
{
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    CYLPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
}


@Override
public void onPermissionsGranted(int requestCode, @NonNull List<String> perms)
{
  // 返回獲取權限
}

@Override
public void onPermissionsDenied(int requestCode, @NonNull List<String> perms)
{
    // 返回獲取失敗的權限
}
```

#### CheckPermissions 確認權限是否已允許
```
CYLPermission.CheckPermissions(Context,String[]);
```

#### RequestPermission 發出權限請求Dialog
```
CYLPermission.RequestPermission(Context,requestCode ,"被拒絕一次後顯示的介面", String[]);
```

