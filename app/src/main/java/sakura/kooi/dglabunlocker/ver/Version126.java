package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.*;

import java.util.Arrays;

import sakura.kooi.dglabunlocker.variables.Accessors;

public class Version126 extends AbstractVersionedCompatibilityProvider {
    @Override
    protected void initializeAccessors() throws ReflectiveOperationException {
        String classGlobalVariables = "com.bjsm.dungeonlab.global.b";

        // method_BluetoothServiceDecoder_decode
        Accessors.totalStrengthA = lookupField(classGlobalVariables, "V");
        Accessors.totalStrengthB = lookupField(classGlobalVariables, "W");
        Accessors.remoteStrengthA = lookupField(classGlobalVariables, "X");
        Accessors.remoteStrengthB = lookupField(classGlobalVariables, "Y");

        // HomeActivity LDC "控制方连接成功"
        Accessors.localStrengthA = lookupField(classGlobalVariables, "ab"); // getRealStrengthA
        Accessors.localStrengthB = lookupField(classGlobalVariables, "ac"); // getRealStrengthB
        Accessors.maxStrengthA = lookupField(classGlobalVariables, "Z"); // getStrengthRangeMax
        Accessors.maxStrengthB = lookupField(classGlobalVariables, "aa"); // getStrengthRangeMax
    }

    @Override
    protected void initializeNames() {
        class_BluetoothServiceDecoder = "com.bjsm.dungeonlab.service.BlueToothService$18";
        method_BluetoothServiceDecoder_decode = "a";

        class_BluetoothService = "com.bjsm.dungeonlab.service.BlueToothService";
        method_BluetoothService_updateStrength = "a";
        method_BluetoothService_updateClientSide = "b";

        class_StrengthTouchListeners = Arrays.asList(
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$15",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$16",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$17",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$18"
        );

        class_RemoteSettingDialog = "com.bjsm.dungeonlab.widget.RemoteSettingDialog";
        field_RemoteSettingDialog_strengthA = "a_channel_strength_range";
        field_RemoteSettingDialog_strengthB = "b_channel_strength_range";
    }
}
