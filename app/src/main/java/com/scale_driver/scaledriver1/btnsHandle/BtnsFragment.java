package com.scale_driver.scaledriver1.btnsHandle;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CorrectionInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scale_driver.scaledriver1.MainActivity;
import com.scale_driver.scaledriver1.R;


public class BtnsFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    btnListener mCallBtnBack;
    int correctValue = 0;
    TextView seekText;
    EditText etBtnName;
    SeekBar seekBar;
    LinearLayout setUpbut;
    SQLiteDatabase db;
    Button btnInc, btnDec, saveValue, close;

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16;
    String currentBtn = "btn1";

    CorrectDB correctDb;

    final private static String TAG = "mLog";
    //public static String btnPrefValues = "com.scale_driver.btnValues";

    //SharedPreferences btnValues;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        correctDb = new CorrectDB(getActivity());
       // btnValues = getActivity().getSharedPreferences(btnPrefValues, Context.MODE_PRIVATE);
//        SharedPreferences.Editor btnEditor = btnValues.edit();
//        btnEditor.putString("btn1", "$381");
//        btnEditor.commit();


    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                setUpbut.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "longclick", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    public interface btnListener {

        public void CorrectBtnClicked(String s);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBtnBack = (btnListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement btnListener");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {

        View v = inflater.inflate(R.layout.btns_fragment,container, false);

        etBtnName = (EditText) v.findViewById(R.id.etBtnName);
        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        btnInc = (Button) v.findViewById(R.id.btnInc);
        btnDec = (Button) v.findViewById(R.id.btnDec);
        setUpbut = (LinearLayout) v.findViewById(R.id.setupBut);
        btnInc.setOnClickListener(this);
        btnDec.setOnClickListener(this);
        saveValue = (Button) v.findViewById(R.id.btnSaveData);
        close = (Button) v.findViewById(R.id.btnClose);
        saveValue.setOnClickListener(this);
        close.setOnClickListener(this);


        btn1 = (Button) v.findViewById(R.id.btn1);
        btn2 = (Button) v.findViewById(R.id.btn2);
        btn3 = (Button) v.findViewById(R.id.btn3);
        btn4 = (Button) v.findViewById(R.id.btn4);
        btn5 = (Button) v.findViewById(R.id.btn5);
        btn6 = (Button) v.findViewById(R.id.btn6);
        btn7 = (Button) v.findViewById(R.id.btn7);
        btn8 = (Button) v.findViewById(R.id.btn8);
        btn9 = (Button) v.findViewById(R.id.btn9);
        btn10 = (Button) v.findViewById(R.id.btn10);
        btn11 = (Button) v.findViewById(R.id.btn11);
        btn12 = (Button) v.findViewById(R.id.btn12);
        btn13 = (Button) v.findViewById(R.id.btn13);
        btn14 = (Button) v.findViewById(R.id.btn14);
        btn15 = (Button) v.findViewById(R.id.btn15);
        btn16 = (Button) v.findViewById(R.id.btn16);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);

        btn1.setOnLongClickListener(this);


        correctValue = seekBar.getProgress();
        seekText = (TextView) v.findViewById(R.id.seekText);
        seekText.setText(String.valueOf(correctValue));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                correctValue = i;
                seekText.setText(String.valueOf(correctValue));
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



        switch (view.getId()){

            case R.id.btnClose:
                setUpbut.setVisibility(View.GONE);
                break;

            case R.id.btnSaveData:
                saveData();
                setUpbut.setVisibility(View.GONE);

                break;

            case R.id.btnDec:
                changeText(correctValue--);
                seekBar.setProgress(correctValue);
                break;
            case R.id.btnInc:
                changeText(correctValue++);
                seekBar.setProgress(correctValue);
                break;
            case R.id.btn1:
               readData();

                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                break;
            case R.id.btn4:
                break;
            case R.id.btn5:
                break;
            case R.id.btn6:
                break;
            case R.id.btn7:
                break;
            case R.id.btn8:
                break;
            case R.id.btn9:
                break;
            case R.id.btn10:
                break;
            case R.id.btn11:
                break;
            case R.id.btn12:
                break;
            case R.id.btn13:
                break;
            case R.id.btn14:
                break;
            case R.id.btn15:
                break;
            case R.id.btn16:
                break;

        }
        correctDb.close();
    }

    private void saveData() {
        db = correctDb.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CorrectDB.KEY_BTNID, currentBtn);
        contentValues.put(CorrectDB.KEY_NAME, etBtnName.getText().toString());
        contentValues.put(CorrectDB.KEY_VALUE1, seekBar.getProgress());
        db.insert(CorrectDB.TABLE_BTNS, null, contentValues);
        correctDb.close();
        Toast.makeText(getActivity(), "Saved data", Toast.LENGTH_SHORT).show();
    }

    private void readData(){
        db = correctDb.getWritableDatabase();
        Cursor cursor = db.query(CorrectDB.TABLE_BTNS, null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(CorrectDB.KEY_ID);
            int btnidIndex = cursor.getColumnIndex(CorrectDB.KEY_BTNID);
            int nameIndex = cursor.getColumnIndex(CorrectDB.KEY_NAME);
            int value1Index = cursor.getColumnIndex(CorrectDB.KEY_VALUE1);

            do {
                Log.d(TAG, "id = " + cursor.getInt(idIndex) + ", Button id = " + cursor.getString(btnidIndex)
                        + ", name = " + cursor.getString(nameIndex) + ", value = " + cursor.getInt(value1Index));
            } while (cursor.moveToNext());

        } else Log.d (TAG, "0 rows");
        cursor.close();
    }

    public void changeText(int value){
        seekText.setText(String.valueOf(value));
    }



}
