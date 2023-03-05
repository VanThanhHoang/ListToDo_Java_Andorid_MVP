package com.example.listtodo_ps25812.View.Dialog.OtherDialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.example.listtodo_ps25812.Presenter.LogoutListener;
import com.example.listtodo_ps25812.databinding.BottomSheetDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetDialogMenu extends BottomSheetDialog {
    BottomSheetDialogBinding bottomSheetDialogBinding;
    LogoutListener logoutListener;

    public BottomSheetDialogMenu(@NonNull Context context, LogoutListener logoutListener) {
        super(context);
        this.logoutListener = logoutListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomSheetDialogBinding = BottomSheetDialogBinding.inflate(getLayoutInflater(), null, false);
        setContentView(bottomSheetDialogBinding.getRoot());
        bottomSheetDialogBinding.swDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        bottomSheetDialogBinding.tvLogOut.setOnClickListener(v -> {
            this.dismiss();
            logoutListener.logOut();
        });
    }
}
