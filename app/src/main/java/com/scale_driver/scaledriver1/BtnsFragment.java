package com.scale_driver.scaledriver1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CorrectionInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class BtnsFragment extends Fragment {

    btnListener mCallBtnBack;

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

        Button button = (Button) v.findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBtnBack.CorrectBtnClicked("$384&");
            }
        });

        return v;
    }
}
