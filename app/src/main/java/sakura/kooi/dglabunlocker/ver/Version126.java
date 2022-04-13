package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.GlobalVariables.*;

import android.content.Context;

import java.util.Arrays;

public class Version126 implements IDgLabField {
    @Override
    public void initDgLabFields(ClassLoader classLoader, Context context) throws ReflectiveOperationException {
        Class<?> clsGlobalVariable = Class.forName("com.bjsm.dungeonlab.global.b", true, classLoader);
        totalStrengthA = lookupField(clsGlobalVariable, "V");
        localStrengthA = lookupField(clsGlobalVariable, "ab");
        maxStrengthA = lookupField(clsGlobalVariable, "Z");

        totalStrengthB = lookupField(clsGlobalVariable, "W");
        localStrengthB = lookupField(clsGlobalVariable, "ac");
        maxStrengthB = lookupField(clsGlobalVariable, "aa");

        remoteStrengthA = lookupField(clsGlobalVariable, "X");
        remoteStrengthB = lookupField(clsGlobalVariable, "Y");

        isRemote = lookupField(clsGlobalVariable, "aZ");

        Class<?> clsHomeActivity = Class.forName("com.bjsm.dungeonlab.ui.activity.HomeActivity", true, classLoader);
        fieldHomeActivityBluetoothService = lookupField(clsHomeActivity, "aR");
        Class<?> clsBluetoothService = Class.forName("com.bjsm.dungeonlab.service.BlueToothService", true, classLoader);
        fieldBluetoothServiceIsController = lookupField(clsBluetoothService, "S");
        Class<?> clsBugDialog = Class.forName("com.bjsm.dungeonlab.widget.BugDialog", true, classLoader);
        constBugDialog = clsBugDialog.getDeclaredConstructor(Context.class);

        classBluetoothServiceDecoder = "com.bjsm.dungeonlab.service.BlueToothService$18";
        methodBluetoothServiceDecoder_Decode = "a";

        classBluetoothService = "com.bjsm.dungeonlab.service.BlueToothService";
        methodBluetoothServiceUpdateStrength = "a";

        classTouchListeners = new String[]{
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$15",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$16",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$17",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$18"
        };

        classControlledTouchListeners = Arrays.asList(
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$24",
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$2",
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$25",
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$3"
        );
    }
}
