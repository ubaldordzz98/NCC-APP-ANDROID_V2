package com.janus.rodeo.Activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SupportMenuInflater;
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
import com.janus.rodeo.Fragments.Home;
//import com.janus.rodeo.Fragments.PhysicalInventoryMenu;
//import com.janus.rodeo.Fragments.PhysicalInventory;
//import com.janus.rodeo.Fragments.ReleaseInformation;
//import com.janus.rodeo.Fragments.UpdateLocation;
//import com.janus.rodeo.Fragments.UpdateLocationDrum;
//import com.janus.rodeo.Fragments.PhysicalInventoryMenu;
import com.janus.rodeo.Models.GeneralResponse;
import com.janus.rodeo.Models.User;
import com.janus.rodeo.Storage.DataBaseConection;
import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKResults;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.ScannerConfig;
import com.symbol.emdk.barcode.ScannerException;
import com.symbol.emdk.barcode.ScannerResults;
import com.symbol.emdk.barcode.StatusData;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;


public class ZebraEMDK_Activity extends AppCompatActivity implements EMDKManager.EMDKListener, Scanner.StatusListener, Scanner.DataListener {
    private String codeScanned;
    private EMDKManager emdkManager = null;
    private BarcodeManager barcodeManager = null;
    private Scanner scanner = null;
    private boolean msgTriggerZebra;
    private boolean flagConnection;
    private String timeZone;
    private List<String> FragmentListOpen = new ArrayList<>();
    private String [] arrayParams = new String[]{"Read","Navigation","Navigation","0"};
    private TextView textViewTitle;
    private BottomNavigationView bottomNavigation;
    private View decorView;
    private DataBaseConection dataBaseConection;
    public static boolean flagModal;
    public static boolean isInReleaseScreen;
    public static boolean isInPhysicalInventory;
    
    public ZebraEMDK_Activity newInstance(){
        ZebraEMDK_Activity fragment= new ZebraEMDK_Activity();
        return  ZebraEMDK_Activity.this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        @SuppressLint("RestrictedApi") MenuInflater menuInflater = new SupportMenuInflater(this);
        menuInflater.inflate(R.menu.tree_dots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Log Out")){
            AskLogOut("Warning", "Do you Want to log out?");
        }
        
        if(item.getTitle().equals(("Help"))) {
            new AsyncTaskHelpInfo().execute();
        }
        return true;
    }

    private void ShowHelpInfo(String type, String message) {
        flagModal = true;
        this.runOnUiThread(new Runnable() {
            public void run() {
                MessageDialog dialog = new MessageDialog();
                dialog.setListener(new Listener() {
                    public void onReturnValue(String yourInput) {
                        Log.d("AskUserCustom","user response: "+yourInput);
                        if(yourInput.equals("Ok")) {
                            flagModal = false;
                            return;
                        } else {
                            flagModal = false;
                        }
                    }
                });
                dialog.showAskDialogInfo(newInstance(),message,type);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION ;
    }

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
        MenuItem log_out = (MenuItem)view.findViewById(R.id.action_log_out);
        textViewTitle = (TextView)view.findViewById(R.id.fragment_container_title);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backNavigation();
            }
        });

        bottomNavigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        decorView=getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility==0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        openFragment(Home.newInstance(),"Shipping");
        dataBaseConection = DataBaseConection.get(this);

