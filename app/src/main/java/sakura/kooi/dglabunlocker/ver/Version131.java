package sakura.kooi.dglabunlocker.ver;

import java.util.Arrays;

import sakura.kooi.dglabunlocker.variables.InjectPoints;

public class Version131 extends AbstractVersionedCompatibilityProvider{
    @Override
    protected void initializeNames() {
        classGlobalVariables = "com.bjsm.dungeonlab.global.b";
        totalStrengthA = "as";
        localStrengthA = "ay";
        remoteStrengthA ="aw";
        maxStrengthA = "aw";

        totalStrengthB = "at";
        localStrengthB = "az";
        remoteStrengthB = "ax";
        maxStrengthB = "ax";

        isRemote = "by";

        classBugDialog = "com.bjsm.dungeonlab.widget.BugDialog";

        InjectPoints.classBluetoothServiceDecoder = "com.bjsm.dungeonlab.service.BlueToothService$18";
        InjectPoints.methodBluetoothServiceDecoder_Decode = "a";

        InjectPoints.classBluetoothService = "com.bjsm.dungeonlab.service.BlueToothService";
        InjectPoints.methodBluetoothServiceUpdateStrength = "b";

        InjectPoints.classTouchListeners = Arrays.asList(
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$15",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$16",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$17",
                "com.bjsm.dungeonlab.ui.activity.HomeActivity$18"
        );

        InjectPoints.classControlledTouchListeners = Arrays.asList(
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$24",
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$2",
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$25",
                "com.bjsm.dungeonlab.ui.activity.ControlledActivity$3"
        );
    }
}
