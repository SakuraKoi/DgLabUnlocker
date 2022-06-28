package sakura.kooi.dglabunlocker.features.clickable;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import sakura.kooi.dglabunlocker.features.ClickableFeature;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.business.HookCurrentActivity;
import sakura.kooi.dglabunlocker.utils.UiUtils;

public class FeatureIgnoreBatteryOptimization extends ClickableFeature {
    @Override
    public String getName() {
        return "忽略电池优化";
    }

    @Override
    public List<Class<? extends AbstractHook<?>>> getRequiredHooks() {
        return Arrays.asList(HookCurrentActivity.class);
    }

    @Override
    public ClientSide getSide() {
        return ClientSide.ALL;
    }

    @Override
    public void inflateFeatureLayout(Context context, LinearLayout layout) {
        TextView btn = UiUtils.createButton(layout, "忽略电池优化", e -> {
            requestIgnoreBatteryOptimization(context);
        });
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pm.isIgnoringBatteryOptimizations("com.bjsm.dungeonlab")) {
            btn.setEnabled(false);
            btn.setText("忽略电池优化 (已生效)");
        }
    }

    private void requestIgnoreBatteryOptimization(Context context) {
        if (context.checkSelfPermission(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) == PackageManager.PERMISSION_GRANTED) {
            @SuppressLint("BatteryLife")
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:com.bjsm.dungeonlab"));
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "请授予所需权限后重试", Toast.LENGTH_LONG).show();
            HookCurrentActivity.getCurrentActivity().requestPermissions(new String[] {Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS}, 114513);
        }
    }
}
