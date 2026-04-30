package com.example.crud.Register;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.crud.R;

import java.util.ArrayList;

public class MultipleChoiceDialogFragment extends DialogFragment {

    public interface onMultiChoiceListener {
        void onPositiveButtonClicked(String[] list, ArrayList<String> Petugas);

        void onNegativeButtonClicked();
    }

    onMultiChoiceListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener= (onMultiChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+"Petugas Harus Dipilih");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        ArrayList<String> Petugas = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] list = getActivity().getResources().getStringArray(R.array.pilih_petugas);
        builder.setTitle("pilih petugas").setMultiChoiceItems(list, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b){
                    Petugas.add(list[i]);
                }else {
                    Petugas.remove(list[i]);
                }
            }
        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveButtonClicked(list,Petugas);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onNegativeButtonClicked();

                    }
                });
        return builder.create();
    }
}
