package com.example.listtodo_ps25812.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.listtodo_ps25812.Untilities.CustomWindowDialogHelper;
import com.example.listtodo_ps25812.databinding.ProcessDialogBinding;

public class ProcessDialog extends Dialog {
    public ProcessDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProcessDialogBinding processDialogBinding = ProcessDialogBinding.inflate(getLayoutInflater(),null,false);
        setContentView(processDialogBinding.getRoot());
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        CustomWindowDialogHelper.customWindow(this);
    }

}
