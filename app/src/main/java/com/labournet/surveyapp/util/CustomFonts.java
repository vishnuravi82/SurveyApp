package com.labournet.surveyapp.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.TextInputEditText;

/**
 * Created by Athira on 10/6/19.
 */
public class CustomFonts {

    private Context ctx;
    public static Typeface BRANDON_BOLD, BRANDON_LIGHT, GEORGIA,BRANDON_REG;

    public CustomFonts(Context ctx) {
        this.ctx = ctx;
    }

    public CustomFonts() {

    }

    public void initializeFontUniversally(Context ctx) {
        BRANDON_BOLD = Typeface.createFromAsset(ctx.getAssets(), "fonts/BrandonText_Bold.otf");
        BRANDON_LIGHT = Typeface.createFromAsset(ctx.getAssets(), "fonts/BrandonText_Light.otf");
        BRANDON_REG = Typeface.createFromAsset(ctx.getAssets(), "fonts/Brandon_reg.otf");
        GEORGIA = Typeface.createFromAsset(ctx.getAssets(), "fonts/Georgia.ttf");
    }

    public static void setCustomFonts(final ViewGroup parent) {
        setFonts(parent, "");
    }

    public static void setCustomFonts(final ViewGroup parent, final String font) {
        setFonts(parent, font);
    }
    public static void setFontExpanded(Context context, View view) {

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Exo2-SemiBoldExpanded.otf");
        try {
            if (view instanceof AppCompatTextView) {
                AppCompatTextView textView = (AppCompatTextView) view;
                textView.setTypeface(tf);
            } else if (view instanceof AppCompatButton) {
                AppCompatButton button = (AppCompatButton) view;
                button.setTypeface(tf);
            } else if (view instanceof TextInputEditText) {
                TextInputEditText et = (TextInputEditText) view;
                et.setTypeface(tf);
            }else if (view instanceof RadioButton) {
                RadioButton rb = (RadioButton) view;
                rb.setTypeface(tf);
            }else if (view instanceof CheckBox) {
                CheckBox cb = (CheckBox) view;
                cb.setTypeface(tf);
            }
        } catch (Exception e) {
            e.toString();
        }
    }

    public static void setFontBold(Context context, View view) {

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/BrandonText_Bold.otf");
        try {
            if (view instanceof AppCompatTextView) {
                AppCompatTextView textView = (AppCompatTextView) view;
                textView.setTypeface(tf);
            } else if (view instanceof AppCompatButton) {
                AppCompatButton button = (AppCompatButton) view;
                button.setTypeface(tf);
            } else if (view instanceof TextInputEditText) {
                TextInputEditText et = (TextInputEditText) view;
                et.setTypeface(tf);
            }else if (view instanceof RadioButton) {
                RadioButton rb = (RadioButton) view;
                rb.setTypeface(tf);
            }else if (view instanceof CheckBox) {
                CheckBox cb = (CheckBox) view;
                cb.setTypeface(tf);
            }
        } catch (Exception e) {
            e.toString();
        }
    }

    public static void setFontRegular(Context context, View view) {

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Brandon_reg.otf");
        try {
            if (view instanceof AppCompatTextView) {
                AppCompatTextView textView = (AppCompatTextView) view;
                textView.setTypeface(tf);
            } else if (view instanceof AppCompatButton) {
                AppCompatButton button = (AppCompatButton) view;
                button.setTypeface(tf);
            } else if (view instanceof TextInputEditText) {
                TextInputEditText et = (TextInputEditText) view;
                et.setTypeface(tf);
            }else if (view instanceof RadioButton) {
                RadioButton rb = (RadioButton) view;
                rb.setTypeface(tf);
            }else if (view instanceof CheckBox) {
                CheckBox cb = (CheckBox) view;
                cb.setTypeface(tf);
            }
        } catch (Exception e) {
            e.toString();
        }
    }

    public static void setFontThin(Context context, View view) {

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/BrandonText_Light.otf");
        try {
            if (view instanceof AppCompatTextView) {
                AppCompatTextView textView = (AppCompatTextView) view;
                textView.setTypeface(tf);
            } else if (view instanceof AppCompatButton) {
                AppCompatButton button = (AppCompatButton) view;
                button.setTypeface(tf);
            } else if (view instanceof TextInputEditText) {
                TextInputEditText et = (TextInputEditText) view;
                et.setTypeface(tf);
            }else if (view instanceof RadioButton) {
                RadioButton rb = (RadioButton) view;
                rb.setTypeface(tf);
            }else if (view instanceof CheckBox) {
                CheckBox cb = (CheckBox) view;
                cb.setTypeface(tf);
            }
        } catch (Exception e) {
            e.toString();
        }
    }

