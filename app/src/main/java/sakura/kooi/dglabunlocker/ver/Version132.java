package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.*;

import java.util.Arrays;
import java.util.Collection;

import sakura.kooi.dglabunlocker.variables.Accessors;

public class Version132 extends AbstractVersionedCompatibilityProvider {

    @Override
    protected void initializeAccessors() throws ReflectiveOperationException {
        String classGlobalVariables = "com.bjsm.dungeonlab.global.b";

        // method_BluetoothServiceDecoder_decode
        Accessors.totalStrengthA = lookupField(classGlobalVariables, "as");
        Accessors.totalStrengthB = lookupField(classGlobalVariables, "at");
        Accessors.remoteStrengthA = lookupField(classGlobalVariables, "au");
        Accessors.remoteStrengthB = lookupField(classGlobalVariables, "av");

        Accessors.isRemoteControlling = lookupField(classGlobalVariables, "by");

        // HomeActivity LDC "控制方连接成功"
        Accessors.localStrengthA = lookupField(classGlobalVariables, "ay"); // getRealStrengthA
        Accessors.localStrengthB = lookupField(classGlobalVariables, "az"); // getRealStrengthB
        Accessors.maxStrengthA = lookupField(classGlobalVariables, "aw"); // getStrengthRangeMax
        Accessors.maxStrengthB = lookupField(classGlobalVariables, "ax"); // getStrengthRangeMax

        // PlayFragment.class -> MainListCustomizAdapter constructor -> DataUtils.b()
        Accessors.listWaveData = lookupField(classGlobalVariables, "ak");

        String classNameWaveBean = "com.bjsm.dungeonlab.bean.WaveClassicBean";
        Accessors.classWaveClassicBean = lookupClass(classNameWaveBean);
        Accessors.waveIsClassic = lookupField(classNameWaveBean, "isClassic");
        Accessors.waveName = lookupField(classNameWaveBean, "waveName");

        Accessors.funcSaveWave = lookupMethod("com.bjsm.dungeonlab.d.d", "a", int.class, Accessors.classWaveClassicBean, int.class, String.class, int.class);

        // MainListCustomizAdapter -> constr -> setList(collection) -> a
        Accessors.funcAdapterSetList = lookupMethod(class_MainListCustomizAdapter, "a", Collection.class);

        // HomeActivity action.equals("com.bjsm.dungeonlab.ble.abpower")
        Accessors.textHomeActivityStrengthA = lookupField(class_HomeActivity, "y");
        Accessors.textHomeActivityStrengthB = lookupField(class_HomeActivity, "G");
    }

    @Override
    protected void initializeNames() {

        // RemoteSettingDialog
        class_RemoteSettingDialog = "com.bjsm.dungeonlab.widget.RemoteSettingDialog";
        field_RemoteSettingDialog_strengthA = "a_channel_strength_range";
        field_RemoteSettingDialog_strengthB = "b_channel_strength_range";

        // BluetoothService.? implements BleNotifyCallback
        class_BluetoothServiceDecoder = "com.bjsm.dungeonlab.service.BlueToothService$18";

        // class_BluetoothServiceDecoder LDC "notify 回调"
        method_BluetoothServiceDecoder_decode = "a";

        // HomeActivity.? implements View.OnTouchListener && R.string.remote_strength_max
        class_StrengthTouchListeners = Arrays.asList(
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$15",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$16",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$17",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$18"
        );

        // BlueToothService LDC "写入强度数据成功"
        class_BluetoothService = "com.bjsm.dungeonlab.service.BlueToothService";
        method_BluetoothService_updateStrength = "b";
        // class_StrengthTouchListeners -> R.string.remote_strength_max -> if -> getter -> setter
        method_BluetoothService_updateClientSide = "b";

        class_StrengthLongPressHandler = "com.bjsm.dungeonlab.ui.activity.HomeActivity$1";

        // HomeActivity action.equals("com.bjsm.dungeonlab.ble.abpower")
        class_HomeActivity = "com.bjsm.dungeonlab.ui.activity.HomeActivity";
        method_HomeActivity_updateStrengthText = "a";
        field_HomeActivity_strengthTextA = "y";
        field_HomeActivity_strengthTextB = "G";

        class_MainListCustomizAdapter = "com.bjsm.dungeonlab.adapter.MainListCustomizAdapter";
    }
}
