package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_BluetoothService;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_BluetoothServiceDecoder;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_ControlledStrengthTouchListeners;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_RemoteSettingDialog;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_StrengthTouchListeners;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_RemoteSettingDialog_strengthA;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_RemoteSettingDialog_strengthB;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.method_BluetoothServiceDecoder_decode;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.method_BluetoothService_updateStrength;

import java.util.Arrays;

public class Version132 extends AbstractVersionedCompatibilityProvider {
    @Override
    protected void initializeNames() {
        classGlobalVariables = "com.bjsm.dungeonlab.global.b";
        classBugDialog = "com.bjsm.dungeonlab.widget.BugDialog";

        // RemoteSettingDialog
        class_RemoteSettingDialog = "com.bjsm.dungeonlab.widget.RemoteSettingDialog";
        field_RemoteSettingDialog_strengthA = "a_channel_strength_range";
        field_RemoteSettingDialog_strengthB = "b_channel_strength_range";

        // BluetoothService.? implements BleNotifyCallback
        class_BluetoothServiceDecoder = "com.bjsm.dungeonlab.service.BlueToothService$18";

        // class_BluetoothServiceDecoder LDC "notify 回调"
        method_BluetoothServiceDecoder_decode = "a";

        // method_BluetoothServiceDecoder_decode
        totalStrengthA = "as";
        totalStrengthB = "at";
        remoteStrengthA = "au";
        remoteStrengthB = "av";

        // HomeActivity LDC "控制方连接成功"
        localStrengthA = "ay"; // getRealStrength
        localStrengthB = "az"; // getRealStrengthA
        maxStrengthA = "aw"; // getStrengthRangeMax
        maxStrengthB = "ax"; // getStrengthRangeMax

        // HomeActivity.? implements View.OnTouchListener && R.string.remote_strength_max
        class_StrengthTouchListeners = Arrays.asList(
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$15",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$16",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$17",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$18"
        );

        // class_StrengthTouchListeners -> R.string.remote_strength_max -> if
        isRemote = "by";

        // ControlledActivity.? implements View.OnTouchListener && R.string.strength_max
        // ControlledActivity.? implements View.OnTouchListener && R.string.strength_min
        class_ControlledStrengthTouchListeners = Arrays.asList(
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$24",
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$2",
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$25",
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$3"
        );

        // BlueToothService LDC "写入强度数据成功"
        class_BluetoothService = "com.bjsm.dungeonlab.service.BlueToothService";
        method_BluetoothService_updateStrength = "b";
    }
}
