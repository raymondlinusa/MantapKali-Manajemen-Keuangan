package com.example.uangmantapkali.utilities;

import android.text.TextUtils;
import android.util.Patterns;

public class TextValidation {
    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
