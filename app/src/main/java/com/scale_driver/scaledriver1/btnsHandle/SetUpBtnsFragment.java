package com.scale_driver.scaledriver1.btnsHandle;

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


public class SetUpBtnsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "mLog";
    SeekBar seekBar;
    String currentBtn = "1";
    Button btnInc, btnDec, btnSave, btnClose, btnClear;
    int correctvalue = 0;
    TextView seekText;
    EditText etName;
    Map<Integer,String> hashMap = new HashMap<>();


    SQLiteDatabase db;
    CorrectDB correctDB;

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

        btnClose = (Button) v.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);


        btnInc = (Button) v.findViewById(R.id.btnInc);
        btnInc.setOnClickListener(this);

        btnDec = (Button) v.findViewById(R.id.btnDec);
        btnDec.setOnClickListener(this);

        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        correctvalue = seekBar.getProgress();
        seekText = (TextView) v.findViewById(R.id.seekText);
        seekText.setText(String.valueOf(correctvalue));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                correctvalue = i;
                seekText.setText(String.valueOf(correctvalue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return v;

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
                saveData();
                // addData();
                break;

            case R.id.btnClose:
                readData();
                break;

            case R.id.btnClear:
                clearBase();
                break;

        }
    }

    private void clearBase() {
        int clearData = db.delete(CorrectDB.TABLE_BTNS, null, null);
        Log.d(TAG, "delete rows conut = " + clearData);
        Toast.makeText(getActivity(), "Clear DataBase", Toast.LENGTH_SHORT).show();

    }

    private void changeSeekValue(int value) {
        seekText.setText(String.valueOf(value));
        seekBar.setProgress(value);
    }

    private void saveData() {
        db = correctDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CorrectDB.KEY_NAME, etName.getText().toString());
        contentValues.put(CorrectDB.KEY_VALUE1, seekBar.getProgress());
        String selection = CorrectDB.KEY_ID + " LIKE ?";
        String[] selectionArgs = {currentBtn};

        int updCount = db.update(CorrectDB.TABLE_BTNS, contentValues, selection, selectionArgs );
        Log.d(TAG, "update rows count = " + updCount);
        //db.insert(CorrectDB.TABLE_BTNS, null, contentValues);
        Toast.makeText(getActivity(), "Saved data", Toast.LENGTH_SHORT).show();
        correctDB.close();
    }

    private void addData() {
        db = correctDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CorrectDB.KEY_NAME, etName.getText().toString());
        contentValues.put(CorrectDB.KEY_VALUE1, seekBar.getProgress());

        db.insert(CorrectDB.TABLE_BTNS, null, contentValues);
        Toast.makeText(getActivity(), "Saved data", Toast.LENGTH_SHORT).show();
        correctDB.close();
    }




    private void readData(){
        db = correctDB.getWritableDatabase();
        Cursor cursor = db.query(CorrectDB.TABLE_BTNS, null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(CorrectDB.KEY_ID);
            int nameIndex = cursor.getColumnIndex(CorrectDB.KEY_NAME);
            int value1Index = cursor.getColumnIndex(CorrectDB.KEY_VALUE1);

            do {
                hashMap.put(cursor.getInt(idIndex),cursor.getString(nameIndex));
//                Log.d(TAG, "id = " + cursor.getInt(idIndex)
//                        + ", name = " + cursor.getString(nameIndex) + ", value = " + cursor.getInt(value1Index));
            } while (cursor.moveToNext());

        } else Log.d (TAG, "0 rows");


        Set<Map.Entry<Integer, String>> set = hashMap.entrySet();

        for (Map.Entry<Integer, String> row : set){
            Log.d(TAG, row.getKey() + ": " + row.getValue());
        }

        cursor.close();

    }

}