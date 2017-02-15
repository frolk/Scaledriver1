package com.scale_driver.scaledriver1.dfu.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.scale_driver.scaledriver1.R;


public class ZipInfoFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zip_info, null);
        return new AlertDialog.Builder(getActivity()).setView(view).setTitle(R.string.dfu_file_info)
                .setPositiveButton(R.string.ok,null).create();
    }
}
