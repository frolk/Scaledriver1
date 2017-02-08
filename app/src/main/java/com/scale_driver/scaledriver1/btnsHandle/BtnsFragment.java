package com.scale_driver.scaledriver1.btnsHandle;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scale_driver.scaledriver1.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BtnsFragment extends Fragment {

    BtnListener mBtnListener;
    ArrayAdapter<String> mAdapter;
    CorrectDB correctDB;
    SQLiteDatabase db;
    CheckBox cbSetButns;
    CheckBox cbCheckButns;

    Map<Integer, String> hashMap = new HashMap<>();

    private static String[] mContacts = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    GridView gvMain;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mBtnListener = (BtnListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement btnListener");
        }

    }

    public interface BtnListener {

        public void CorrectBtnClicked(String s);

        public void SetUpBtnClicked(int btnId);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.btnfragment, null);

        btnNameUpdate();

        cbSetButns = (CheckBox) v.findViewById(R.id.cbSetBut);
        cbCheckButns = (CheckBox) v.findViewById(R.id.cbCheckBut);

        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item, R.id.tvText, mContacts);
        gvMain = (GridView) v.findViewById(R.id.gridView1);
        gvMain.setAdapter(mAdapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (cbSetButns.isChecked()) {
                    mBtnListener.SetUpBtnClicked(i);
                } else {
                    //Log.d("mLog", "position = " + i + ", btnvalue = " + getBtnValue(i + 1));
                    mBtnListener.CorrectBtnClicked(String.valueOf(getBtnValue(i+1)));
                }
            }
        });

        return v;
    }

    private int getBtnValue(int i) {
        db = correctDB.getWritableDatabase();
        int btnValue = 0;
        String[] columns = new String[]{CorrectDB.KEY_VALUE1};
        String selection = CorrectDB.KEY_BTNID + " LIKE ?";
        String[] selectionsArgs = new String[]{String.valueOf(i)};
        Cursor cursor = db.query(CorrectDB.TABLE_BTNS, columns, selection, selectionsArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int value1Index = cursor.getColumnIndex(CorrectDB.KEY_VALUE1);
            btnValue = cursor.getInt(value1Index);
        }
        cursor.close();
        correctDB.close();
        return btnValue;
    }

    private void btnNameUpdate(){
        correctDB = new CorrectDB(getActivity());
        db = correctDB.getWritableDatabase();

        String[] btnsName = new String[]{CorrectDB.KEY_BTNID, CorrectDB.KEY_NAME};
        Cursor cursor = db.query(CorrectDB.TABLE_BTNS, btnsName, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int btnIdIndex = cursor.getColumnIndex(CorrectDB.KEY_BTNID);
            int btnNameIndex = cursor.getColumnIndex(CorrectDB.KEY_NAME);

            do {
                hashMap.put(cursor.getInt(btnIdIndex), cursor.getString(btnNameIndex));
                Log.d("mLog", "btnId = " + cursor.getInt(btnIdIndex) + ", btnName = " + cursor.getString(btnNameIndex));
            } while (cursor.moveToNext());
        }
        Log.d("mLog", "0 rows in base");

        cursor.close();
        correctDB.close();

        Set<Map.Entry<Integer, String>> set = hashMap.entrySet();

        for (Map.Entry<Integer, String> item : set) {
            mContacts[item.getKey() - 1] = item.getValue();
            Log.d("mLog", "id = " + item.getKey() + ", value = " + item.getValue());
        }

    }

}