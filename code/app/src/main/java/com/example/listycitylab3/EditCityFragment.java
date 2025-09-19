package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    interface EditCityDialogListener {
        void editCity(City city, String name, String province);
    }

    private EditCityDialogListener editListener;
    private City city;

    public void getCity (City oldCity) {
        city = oldCity;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            editListener = (EditCityDialogListener) context;
        }
        else {
            throw new RuntimeException(context +
                    "must implement EditCityDialogueListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        editCityName.setText(city.getName());
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        editProvinceName.setText(city.getProvince());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

       return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    editListener.editCity(city, cityName, provinceName);
                })
                .create();
    }
}
