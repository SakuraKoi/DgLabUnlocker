package sakura.kooi.dglabunlocker.features.clickable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sakura.kooi.dglabunlocker.features.ClickableFeature;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.business.HookActivityResult;
import sakura.kooi.dglabunlocker.hooks.business.HookCurrentActivity;
import sakura.kooi.dglabunlocker.ui.WaveSelectDialog;
import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class FeatureExportWave extends ClickableFeature implements HookActivityResult.IActivityResultInterceptor {
    private List<Object> pendingWaves;

    @Override
    public String getName() {
        return "导出波形";
    }

    @Override
    public List<Class<? extends AbstractHook<?>>> getRequiredHooks() {
        return Arrays.asList(HookCurrentActivity.class, HookActivityResult.class);
    }

    @Override
    public ClientSide getSide() {
        return ClientSide.ALL;
    }

    @Override
    public View inflateFeatureLayout(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        UiUtils.createButton(layout, "导出波形", e -> {
            displayExportWaveDialog(context);
        });
        return layout;
    }

    private Map<String, Object> getWaveList() throws ReflectiveOperationException {
        return Accessors.listWaveData.get().stream().filter(wave -> {
            try {
                return Accessors.waveIsClassic.get(wave) == 1;
            } catch (ReflectiveOperationException e) {
                Log.e("DgLabUnlocker", "An error occurred while checking wave is classic", e);
                return false;
            }
        }).collect(Collectors.toMap(wave -> {
            try {
                return Accessors.waveName.get(wave);
            } catch (ReflectiveOperationException e) {
                Log.e("DgLabUnlocker", "An error occurred while getting wave name", e);
                return null;
            }
        }, wave -> wave, (o1, o2) -> o1, LinkedHashMap::new));
    }

    private void displayExportWaveDialog(Uri uri) {
        Context context = HookCurrentActivity.getCurrentActivity();
        Map<String, Object> waveList;
        try {
            waveList = getWaveList();
        } catch (ReflectiveOperationException e) {
            Log.e("DgLabUnlocker", "An error occurred while getting wave list", e);
            Toast.makeText(context, "获取波形列表失败", Toast.LENGTH_LONG).show();
            return;
        }
        new WaveSelectDialog(context, "选择要导出的波形", waveList, selectedWaves -> {
            if (selectedWaves.isEmpty()) {
                return;
            }
            pendingWaves = selectedWaves;
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/json");
            intent.putExtra(Intent.EXTRA_TITLE, "DG-Lab_Unlocker_Wave-Export.json");
            Log.i("DgLabUnlocker", "Sending ACTION_CREATE_DOCUMENT from activity " + HookCurrentActivity.getCurrentActivity().getClass().getName());
            HookCurrentActivity.getCurrentActivity().startActivityForResult(intent, 11451);
        }).show();
    }

    private JsonArray cleanupWaves(Gson gson, List<Object> selectedWaves) {
        JsonArray out = new JsonArray();
        for (Object wave : selectedWaves) {
            JsonObject element = gson.toJsonTree(wave).getAsJsonObject();
            element.remove("selectedStateA");
            element.add("selectedStateA", new JsonPrimitive(3));

            element.remove("selectedStateB");
            element.add("selectedStateB", new JsonPrimitive(3));

            element.remove("playOrderA");
            element.add("playOrderA", new JsonPrimitive(-1));

            element.remove("playOrderB");
            element.add("playOrderB", new JsonPrimitive(-1));

            element.remove("channel");
            out.add(element);
        }
        return out;
    }

    @Override
    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == 11451 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String pendingJson = gson.toJson(cleanupWaves(gson, pendingWaves));
                try {
                    ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "w");
                    FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
                    fileOutputStream.write(pendingJson.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    pfd.close();
                    Toast.makeText(context, "成功导出了 " + pendingWaves.size() + " 个波形", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Log.e("DgLabUnlocker", "An error occurred while writing wave list", e);
                    Toast.makeText(context, "导出波形列表失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
