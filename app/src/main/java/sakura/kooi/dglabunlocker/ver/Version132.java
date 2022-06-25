package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_BluetoothService;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_BluetoothServiceDecoder;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_HomeActivity;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_RemoteSettingDialog;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_StrengthLongPressHandler;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_StrengthTouchListeners;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_HomeActivity_strengthTextA;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_HomeActivity_strengthTextB;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_RemoteSettingDialog_strengthA;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_RemoteSettingDialog_strengthB;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.method_BluetoothServiceDecoder_decode;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.method_BluetoothService_updateClientSide;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.method_BluetoothService_updateStrength;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.method_HomeActivity_updateStrengthText;

import java.util.Arrays;

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

        // HomeActivity LDC "控制方连接成功"
        Accessors.localStrengthA = lookupField(classGlobalVariables, "ay"); // getRealStrengthA
        Accessors.localStrengthB = lookupField(classGlobalVariables, "az"); // getRealStrengthB
        Accessors.maxStrengthA = lookupField(classGlobalVariables, "aw"); // getStrengthRangeMax
        Accessors.maxStrengthB = lookupField(classGlobalVariables, "ax"); // getStrengthRangeMax


        // HomeActivity action.equals("com.bjsm.dungeonlab.ble.abpower")
        String classHomeActivity = "com.bjsm.dungeonlab.ui.activity.HomeActivity";
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
    }
}
