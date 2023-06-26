package com.janus.rodeo.Activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.janus.rodeo.R;
import com.janus.rodeo.Communication.IntentServiceResult;
import com.janus.rodeo.Communication.RestServices;
import com.janus.rodeo.Dialogs.Listener;
import com.janus.rodeo.Dialogs.MessageDialog;
//import com.janus.rodeo.Fragments.CoilLookup;
//import com.janus.rodeo.Fragments.DrumLookup;
//import com.janus.rodeo.Fragments.TreeNavigation;
import com.janus.rodeo.Helpers.RodeoSingleton;
import com.janus.rodeo.Helpers.Utilities;
import com.janus.rodeo.Models.User;
import com.janus.rodeo.Storage.DataBaseConection;
import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.Scanner;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NavigationActivity extends AppCompatActivity{
    private EMDKManager emdkManager = null;
    private BarcodeManager barcodeManager = null;
    private Scanner scanner = null;
    private TextView textViewTitle;
    private BottomNavigationView bottomNavigation;
    private String [] arrayParams = new String[]{"Read","Navigation","Navigation","0"};
    private List<String> FragmentListOpen = new ArrayList<>();
    private com.janus.rodeo.Models.Activity activitySelected;
    private boolean mKeyF4Pressing = false;
    private DataBaseConection dataBaseConection;
    private String timeZone;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String title = getResources().getString(R.string.applicationName);
        timeZone = this.getResources().getString(R.string.time_zone_default);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        View view =getSupportActionBar().getCustomView();
        ImageView imageButtonBack= (ImageView)view.findViewById(R.id.action_bar_back);
        textViewTitle = (TextView)view.findViewById(R.id.fragment_container_title);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backNavigation();
            }
        });
        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        fullScreenCall();
        dataBaseConection = DataBaseConection.get(this);
        Date date2 = Utilities.getLastDate(timeZone,-2);
        Date date3 = Utilities.getLastDate(timeZone,-3);
        String dateLast2 = Utilities.getStringDate("yyyy-MM-dd",date2);
        String dateLast3 = Utilities.getStringDate("yyyy-MM-dd",date3);
        Log.d("Inventarios","Borraremos los inventarios en Ejecucion con fecha menor a -->" + dateLast2);
        Log.d("Inventarios","Borraremos los inventarios Finalizados con fecha menor a -->" + dateLast3);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(IntentServiceResult syncStatusMessage) {
        String result = syncStatusMessage.getResultValue();
    }

    public void openFragment(Fragment fragment,String title) {
        textViewTitle.setText(title);
        findViewById(R.id.action_bar_back).setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String openFragment = getLastFragment();
        if(keyCode == KeyEvent.KEYCODE_F4) {
            if(openFragment == "RFID" ) {
                EventBus.getDefault().post(new IntentServiceResult("KEYCODE_F4_RD"));
            } else if(openFragment == "RFID_Inventory") {
                EventBus.getDefault().post(new IntentServiceResult("KEYCODE_F4_ID"));
            } else if(openFragment == "Asign_Assets_RFID") {
                EventBus.getDefault().post(new IntentServiceResult("KEYCODE_F4_AD"));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode,KeyEvent event){
        String openFragment = getLastFragment();
        if(keyCode == KeyEvent.KEYCODE_F4) {
            if(openFragment == "RFID") {
                EventBus.getDefault().post(new IntentServiceResult("KEYCODE_F4_RU"));
            } else if(openFragment == "RFID_Inventory") {
                EventBus.getDefault().post(new IntentServiceResult("KEYCODE_F4_IU"));
            } else if(openFragment == "Asign_Assets_RFID") {
                EventBus.getDefault().post(new IntentServiceResult("KEYCODE_F4_AU"));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("Navegation","No se permite navegar atras");
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) { FragmentListOpen.removeAll(FragmentListOpen);
                    /*switch (item.getItemId()) {
                        case R.id.navigation_coils:
                            deleteAllFragments();
                            deleteLastFragment();
                            openFragment(CoilLookup.newInstance(), "Coil Lookup");
                            return true;
                        case R.id.navigation_home:
                            deleteAllFragments();
                            deleteLastFragment();
                            return true;
                        case R.id.navigation_drums:
                            deleteAllFragments();
                            deleteLastFragment();
                            openFragment(DrumLookup.newInstance(), "Drum Lookup");
                            return true;
                        case R.id.navigation_manual_navegation:
                            deleteAllFragments();
                            deleteLastFragment();
                            openFragment(TreeNavigation.newInstance(null),"Navegacion");
                            return true;
                    }*/
                    return false;
                }
            };

    public void setTitleHeader(String title){
        textViewTitle.setText(title);
    }

    public void addFragmentToList(String fragmentName){
        if(!getLastFragment().equals(fragmentName) || FragmentListOpen.isEmpty()){
            FragmentListOpen.add(fragmentName);
        }
    }

    public String getLastFragment(){
        if(FragmentListOpen.isEmpty()) {
            return "";
        } else {
            return  FragmentListOpen.get(FragmentListOpen.size() - 1);
        }
    }

    public void deleteLastFragment(){
        if(!FragmentListOpen.isEmpty()) {
            FragmentListOpen.remove(FragmentListOpen.size() - 1);
        }
    }

    public void cleanNavigation(){
        FragmentListOpen.clear();
    }

    public void backNavigation(){
        deleteLastFragment();
        String fragment = getLastFragment();
        switch (fragment){
            case "Informacion":
                // openFragment(DisplayInfoActivity.newInstance(),fragment);
                return;
            case "Navegacion":
                // if(nodeSelected!=null){
                //  openFragment(TreeNavegation.newInstance(nodeSelected),fragment);
                // }else{
                //  openFragment(TreeNavegation.newInstance(null),fragment);
                // }
                return;
            case "Area":
                // openFragment(ZoneDisplay.newInstance(areaSelected,null),fragment);
                return;
            case "Asset":
                // openFragment(AssetDisplay.newInstance(assetSelected),fragment);
                return;
            case "QR":
                String[] arrayParmsQR = new String[]{"Read","Navigation","Navigation","0"};
                // openFragment(ReadQR.newInstance(arrayParmsQR,null),fragment);
                return;
            case "QR_Zebra":
                String[] arrayParmsQRZebra = new String[]{"Read","Navigation","Navigation","0"};
                // openFragment(ReadQR_ZebraKeyMapping.newInstance(),fragment);
                return;
            default:
                // openFragment(DisplayInfoActivity.newInstance(),fragment);
        }
    }

    public void deleteAllFragments(){
        List<Fragment> all_frags = getSupportFragmentManager().getFragments();
        if (all_frags.size() == 0) {
            super.onBackPressed();
        } else {
            for (Fragment frag : all_frags) {
                getSupportFragmentManager().beginTransaction().remove(frag).commit();
            }
        }
    }

    public void fullScreenCall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void logout(){
        User user = RestServices.getInstance(this).getUserLogged();
        dataBaseConection.deleteUser(user);
        finish();
    }

    public void setActivitySelected(com.janus.rodeo.Models.Activity activity){this.activitySelected = activity;}

    public void MessageAlert(String type,String Message){
        MessageDialog dialog = new MessageDialog();
        dialog.setListener(new Listener(){
            public void onReturnValue(String yourInput) {
                Log.d("MessageAlert","user response: "+yourInput);
            }
        });
        dialog.showMessageDialog(this,Message,type,false);
    }

    public void openCameraIntent(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1888);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1888 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String openFragment = getLastFragment();
            if(openFragment == "Activity"){
                RodeoSingleton.getInstance(this).setImageCaptured(photo);
                EventBus.getDefault().post(new IntentServiceResult("IMAGE_RECEIVED"));
            }
        }
    }

    public String refreshToken(){
        String newToken = RestServices.getInstance(this).refreshToken();
        return newToken;
    }

}