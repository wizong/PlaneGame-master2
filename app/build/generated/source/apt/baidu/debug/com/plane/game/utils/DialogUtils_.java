//
// DO NOT EDIT THIS FILE.
// Generated using AndroidAnnotations 4.4.0.
// 
// You can create a larger work that contains this file and distribute that work under terms of your choice.
//

package com.plane.game.utils;

import android.content.Context;

public final class DialogUtils_
    extends DialogUtils
{
    private Context context_;

    private DialogUtils_(Context context) {
        context_ = context;
        init_();
    }

    public static DialogUtils_ getInstance_(Context context) {
        return new DialogUtils_(context);
    }

    private void init_() {
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }
}