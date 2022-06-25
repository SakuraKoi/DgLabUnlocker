package sakura.kooi.dglabunlocker.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import sakura.kooi.dglabunlocker.utils.UiUtils;

public class WaveSelectDialog  extends Dialog {
    public WaveSelectDialog(@NonNull Context context, String title, Map<String, Object> waveList, Consumer<List<Object>> selectedCallback) {
        super(context);
        this.setContentView(UiUtils.makeDialogLayout(this, title, container -> {
            ArrayList<Object> selectedWave = new ArrayList<>();
            ScrollView scrollView = new ScrollView(context);
            LinearLayout selectContainer = new LinearLayout(context);
            selectContainer.setOrientation(LinearLayout.VERTICAL);
            scrollView.addView(selectContainer);

            waveList.forEach((name, wave) -> UiUtils.createCheckbox(selectContainer, " " + name, selected -> {
                if (selected) {
                    selectedWave.add(wave);
                } else {
                    selectedWave.remove(wave);
                }
            }));

            container.addView(scrollView);
            UiUtils.createButton(container, "确认", v -> {
                selectedCallback.accept(selectedWave);
                dismiss();
            });
        }));
    }
}
