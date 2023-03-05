package com.example.listtodo_ps25812.Untilities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.Window;
import android.view.WindowManager;

public class CustomWindowDialogHelper {
    public static void customWindow(Dialog dialog){
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        InsetDrawable inset = new InsetDrawable(new ColorDrawable(Color.TRANSPARENT), 40);
        window.setBackgroundDrawable(inset);
        dialog.setCanceledOnTouchOutside(false);
    }
}
