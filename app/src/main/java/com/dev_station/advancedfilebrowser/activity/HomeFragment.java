package com.dev_station.advancedfilebrowser.activity;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Collections;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Environment;

import android.Manifest;
/*import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;

import android.support.annotation.NonNull;

import android.util.Log;
import 	android.support.v4.app.ActivityCompat;*/
import android.support.v7.app.AppCompatActivity;

import com.dev_station.advancedfilebrowser.R;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import android.widget.Toast;


public class HomeFragment extends Fragment {
    TextView tv;
    private String path;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                path = "/";
                if (getActivity().getIntent().hasExtra("path")) {
                    path = getActivity().getIntent().getStringExtra("path");
                }
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(path);

                // Read all files sorted into the values-array
                List values = new ArrayList();
                File dir = new File(path);
                dir=Environment.getExternalStorageDirectory();

                if (!dir.canRead()) {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(((AppCompatActivity)getActivity()).getSupportActionBar().getTitle() + " (inaccessible)");
                }
                String[] list = dir.list();
                if (list != null) {
                    for (String file : list) {
                        if (!file.startsWith(".")) {
                            values.add(file);
                        }
                    }
                }
                Collections.sort(values);

                // Put the data into the list

                ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_2, android.R.id.text1, values);
                ListView lv = (ListView)rootView.findViewById(R.id.lstFiles);
                lv.setAdapter(adapter);
                //setListAdapter(adapter);
                // </code>
                // Inflate the layout for this fragment

                Toast.makeText((AppCompatActivity)getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText((AppCompatActivity)getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };


        new TedPermission((AppCompatActivity)getActivity())
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("setting")
                .check();
        tv=(TextView) rootView.findViewById(R.id.label);
        tv.setText("Working!");
        //File[] fs=checkPremission();
        //tv.setText(""+fs.length);
        //new <code>
        // Use the current directory as title
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
