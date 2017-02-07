package com.scale_driver.scaledriver1.btnsHandle;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scale_driver.scaledriver1.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BtnsFragment extends Fragment {

  //  BtnListener mCallBtnBack;
    ArrayAdapter<String> mAdapter;
    CorrectDB correctDB;
    SQLiteDatabase db;

    Map<Integer, String> hashMap = new HashMap<>();


    private static String[] mContacts = {"","","","","","","","","","","","","","","",""};
    GridView gvMain;

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        try {
//            mCallBtnBack = (BtnListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString() + " must implement btnListener");
//        }
//
//    }
//    public interface BtnListener {
//
//        public void CorrectBtnClicked(String s);
//        public void SetUpBtnClicked(String s);
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correctDB = new CorrectDB(getActivity());
        hashMap.put(1, "-101");
        hashMap.put(2, "-202");
        hashMap.put(3, "-303");
        hashMap.put(4, "-504");
        hashMap.put(5, "105");
        hashMap.put(6, "205");
        hashMap.put(7, "305");
        hashMap.put(8, "505");
        hashMap.put(9, "606");
        hashMap.put(9, "707");
        hashMap.put(10, "808");

        Set<Map.Entry<Integer,String>> set = hashMap.entrySet();

        for(Map.Entry<Integer,String> item : set){
            mContacts[item.getKey()-1] = item.getValue();
             Log.d("mLog", "id = " + item.getKey() + ", value = " + item.getValue());
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.btnfragment, null);

        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item, R.id.tvText, mContacts);
        gvMain = (GridView) v.findViewById(R.id.gridView1);
        gvMain.setAdapter(mAdapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Выбрано: " + mAdapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        gvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Долгое нажатие на: " + mAdapter.getItem(i), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return v;

    }

}