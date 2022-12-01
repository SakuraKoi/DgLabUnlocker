package sakura.kooi.dglabunlocker.features.clickable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import sakura.kooi.dglabunlocker.features.ClickableFeature;
import sakura.kooi.dglabunlocker.hooks.AbstractHook;
import sakura.kooi.dglabunlocker.hooks.business.HookActivityResult;
import sakura.kooi.dglabunlocker.hooks.business.HookCurrentActivity;
import sakura.kooi.dglabunlocker.hooks.business.HookWaveAdapterCollector;
import sakura.kooi.dglabunlocker.ui.WaveSelectDialog;
import sakura.kooi.dglabunlocker.utils.UiUtils;
import sakura.kooi.dglabunlocker.utils.WaveUtils;
import sakura.kooi.dglabunlocker.variables.Accessors;

public class FeatureImportWave extends ClickableFeature implements HookActivityResult.IActivityResultInterceptor {
    @Override
    public String getName() {
        return "导入波形";
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
    public void inflateFeatureLayout(Context context, LinearLayout layout) {
        UiUtils.createButton(layout, "导入波形", e -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/json");
            Log.i("DgLabUnlocker", "Sending ACTION_OPEN_DOCUMENT from activity " + HookCurrentActivity.getCurrentActivity().getClass().getName());
            HookCurrentActivity.getCurrentActivity().startActivityForResult(intent, 11452);
        });
    }

    @Override
    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == 11452 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    try (ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r")) {
                        try (FileInputStream fileInputStream = new FileInputStream(pfd.getFileDescriptor())) {
                            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                            int nRead;
                            byte[] block = new byte[16384];
                            while ((nRead = fileInputStream.read(block, 0, block.length)) != -1) {
                                buffer.write(block, 0, nRead);
                            }
                            handleImportedData(buffer.toByteArray());
                        }
                    }
                } catch (IOException e) {
                    Log.e("DgLabUnlocker", "An error occurred while reading wave list", e);
                    Toast.makeText(context, "读取波形列表失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void handleImportedData(byte[] data) {
        Gson gson = new Gson();
        List<Object> waves = gson.fromJson(new String(data), TypeToken.getParameterized(List.class, Accessors.classWaveClassicBean).getType());
        LinkedHashMap<String, Object> waveList = waves.stream().collect(Collectors.toMap(wave -> {
            try {
                return Accessors.waveName.get(wave);
            } catch (ReflectiveOperationException e) {
                Log.e("DgLabUnlocker", "An error occurred while getting wave name", e);
                return null;
            }
        }, wave -> wave, (o1, o2) -> o1, LinkedHashMap::new));

        Context context = HookCurrentActivity.getCurrentActivity();
        new WaveSelectDialog(context, "选择要导入的波形", waveList, selectedWaves -> {
            if (selectedWaves.isEmpty()) {
                return;
            }

            Set<String> currentWaveNameList = null;
            try {
                currentWaveNameList = WaveUtils.getWaveListWithName(true).keySet();
            } catch (ReflectiveOperationException e) {
                Log.w("DgLabUnlocker", "An error occurred while deduplicating wave name", e);
            }

            int success = 0;
            for (Object wave : selectedWaves) {
                try {
                    Accessors.funcSaveWave.invoke(8, wave, 1, deduplicateWaveName(currentWaveNameList, Accessors.waveName.get(wave)), 1);
                    success++;
                } catch (Throwable e) {
                    Log.e("DgLabUnlocker", "An error occurred while importing wave", e);
                }
            }
            boolean refreshed = false;
            try {
                refreshWaveList();
                refreshed = true;
            } catch (ReflectiveOperationException e) {
                Log.e("DgLabUnlocker", "An error occurred while refreshing wave list", e);
            }
            Toast.makeText(context, "成功导入" + success + " / " + selectedWaves.size() + " 个波形" + (refreshed ? "" : " (可能需要重启APP以生效)"), Toast.LENGTH_LONG).show();
        }).show();
    }

    private String deduplicateWaveName(Set<String> currentWaveNameList, String origName) {
        if (currentWaveNameList == null) {
            return origName;
        }

        int id = 0;
        String name = origName;
        while (currentWaveNameList.contains(name)) {
            name = origName + ++id;
        }
        return name;
    }

    private void refreshWaveList() throws ReflectiveOperationException {
        List<Object> waveList = WaveUtils.getWaveList(true);
        for (Object adapter : HookWaveAdapterCollector.getAdapters()) {
            try {
                Accessors.funcSaveWave.invoke(adapter, waveList);
            } catch (Throwable e) {
                throw new ReflectiveOperationException(e);
            }
        }
    }

}