    private static void setFonts(ViewGroup parent, String fontType) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                setFonts((ViewGroup) child, fontType);
            } else {
                String applyFont = "";
                if (fontType.trim().isEmpty()) {
                    if (child.getTag() != null) {
                        applyFont = child.getTag().toString();
                    }
                } else {
                    applyFont = fontType;
                }
                switch (applyFont) {
                    case "Brandon_reg": {
                        if (child instanceof AppCompatTextView) {
                            ((AppCompatTextView) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof AppCompatEditText) {
                            ((AppCompatEditText) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof AppCompatButton) {
                            ((AppCompatButton) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof AppCompatCheckBox) {
                            ((AppCompatCheckBox) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof AppCompatRadioButton) {
                            ((AppCompatRadioButton) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof TextInputEditText) {
                            ((TextInputEditText) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof TextView) {
                            ((TextView) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof EditText) {
                            ((EditText) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof Button) {
                            ((Button) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof CheckBox) {
                            ((CheckBox) child).setTypeface(BRANDON_REG);
                        } else if (child instanceof RadioButton) {
                            ((RadioButton) child).setTypeface(BRANDON_REG);
                        }
                    }
                    break;
                    case "BrandonText_Bold": {
                        if (child instanceof AppCompatTextView) {
                            ((AppCompatTextView) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof AppCompatEditText) {
                            ((AppCompatEditText) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof AppCompatButton) {
                            ((AppCompatButton) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof AppCompatCheckBox) {
                            ((AppCompatCheckBox) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof AppCompatRadioButton) {
                            ((AppCompatRadioButton) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof TextInputEditText) {
                            ((TextInputEditText) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof TextView) {
                            ((TextView) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof EditText) {
                            ((EditText) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof Button) {
                            ((Button) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof CheckBox) {
                            ((CheckBox) child).setTypeface(BRANDON_BOLD);
                        } else if (child instanceof RadioButton) {
                            ((RadioButton) child).setTypeface(BRANDON_BOLD);
                        }
                    }
                    break;
                    case "BrandonText_Light": {
                        if (child instanceof AppCompatTextView) {
                            ((AppCompatTextView) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof AppCompatEditText) {
                            ((AppCompatEditText) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof AppCompatButton) {
                            ((AppCompatButton) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof AppCompatCheckBox) {
                            ((AppCompatCheckBox) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof AppCompatRadioButton) {
                            ((AppCompatRadioButton) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof TextInputEditText) {
                            ((TextInputEditText) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof TextView) {
                            ((TextView) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof EditText) {
                            ((EditText) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof Button) {
                            ((Button) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof CheckBox) {
                            ((CheckBox) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof RadioButton) {
                            ((RadioButton) child).setTypeface(BRANDON_LIGHT);
                        }
                    }
                    break;
                    case "Georgia": {
                        if (child instanceof AppCompatTextView) {
                            ((AppCompatTextView) child).setTypeface(GEORGIA);
                        } else if (child instanceof AppCompatEditText) {
                            ((AppCompatEditText) child).setTypeface(GEORGIA);
                        } else if (child instanceof AppCompatButton) {
                            ((AppCompatButton) child).setTypeface(GEORGIA);
                        } else if (child instanceof AppCompatCheckBox) {
                            ((AppCompatCheckBox) child).setTypeface(GEORGIA);
                        } else if (child instanceof AppCompatRadioButton) {
                            ((AppCompatRadioButton) child).setTypeface(GEORGIA);
                        } else if (child instanceof TextInputEditText) {
                            ((TextInputEditText) child).setTypeface(GEORGIA);
                        } else if (child instanceof TextView) {
                            ((TextView) child).setTypeface(GEORGIA);
                        } else if (child instanceof EditText) {
                            ((EditText) child).setTypeface(GEORGIA);
                        } else if (child instanceof Button) {
                            ((Button) child).setTypeface(GEORGIA);
                        } else if (child instanceof CheckBox) {
                            ((CheckBox) child).setTypeface(GEORGIA);
                        } else if (child instanceof RadioButton) {
                            ((RadioButton) child).setTypeface(GEORGIA);
                        }
                    }
                    break;
                    case "null":break;
                    case "":break;
                    default: {
                        if (child instanceof AppCompatTextView) {
                            ((AppCompatTextView) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof AppCompatEditText) {
                            ((AppCompatEditText) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof AppCompatButton) {
                            ((AppCompatButton) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof AppCompatCheckBox) {
                            ((AppCompatCheckBox) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof AppCompatRadioButton) {
                            ((AppCompatRadioButton) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof TextInputEditText) {
                            ((TextInputEditText) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof TextView) {
                            ((TextView) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof EditText) {
                            ((EditText) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof Button) {
                            ((Button) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof CheckBox) {
                            ((CheckBox) child).setTypeface(BRANDON_LIGHT);
                        } else if (child instanceof RadioButton) {
                            ((RadioButton) child).setTypeface(BRANDON_LIGHT);
                        }
                    }
                    break;
                }
            }
        }
    }
}