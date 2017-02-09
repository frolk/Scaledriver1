package com.scale_driver.scaledriver1.btnsHandle;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;

import com.scale_driver.scaledriver1.R;

import java.util.Map;
import java.util.Set;


public class BtnsFragment extends Fragment {

    public static final int BTN_NUMS = 10;
    public static final String TAG = "mLog";

    BtnListener mBtnListener;
    ArrayAdapter<String> mAdapter;
    CorrectDB correctDB;
    SQLiteDatabase db;
    CheckBox cbSetButns;
    CheckBox cbCheckButns;
    SharedPreferences sp;
    int btnNums;

    SparseArray<String> btnNamesArray = new SparseArray<String>();

    private static String[] mContacts;
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

        public void SetUpBtnClicked(int btnId, String item, int btnValue1);
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
        Log.d(TAG, "butNums = " + btnNums);

        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item, R.id.tvText, mContacts);
        gvMain = (GridView) v.findViewById(R.id.gridView1);
        gvMain.setAdapter(mAdapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (cbSetButns.isChecked()) {
                    mBtnListener.SetUpBtnClicked(i + 1, mAdapter.getItem(i), getBtnValue(i + 1));
                    Log.d("mLog", "number of items: " + mAdapter.getCount());
                } else {
                    //Log.d("mLog", "position = " + i + ", btnvalue = " + getBtnValue(i + 1));
                    mBtnListener.CorrectBtnClicked(String.valueOf(getBtnValue(i + 1)));
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

    private void btnNameUpdate() {

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        btnNums = Integer.parseInt(sp.getString("btnNums", "no data"));
        mContacts = new String[btnNums];

        correctDB = new CorrectDB(getActivity());
        db = correctDB.getWritableDatabase();

        String[] btnsName = new String[]{CorrectDB.KEY_BTNID, CorrectDB.KEY_NAME};
        Cursor cursor = db.query(CorrectDB.TABLE_BTNS, btnsName, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int btnIdIndex = cursor.getColumnIndex(CorrectDB.KEY_BTNID);
            int btnNameIndex = cursor.getColumnIndex(CorrectDB.KEY_NAME);

            do {
                btnNamesArray.put(cursor.getInt(btnIdIndex), cursor.getString(btnNameIndex));
            } while (cursor.moveToNext());
        }

        cursor.close();
        correctDB.close();


        for (int i = 0; i < btnNums; i++)
            mContacts[i] = "";

        for (int i = 0; i < btnNamesArray.size(); i++) {
            int key = btnNamesArray.keyAt(i) - 1;
            if (i < btnNums) {
                mContacts[key] = btnNamesArray.valueAt(i);
            }
        }
    }

}