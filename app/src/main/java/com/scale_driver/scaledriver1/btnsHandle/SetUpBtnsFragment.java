package com.scale_driver.scaledriver1.btnsHandle;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scale_driver.scaledriver1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class SetUpBtnsFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "mLog";
    SeekBar seekBar;
    String currentBtn;
    String currentBtnName;
    String currentBtnValue1;
    Button btnInc, btnDec, btnSave, btnClose, btnClear;
    int correctvalue;
    TextView seekText;
    EditText etName;
    Map<Integer, String> hashMap = new HashMap<>();
    SetUpbtns msetUpbtns;


    public interface SetUpbtns {
        public void setUpbtnsCloseFrag();
        public void updateBtnFrag();
        public void CorrectBtnClicked(String s);

    }


    SQLiteDatabase db;
    CorrectDB correctDB;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            msetUpbtns = (SetUpbtns) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SetUpBtns");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correctDB = new CorrectDB(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.set_up_btns_fragment, null);
        etName = (EditText) v.findViewById(R.id.etBtnName);
        btnSave = (Button) v.findViewById(R.id.btnSaveData);
        btnSave.setOnClickListener(this);

        btnClear = (Button) v.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnClear.setOnLongClickListener(this);
        btnClose = (Button) v.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        btnInc = (Button) v.findViewById(R.id.btnInc);
        btnInc.setOnClickListener(this);

        btnDec = (Button) v.findViewById(R.id.btnDec);
        btnDec.setOnClickListener(this);

        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        seekText = (TextView) v.findViewById(R.id.seekText);

        seekText.setText(String.valueOf(correctvalue));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                correctvalue = i;
                seekText.setText(String.valueOf(correctvalue));
                msetUpbtns.CorrectBtnClicked("$" + String.valueOf(i) + "&");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        currentBtn = String.valueOf(getArguments().getInt(CorrectDB.KEY_BTNID));
        currentBtnName = getArguments().getString(CorrectDB.KEY_NAME);
//        correctvalue = getArguments().getInt(CorrectDB.KEY_VALUE1);
        currentBtnValue1 = String.valueOf(getArguments().getInt(CorrectDB.KEY_VALUE1));
        Log.d(TAG, "From SetUpbtns: btnId = " + currentBtn + ", btnName = " + currentBtnName + ", btnValue1 = " + currentBtnValue1);



        return v;

    }


    @Override
    public void onResume() {
        super.onResume();
        etName.setText(currentBtnName);
        seekBar.setProgress(Integer.parseInt(currentBtnValue1));
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.btnClear) {
            // db = correctDB.getWritableDatabase();
            //db.execSQL("DROP TABLE IF EXISTS " + CorrectDB.TABLE_BTNS);
            getActivity().deleteDatabase(CorrectDB.DATABASE_NAME);
            Log.d(TAG, "Database deleted");
        }
        return false;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnInc:
                changeSeekValue(correctvalue++);

                break;

            case R.id.btnDec:
                changeSeekValue(correctvalue--);
                break;

            case R.id.btnSaveData:
                if (isIdExist(currentBtn)) {
                    updateData();
                } else
                    addData();
                msetUpbtns.updateBtnFrag();
                msetUpbtns.setUpbtnsCloseFrag();
                break;

            case R.id.btnClose:
                 msetUpbtns.setUpbtnsCloseFrag();
                break;

            case R.id.btnClear:
                clearBase();
                msetUpbtns.updateBtnFrag();
                break;

        }
    }

    private void clearBase() {
        db = correctDB.getWritableDatabase();
        int clearData = db.delete(CorrectDB.TABLE_BTNS, null, null);
        Log.d(TAG, "delete rows count = " + clearData);
        Toast.makeText(getActivity(), "Clear DataBase", Toast.LENGTH_SHORT).show();
        correctDB.close();

    }

    private void changeSeekValue(int value) {
        seekText.setText(String.valueOf(value));
        seekBar.setProgress(value);
    }

    private void updateData() {
        db = correctDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CorrectDB.KEY_BTNID, currentBtn);
        contentValues.put(CorrectDB.KEY_NAME, etName.getText().toString());
        contentValues.put(CorrectDB.KEY_VALUE1, seekBar.getProgress());
        String selection = CorrectDB.KEY_BTNID + " LIKE ?";
        String[] selectionArgs = {currentBtn};

        int updCount = db.update(CorrectDB.TABLE_BTNS, contentValues, selection, selectionArgs);
        Log.d(TAG, "update rows count = " + updCount);
        correctDB.close();
    }

    private void addData() {
        db = correctDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CorrectDB.KEY_BTNID, currentBtn);
        contentValues.put(CorrectDB.KEY_NAME, etName.getText().toString());
        contentValues.put(CorrectDB.KEY_VALUE1, seekBar.getProgress());

        db.insert(CorrectDB.TABLE_BTNS, null, contentValues);
        Toast.makeText(getActivity(), "Saved data", Toast.LENGTH_SHORT).show();
        correctDB.close();
    }

    private Boolean isIdExist(String currentBtn) {
        db = correctDB.getWritableDatabase();
        String selection = CorrectDB.KEY_BTNID + " LIKE ?";
        String[] selectionArgs = new String[]{currentBtn};
        Cursor cursor = db.query(CorrectDB.TABLE_BTNS, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        correctDB.close();

        return true;

    }

    private void readData() {
        db = correctDB.getWritableDatabase();
        Cursor cursor = db.query(CorrectDB.TABLE_BTNS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(CorrectDB.KEY_ID);
            int btnIdIndex = cursor.getColumnIndex(CorrectDB.KEY_BTNID);
            int nameIndex = cursor.getColumnIndex(CorrectDB.KEY_NAME);
            int value1Index = cursor.getColumnIndex(CorrectDB.KEY_VALUE1);

            do {
                hashMap.put(cursor.getInt(idIndex), cursor.getString(nameIndex));
                Log.d(TAG, "id = " + cursor.getInt(idIndex) + ", bntId = " + cursor.getInt(btnIdIndex)
                        + ", name = " + cursor.getString(nameIndex) + ", value = " + cursor.getInt(value1Index));
            } while (cursor.moveToNext());

        } else Log.d(TAG, "0 rows");

        cursor.close();
        correctDB.close();
    }

}