package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.*;

import java.util.Arrays;

import sakura.kooi.dglabunlocker.variables.Accessors;

public class Version131 extends AbstractVersionedCompatibilityProvider {
    @Override
    protected void initializeAccessors() throws ReflectiveOperationException {
        String classGlobalVariables = "com.bjsm.dungeonlab.global.b";

        Accessors.totalStrengthA = lookupField(classGlobalVariables, "as");
        Accessors.totalStrengthB = lookupField(classGlobalVariables, "at");
        Accessors.remoteStrengthA = lookupField(classGlobalVariables, "aw");
        Accessors.remoteStrengthB = lookupField(classGlobalVariables, "ax");

        Accessors.localStrengthA = lookupField(classGlobalVariables, "ay"); // getRealStrengthA
        Accessors.localStrengthB = lookupField(classGlobalVariables, "az"); // getRealStrengthB
        Accessors.maxStrengthA = lookupField(classGlobalVariables, "aw"); // getStrengthRangeMax
        Accessors.maxStrengthB = lookupField(classGlobalVariables, "ax"); // getStrengthRangeMax
    }

    @Override
    protected void initializeNames() {
        class_BluetoothServiceDecoder = "com.bjsm.dungeonlab.service.BlueToothService$18";
        method_BluetoothServiceDecoder_decode = "a";

        class_BluetoothService = "com.bjsm.dungeonlab.service.BlueToothService";
        method_BluetoothService_updateStrength = "b";
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
