package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.*;

import java.util.Arrays;

public class Version132 extends AbstractVersionedCompatibilityProvider {
    @Override
    protected void initializeNames() {
        classGlobalVariables = "com.bjsm.dungeonlab.global.b";

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
