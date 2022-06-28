package sakura.kooi.dglabunlocker.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import sakura.kooi.dglabunlocker.ui.StatusDialog;
import sakura.kooi.dglabunlocker.variables.ResourceInject;

public class UiUtils {
    public static int dpToPx(View view, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getContext().getResources().getDisplayMetrics());
    }

    @NonNull
    public static LinearLayout makeDialogContainer(Context context, String title) {
        LinearLayout container = new LinearLayout(context);
        container.setMinimumWidth(UiUtils.dpToPx(container, 270));
        container.setPadding(dpToPx(container, 16), dpToPx(container, 16), dpToPx(container, 16), dpToPx(container, 16));
        if (StatusDialog.resourceInjection) {
            container.setBackground(ResourceInject.dialogSettingsBackground.getConstantState().newDrawable());
        }
        container.setOrientation(LinearLayout.VERTICAL);
        TextView header = createTextView(context, title);
        header.setGravity(Gravity.CENTER);
        header.setPadding(0, 0, 0, dpToPx(container, 16));
        container.addView(header);
        return container;
    }

    public static LinearLayout makeDialogLayout(Dialog dialog, String title, Consumer<LinearLayout> layoutHandler) {
        if (StatusDialog.resourceInjection) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } else {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        LinearLayout container = UiUtils.makeDialogContainer(dialog.getContext(), title);
        layoutHandler.accept(container);
        return container;
    }

    public static void createSpacing(LinearLayout container, float dp) {
        Space space = new Space(container.getContext());
        container.addView(space);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) space.getLayoutParams();
        params.height = dpToPx(space, dp);
        space.setLayoutParams(params);
    }

    public static TextView createButton(LinearLayout container, String title, View.OnClickListener listener) {
        return createButton(container, title, true, listener);
    }

    public static TextView createButton(LinearLayout container, String title, boolean withSpacing, View.OnClickListener listener) {
        TextView button = createButton(container.getContext(), title, listener);
        if (withSpacing) {
            container.addView(button);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
            layoutParams.topMargin = dpToPx(button, 6);
            layoutParams.bottomMargin = dpToPx(button, 6);
            button.setLayoutParams(layoutParams);
        } else {
            container.addView(button);
        }
        return button;
    }

    public static TextView createButton(Context context, String title, View.OnClickListener listener) {
        TextView view = new TextView(context);
        view.setPadding( dpToPx(view, 8), dpToPx(view, 3), dpToPx(view, 8), dpToPx(view, 3));
        if (StatusDialog.resourceInjection) {
            view.setBackground(ResourceInject.buttonBackground.getConstantState().newDrawable());
            view.setTextColor(Color.BLACK);
        } else {
            view.setBackgroundResource(android.R.drawable.btn_default);
        }
        view.setText(title);
        view.setGravity(Gravity.CENTER);
        view.setOnClickListener(listener);
        return view;
    }

    public static CheckBox createCheckbox(LinearLayout container, String title, Consumer<Boolean> listener) {
        CheckBox checkbox = new CheckBox(container.getContext());
        checkbox.setPadding( dpToPx(checkbox, 8), dpToPx(checkbox, 3), dpToPx(checkbox, 8), dpToPx(checkbox, 3));
        if (StatusDialog.resourceInjection) {
            checkbox.setTextColor(0xffffe99d);
            StateListDrawable thumbSelector = new StateListDrawable();
            thumbSelector.addState(new int[]{android.R.attr.state_checked}, ResourceInject.checkOn.getConstantState().newDrawable());
            thumbSelector.addState(StateSet.WILD_CARD, ResourceInject.checkOff.getConstantState().newDrawable());
            checkbox.setButtonDrawable(thumbSelector);
        }
        checkbox.setText(title);
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> listener.accept(isChecked));
        checkbox.setChecked(false);
        container.addView(checkbox);
        return checkbox;
    }

    public static TextView createTextView(Context context, String text) {
        TextView textView = createTextView(context, 0xffffe99d);
        textView.setText(text);
        return textView;
    }

    public static TextView createTextView(Context context) {
        return createTextView(context, 0xffffe99d);
    }

    public static TextView createTextView(Context context, int color) {
        TextView view = new TextView(context);
        if (StatusDialog.resourceInjection) {
            view.setTextColor(color);
        }
        return view;
    }

    public static Switch createSwitch(Context context) {
        Switch view = new Switch(context);
        if (StatusDialog.resourceInjection) {
            StateListDrawable trackSelector = new StateListDrawable();
            trackSelector.addState(new int[]{android.R.attr.state_checked}, ResourceInject.switchOpenTrack.getConstantState().newDrawable());
            trackSelector.addState(StateSet.WILD_CARD, ResourceInject.switchCloseTrack.getConstantState().newDrawable());
            view.setTrackDrawable(trackSelector);
            StateListDrawable thumbSelector = new StateListDrawable();
            thumbSelector.addState(new int[]{android.R.attr.state_checked}, ResourceInject.switchOpenThumb.getConstantState().newDrawable());
            thumbSelector.addState(StateSet.WILD_CARD, ResourceInject.switchCloseThumb.getConstantState().newDrawable());
            view.setThumbDrawable(thumbSelector);
            view.setTextOff("");
            view.setTextOn("");
        } else {
            view.setTextOff("Off");
            view.setTextOn("On");
        }
        return view;
    }
}
