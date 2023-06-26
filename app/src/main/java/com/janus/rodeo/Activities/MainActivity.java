package com.janus.rodeo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.janus.rodeo.BuildConfig;
import com.janus.rodeo.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
//import androidx.multidex.BuildConfig;
import com.janus.rodeo.Communication.RestServices;
import com.janus.rodeo.Dialogs.Listener;
import com.janus.rodeo.Dialogs.MessageDialog;
import com.janus.rodeo.Helpers.RodeoSingleton;
import com.janus.rodeo.Helpers.Utilities;
import com.janus.rodeo.Models.ResponsePost;
import com.janus.rodeo.Models.User;
import com.janus.rodeo.Storage.DataBaseConection;
import java.util.ArrayList;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {
    private EditText etUserEmail;
    private EditText etPassword;
    private String password;
    private DataBaseConection dataBaseConection;
    private String storedPassword;
    private int versionCode;
    private String applicationID;
    private String versionName;
    private View decorView;
    private Context context = this;
    private static MainActivity instance = new MainActivity();
    private CheckBox show_hide_password;
    private TextView display_version_app;
    private EditText passwordText;
    private User userLogged;
    private String timeZone;
    public String username;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeZone = context.getResources().getString(R.string.time_zone_default);
        RestServices.getInstance(this).startRestAPI();
        etUserEmail = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.Password);
        etUserEmail.setText("");
        etPassword.setText("");
        display_version_app = (TextView) findViewById(R.id.tvVersion);
        versionCode = BuildConfig.VERSION_CODE;
        applicationID = BuildConfig.APPLICATION_ID;
        PackageManager pm = getApplicationContext().getPackageManager();
        String pkgName = getApplicationContext().getPackageName();
        PackageInfo pkgInfo = null;

        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String ver = pkgInfo.versionName;
        versionName = ver;
        display_version_app.setText("version: "+versionName);
        findViewById();
        show_hide_pass();
        decorView=getWindow().getDecorView();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility==0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);
        permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        dataBaseConection = DataBaseConection.get(this);
    }

    private int hideSystemBars(){
        return
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                ;
    }

    public void SignIN(View view) {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        if (RestServices.getInstance(this).isOnline()) {
            username = etUserEmail.getText().toString();
            password = etPassword.getText().toString();
            if (username.equals("") || password.equals("")) {
                AlertUserCustom("Warning","Please fill in all fields");
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            } else {
                new AsyncTaskRequest().execute();
            }
        } else {
            AlertUserCustom("Error","Check your Internet connection");
            findViewById(R.id.progressBar).setVisibility(View.GONE);
        }
    }

    private void autoLogin(){
        User user = dataBaseConection.getUser();
        if(user != null){
            etUserEmail.setText(user.getUserName());
            etPassword.setText(user.getPassword());
            if(RestServices.getInstance(this).isOnline()){
                new AsyncTaskRequest().execute();
            } else {
                AlertUserCustom("Error","Check your Internet connection");
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        etUserEmail.setText("");
        etPassword.setText("");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        etUserEmail.setText("");
        etPassword.setText("");
        super.onPause();
    }

    @Override
    protected void onResume() {
        etUserEmail.setText("");
        etPassword.setText("");
        super.onResume();
    }

    public void show_hide_pass() {
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    public void findViewById() {
        passwordText = (EditText) findViewById(R.id.Password);
        show_hide_password = (CheckBox) findViewById(R.id.chShowHidePassword);
    }

    private class AsyncTaskRequest extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... urls) {
            return LoginRequest();
        }
        @Override
        protected void onPostExecute(User result) {
            LoginCompleted(result);
        }
    }

    private User LoginRequest() {
        User userTemp = new User();
        try {
            username = etUserEmail.getText().toString();
            password = etPassword.getText().toString();
            if (username.equals("") || password.equals("")) {
                AlertUserCustom("Warning","Please fill in all fields");
            } else {
                if(RestServices.getInstance(this).isOnline()){
                    userTemp = RestServices.getInstance(this).loginRequest(username, password);
                } else {
                    AlertUserCustom("Error","Check your Internet connection");
                }
            }
        } catch (Exception ex) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() { }
            });
            Log.e("Error", "Error login request-->" + ex.toString());
        }
        return userTemp;
    }

    private void LoginCompleted(User usuario){
        if(usuario != null){
            String userName = usuario.getFullName();
            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            switch(userName){
                case "BAD BAD":
                    AlertUserCustom("Warning","Incorrect Username or Password");
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    break;
                case "N/C":
                    AlertUserCustom("Error","No connection to remote system");
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    break;
                default:
                    userLogged = usuario;
                    userLogged.setUserName(username);
                    userLogged.setPassword(password);
                    RodeoSingleton.getInstance(this).setUsername(userLogged.getUserName());
                    RodeoSingleton.getInstance(this).setName(userLogged.getFullName());
                    RodeoSingleton.getInstance(this).setEmail(userLogged.getMail());
                    RodeoSingleton.getInstance(this).setToken(userLogged.getToken());
                    RodeoSingleton.getInstance(this).setLoginDate(Utilities.getDateNow(timeZone));
                    RodeoSingleton.getInstance(this).setDurationToken(userLogged.getExpirationTimeToken());
                    RodeoSingleton.getInstance(this).setExpireDate(Utilities.addMinutesActualDate(timeZone, userLogged.getExpirationTimeToken()));
                    RodeoSingleton.getInstance(this).setUserid(userLogged.getUserCode());
                    RodeoSingleton.getInstance(this).setIdPermiso(userLogged.getUserPermissions());
                    RestServices.getInstance(this).setUserLogged(userLogged);
                    dataBaseConection.deleteUser(userLogged);
                    dataBaseConection.addUser(userLogged);
                    if (android.os.Build.MANUFACTURER.contains("Zebra Technologies") || android.os.Build.MANUFACTURER.contains("Motorola Solutions") ) {
                        try{
                            Intent intent1 = new Intent(MainActivity.this, ZebraEMDK_Activity.class);
                            etUserEmail.setText("");
                            etPassword.setText("");
                            startActivity(intent1);
                        } catch(Exception ex){
                            Log.d("Error", ex.toString());
                        }

                        break;
                    } else {
                        Intent intent1 = new Intent(MainActivity.this, NavigationActivity.class);
                        etUserEmail.setText("");
                        etPassword.setText("");
                        startActivity(intent1);
                        break;
                    }
            }
        } else {
            AlertUserCustom("Error","No connection to remote system");
            findViewById(R.id.progressBar).setVisibility(View.GONE);
        }
    }

    private class AsyncTaskVersion extends AsyncTask<String, Void, ResponsePost> {
        @Override
        protected ResponsePost doInBackground(String... urls) {
            return AskVersion();
        }
        @Override
        protected void onPostExecute(ResponsePost result) {
            String message = result.getMessage();
            if(message.equals("DL")) {
                ResultVersion(result);
            } else {
                Log.d("Version","Your app is up to date");
            }
        }
    }

    private ResponsePost AskVersion(){
        ResponsePost newVersion = new ResponsePost();
        if(RestServices.getInstance(this).isOnline()){
            newVersion = RestServices.getInstance(this).versionApk(versionCode, applicationID);
        } else {
            AlertUserCustom("Error","Check your Internet connection");
            findViewById(R.id.progressBar).setVisibility(View.GONE);
        }
        return newVersion;
    }

    private void ResultVersion(ResponsePost result){
        AskDownloadApk("Warning","A new version of the application has been found. \n Do you want to download it?");
        Log.d("Version","Your app is older");
    }

    private void downloadApk(){
        Log.d("Download","We downloaded the new APK");
    }

    private void AlertUserCustom(String type, String message){
        MessageDialog dialog = new MessageDialog();
        dialog.setListener(new Listener(){
            public void onReturnValue(String yourInput) {
                Log.d("AlertUserCustom","user response: "+yourInput);
            }
        });
        dialog.showMessageDialog(this,message,type,false);
    }

    private void AskDownloadApk(String type, String message) {
        MessageDialog dialog = new MessageDialog();
        dialog.setListener(new Listener(){
            public void onReturnValue(String yourInput) {
                Log.d("AskUserCustom","user response: "+yourInput);
                if(!yourInput.equals("CANCEL")){
                    downloadApk();
                }
            }
        });
        dialog.showAskDialog(this,message,type);
    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();
        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}