package com.scale_driver.scaledriver1.btnsHandle;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CorrectionInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scale_driver.scaledriver1.R;


public class BtnsFragment extends Fragment {

    btnListener mCallBtnBack;
    int correctValue = 0;
    TextView btnValue;
    SeekBar seekBarThis;

    public static String btnPrefValues = "com.scale_driver.btnValues";

    SharedPreferences btnValues;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        btnValues = getActivity().getSharedPreferences(btnPrefValues, Context.MODE_PRIVATE);
        SharedPreferences.Editor btnEditor = btnValues.edit();
        btnEditor.putString("btn1", "$381");
        btnEditor.putString("btn2", "$382");
        btnEditor.putString("btn3", "$383");
        btnEditor.putString("btn4", "$384");
        btnEditor.putString("btn5", "$385");
        btnEditor.putString("btn6", "$386");
        btnEditor.commit();


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.btns_fragment, null);

        SeekBar seekbar = (SeekBar) v.findViewById(R.id.seekBar);
        Button btnInc = (Button) v.findViewById(R.id.btnInc);
        Button btnDec = (Button) v.findViewById(R.id.btnDec);
        Button button = (Button) v.findViewById(R.id.btn1);
        correctValue = seekbar.getProgress();
        final TextView seekText = (TextView) v.findViewById(R.id.seekText);
        btnValue = seekText;
        seekBarThis = seekbar;

        seekText.setText(String.valueOf(correctValue));

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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


        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeText(correctValue++);
                seekBarThis.setProgress(correctValue);
            }
        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeText(correctValue--);
                seekBarThis.setProgress(correctValue);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBtnBack.CorrectBtnClicked("$384&");
            }
        });

        return v;
    }


    public void changeText(int value){
        btnValue.setText(String.valueOf(value));
    }



}
