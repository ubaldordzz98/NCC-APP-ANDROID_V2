package com.janus.rodeo.Fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.janus.rodeo.R;
import com.janus.rodeo.Activities.NavigationActivity;
import com.janus.rodeo.Activities.ZebraEMDK_Activity;
import com.janus.rodeo.Adapters.ReleaseAdapter;
import com.janus.rodeo.Communication.RestServices;
import com.janus.rodeo.Dialogs.Listener;
import com.janus.rodeo.Dialogs.MessageDialog;
import com.janus.rodeo.Models.ShRelease;
import com.janus.rodeo.Storage.DataBaseConection;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Home extends Fragment {
    private ViewGroup root;
    private SearchView _ui_search_release;
    private ListView _ui_lst_releases;
    private DataBaseConection dataBaseConection;
    private  String _release_searched;
    private ReleaseAdapter adapter;
    private long lastSearchTime;
    private List<ShRelease> ReleaseList = new ArrayList<>();
    private ProgressBar _pg_list_releases;

    public Home() { }

    @Override
    public void onStop() {
        super.onStop();
    }

    public static Home newInstance() {
        ZebraEMDK_Activity.isInReleaseScreen=true;
        ZebraEMDK_Activity.isInPhysicalInventory=false;
        Home fragment = new Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ZebraEMDK_Activity.isInReleaseScreen=true;
        ZebraEMDK_Activity.isInPhysicalInventory=false;
        root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        _pg_list_releases= (ProgressBar) root.findViewById(R.id.pb_lst_releases);
        _pg_list_releases.setVisibility(View.VISIBLE);
        _ui_search_release=(SearchView)root.findViewById(R.id.release_search_bar);
        _ui_lst_releases=(ListView)root.findViewById(R.id.lst_releases);
        _ui_search_release.setActivated(true);
        _ui_search_release.setQueryHint("Type the release number");
        _ui_search_release.onActionViewExpanded();
        _ui_search_release.setIconified(false);
        _ui_search_release.clearFocus();

        if(android.os.Build.MANUFACTURER.contains("Zebra Technologies") || android.os.Build.MANUFACTURER.contains("Motorola Solutions") ) {
            ((ZebraEMDK_Activity)getActivity()).addFragmentToList("Home");
        } else {
            ((NavigationActivity)getActivity()).addFragmentToList("Home");
            ((NavigationActivity)getActivity()).fullScreenCall();
        }


        FragmentActivity frg;
        frg=getActivity();

        _ui_search_release.setInputType(InputType.TYPE_CLASS_NUMBER);
        _ui_search_release.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                long actualSearchTime = (Calendar.getInstance()).getTimeInMillis();
                if (actualSearchTime > lastSearchTime + 1000) {
                    lastSearchTime=actualSearchTime;
                    callSearch(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    _release_searched="";
                    long actualSearchTime = (Calendar.getInstance()).getTimeInMillis();
                    if (actualSearchTime > lastSearchTime + 1000) {
                        lastSearchTime=actualSearchTime;
                        callSearch("");
                    }
                    return true;
                }
                return true;
            }
            public void callSearch(String query) {
                _release_searched=query;
                new AsyncTaskRequestRlSchedule().execute();
            }
        });

        int searchCloseButtonId = _ui_search_release.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);

        ImageView clearButton = (ImageView) this._ui_search_release.findViewById(searchCloseButtonId);
        clearButton.setOnClickListener(v -> {
            if(_ui_search_release.getQuery().length() == 0) {
                _ui_search_release.setIconified(true);
            } else {
                _ui_search_release.setQuery("", false);
                _release_searched = "";
                new AsyncTaskRequestRlSchedule().execute();
            }
        });

        _ui_lst_releases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(ReleaseList.size()>0) {
                    ShRelease rlSelected = ReleaseList.get(position);
                    String rl= "Release "+rlSelected.getReleaseNumber();
                    if(Build.MANUFACTURER.contains("Zebra Technologies") || Build.MANUFACTURER.contains("Motorola Solutions") ) {
                        //((ZebraEMDK_Activity) getActivity()).openFragment(ReleaseInformation.newInstance(rlSelected.getReleaseNumber(), null), rl);
                    } else {
                        //((NavigationActivity) getActivity()).openFragment(ReleaseInformation.newInstance(rlSelected.getReleaseNumber(),null), rl);
                    }
                }
            }
        });

        new AsyncTaskRequestRlSchedule().execute();
        return root;
    }

    public class AsyncTaskRequestRlSchedule extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) { return getReleases();}
        @Override
        protected void onPostExecute(String result) { setReleasesInfo();}
    }

    public String getReleases() {
        try {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    _pg_list_releases.setVisibility(View.VISIBLE);
                }
            });

            if(_release_searched==null) {
                ReleaseList= RestServices.getInstance(getActivity()).getReleasesList("");
            } else {
                ReleaseList= RestServices.getInstance(getActivity()).getReleasesList(_release_searched);
            }

            if(ReleaseList==null) {
                MessageAlert("Error", "Connection Error");
            } else {
                if(ReleaseList.size()==0) {
                    MessageAlert("Error", "No Results Found");
                }
            }
            return  "OK";
        } catch (Exception ex) {
            return  "OK";
        }
    }

    public void setReleasesInfo(){
        try {
            if(ReleaseList!=null) {
                ListAdapter a= _ui_lst_releases.getAdapter();
                if(_ui_lst_releases.getAdapter()!=null) {
                    adapter = new ReleaseAdapter(getActivity(),R.layout.list_history,ReleaseList);
                    _ui_lst_releases.setAdapter(null);
                    _ui_lst_releases.setAdapter(adapter);
                } else {
                    adapter = new ReleaseAdapter(getActivity(),R.layout.list_history,ReleaseList);
                    _ui_lst_releases.setAdapter(adapter);
                }
            }
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    _pg_list_releases.setVisibility(View.INVISIBLE);
                }
            });
        } catch (Exception ex) {
            return;
        }
    }

    private void MessageAlert(String type,String Message){
        ZebraEMDK_Activity.flagModal=true;
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                MessageDialog dialog = new MessageDialog();
                dialog.setListener(new Listener(){
                    public void onReturnValue(String yourInput) {
                        ZebraEMDK_Activity.flagModal=false;
                        return;
                    }
                });
                dialog.showMessageDialog(getActivity(),Message,type,false);
            }
        });

    }

}