        msgTriggerZebra = true;
        EMDKResults results = EMDKManager.getEMDKManager(getApplicationContext(), this);
        if (results.statusCode!=   EMDKResults.STATUS_CODE.SUCCESS) {
            updateStatus("EMDKManager object request failed!");
            return;
        } else {
            updateStatus("EMDKManager object initialization is   in   progress.......");
        }
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
        codeScanned = result;
        updateStatus("Scanning...");
    }

    public void fullScreenCall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void openFragment(Fragment fragment, String title) {
        textViewTitle.setText(title);
        findViewById(R.id.action_bar_back).setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void backNavigation(){ }

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

    public void addFragmentToList(String fragmentName){
        if(!getLastFragment().equals(fragmentName) || FragmentListOpen.isEmpty()) {
            FragmentListOpen.add(fragmentName);
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) { FragmentListOpen.removeAll(FragmentListOpen);
            switch (item.getItemId()) {
                case R.id.navigation_coils:
                    ZebraEMDK_Activity.isInReleaseScreen=false;
                    ZebraEMDK_Activity.isInPhysicalInventory=false;
                    deleteAllFragments();
                    deleteLastFragment();
                    //openFragment(CoilLookup.newInstance(), "Coil Lookup");
                    return true;
                case R.id.navigation_home:
                    ZebraEMDK_Activity.isInReleaseScreen=true;
                    ZebraEMDK_Activity.isInPhysicalInventory=false;
                    deleteAllFragments();
                    deleteLastFragment();
                    openFragment(Home.newInstance(), "Shipping");
                    return true;
                case R.id.navigation_drums:
                    ZebraEMDK_Activity.isInReleaseScreen=false;
                    ZebraEMDK_Activity.isInPhysicalInventory=false;
                    deleteAllFragments();
                    deleteLastFragment();
                    //openFragment(DrumLookup.newInstance(), "Drum Lookup");
                    return true;
                case R.id.navigation_inventory:
                    ZebraEMDK_Activity.isInReleaseScreen=false;
                    ZebraEMDK_Activity.isInPhysicalInventory=false;
                    deleteAllFragments();
                    deleteLastFragment();
                    //openFragment(PhysicalInventoryMenu.newInstance(), "Physical Inventory");
                    return true;
            }
            return false;
        }
    };

    public void logout(){
        User user = RestServices.getInstance(this).getUserLogged();
        dataBaseConection.deleteUser(user);
        finish();
    }

    private void MessageAlert(String type,String Message){
        ZebraEMDK_Activity.flagModal = true;
        runOnUiThread(new Runnable() {
            public void run() {
                MessageDialog dialog = new MessageDialog();
                dialog.setListener(new Listener(){
                    public void onReturnValue(String yourInput) {
                        flagModal = false;
                        Log.d("MessageAlert","user response: "+yourInput);
                    }
                });
                dialog.showMessageDialog(newInstance(),Message,type,false);
            }
        });
    }

    private void initBarcodeManager() {
        barcodeManager = (BarcodeManager) emdkManager.getInstance(EMDKManager.FEATURE_TYPE.BARCODE);
        if (barcodeManager == null) {
            Toast.makeText(this, "Barcode scanning is not supported.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initScanner() {
        if (scanner == null) {
            scanner = barcodeManager.getDevice(BarcodeManager.DeviceIdentifier.DEFAULT);
            if (scanner != null) {
                scanner.addDataListener(this);
                scanner.addStatusListener(this);
                scanner.triggerType = Scanner.TriggerType.HARD;
                try {
                    scanner.enable();
                } catch (ScannerException e) {
                    updateStatus(e.getMessage());
                    deInitScanner();
                }
            } else {
                updateStatus("Failed to   initialize the scanner device.");
            }
        }
    }

    private void deInitScanner() {
        if (scanner != null) {
            try {
                scanner.release();
            } catch (Exception e) {
                updateStatus(e.getMessage());
            }
            scanner = null;
        }
    }

    @Override
    public void onOpened(EMDKManager emdkManager) {
        this.emdkManager = emdkManager;
        initBarcodeManager();
        initScanner();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (emdkManager != null) {
            emdkManager.release();
            emdkManager = null;
        }
    }

    @Override
    public void onClosed() {
        if (emdkManager != null) {
            emdkManager.release();
            emdkManager = null;
        }
        updateStatus("EMDK closed unexpectedly! Please close and restart the application.");
    }

    public class AsyncTaskCheckCoilInRelease extends AsyncTask<String, Void, String> {
        @Override
        protected  String doInBackground(String... urls) {return CheckIfCoilIsInRelease();}
        @Override
        protected void onPostExecute(String result) {  }
    }

    public class AsyncTaskHelpInfo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {return GetInfoHelp();}
        @Override
        protected void onPostExecute(String result) {  }
    }

    public String GetInfoHelp(){
        String fragment = getLastFragment();
        GeneralResponse resp= RestServices.getInstance(this).getHelpInfo(fragment);
        if(resp!=null){
            if(resp.getMessage() == null || resp.getMessage().length()==0){ }
            else{ ShowHelpInfo("OK", resp.getMessage()); }
        } else {
            MessageAlert("Error", "Connection Error");
        }
        return "OK";
    }

    public String CheckIfCoilIsInRelease(){
        this.runOnUiThread(new Runnable() {
            public void run() {
                deleteAllFragments();
                deleteLastFragment();
                //openFragment(UpdateLocation.newInstance(codeScanned), "Update Location");
            }
        });
        return "OK";
    }

    private void AskUser(String type, String message, GeneralResponse response) {
        flagModal = true;
        this.runOnUiThread(new Runnable() {
            public void run() {
                MessageDialog dialog = new MessageDialog();
                dialog.setListener(new Listener(){
                    public void onReturnValue(String yourInput) {
                        Log.d("AskUserCustom","user response: "+yourInput);
                        if(yourInput.equals("Ok")){
                            flagModal = false;
                          //  openFragment(ReleaseInformation.newInstance(null, codeScanned), "Release "+response.getCoil().getShReleaseNumber());
                        } else{
                            flagModal = false;
                            deleteAllFragments();
                            deleteLastFragment();
                           // openFragment(UpdateLocation.newInstance(codeScanned), "Update Location");
                        }
                    }
                });
                dialog.showAskDialog(newInstance(),message,type);
            }
        });
    }

    private void AskLogOut(String type, String message) {
        flagModal = true;
        this.runOnUiThread(new Runnable() {
            public void run() {
                MessageDialog dialog = new MessageDialog();
                dialog.setListener(new Listener(){
                    public void onReturnValue(String yourInput) {
                        Log.d("AskUserCustom","user response: "+yourInput);
                        if(yourInput.equals("Ok")){
                            flagModal = false;
                            logout();
                        } else {
                            flagModal = false;
                        }
                    }
                });
                dialog.showAskDialog(newInstance(),message,type);
            }
        });
    }

    private void updateStatus(String status) {
        if(status.equals("Scanning...")) {
            if(flagModal == false){
                if(codeScanned!=null){
                    msgTriggerZebra=false;
                    codeScanned = codeScanned.toUpperCase();
                    if(isInReleaseScreen==false && isInPhysicalInventory==false){
                        if(codeScanned.length()==16||codeScanned.length()==14){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(isInReleaseScreen==false){
                                        new AsyncTaskCheckCoilInRelease().execute();
                                    }
                                }
                            });
                        } else if (codeScanned.length()==10 || codeScanned.length()==6 || codeScanned.length()==21){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    deleteAllFragments();
                                    deleteLastFragment();
                                    //openFragment(UpdateLocationDrum.newInstance(codeScanned), "Update Location");
                                }
                            });
                        } else{
                            MessageAlert("Error", "Barcode or QR not found in the system");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onData(ScanDataCollection scanDataCollection) {
        String dataStr = "";
        if ((scanDataCollection != null) &&   (scanDataCollection.getResult() == ScannerResults.SUCCESS)) {
            ArrayList<ScanDataCollection.ScanData> scanData =  scanDataCollection.getScanData();
            for (ScanDataCollection.ScanData data :  scanData) {
                String barcodeData =  data.getData();
                ScanDataCollection.LabelType labelType = data.getLabelType();
                dataStr =  barcodeData /*+ "  " +  labelType*/;
            }
            updateData(dataStr);
        }
    }

    private int dataLength = 0;

    private void updateData(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new IntentServiceResult(result));
            }
        });
    }

    @Override
    public void onStatus(StatusData statusData) {
        StatusData.ScannerStates state =  statusData.getState();
        String statusStr = "";

        switch (state) {
            case IDLE:
                statusStr = statusData.getFriendlyName()+" is   enabled and idle...";
                setConfig();
                try {
                    scanner.read();
                } catch (ScannerException e)   {
                }
                break;
            case WAITING:
                statusStr = "Scanner is waiting for trigger press...";
                break;
            case SCANNING:
                statusStr = "Scanning...";
                break;
            case DISABLED:
                statusStr = statusData.getFriendlyName()+" is disabled.";
                break;
            case ERROR:
                statusStr = "An error has occurred.";
                break;
            default:
                break;
        }
        updateStatus(statusStr);
    }

    private void setConfig() {
        if (scanner != null) {try {
            ScannerConfig config = scanner.getConfig();
            config.decoderParams.dataMatrix.enabled = true;
            if (config.isParamSupported("config.scanParams.decodeHapticFeedback")) {
                config.scanParams.decodeHapticFeedback = true;
            }
            scanner.setConfig(config);
        } catch (ScannerException e)   {
            updateStatus(e.getMessage());
        }
        }
    }

}