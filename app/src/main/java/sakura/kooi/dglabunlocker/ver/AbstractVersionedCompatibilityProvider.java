package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_HomeActivity;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_RemoteSettingDialog;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_HomeActivity_strengthTextA;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_HomeActivity_strengthTextB;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_RemoteSettingDialog_strengthA;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_RemoteSettingDialog_strengthB;

import android.app.Dialog;
import android.content.Context;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

import sakura.kooi.dglabunlocker.utils.FieldAccessor;
import sakura.kooi.dglabunlocker.variables.Accessors;

public abstract class AbstractVersionedCompatibilityProvider {
    protected String classGlobalVariables;
    protected String localStrengthA;
    protected String totalStrengthA;
    protected String maxStrengthA;
    protected String remoteStrengthA;

    protected String localStrengthB;
    protected String totalStrengthB;
    protected String maxStrengthB;
    protected String remoteStrengthB;

    protected String classBugDialog;

    protected abstract void initializeNames();

    public void initializeAccessors(ClassLoader classLoader) throws ReflectiveOperationException {
        initializeNames();
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Class<?> clsBugDialog = Class.forName(classBugDialog, true, classLoader);
        Constructor<Dialog> constBugDialog = (Constructor<Dialog>) clsBugDialog.getDeclaredConstructor(Context.class);
        Accessors.constructorBugDialog = lookup.unreflectConstructor(constBugDialog);

        Class<?> clsGlobalVariable = Class.forName(classGlobalVariables, true, classLoader);

        Accessors.localStrengthA = new FieldAccessor<>(lookup, clsGlobalVariable, localStrengthA);
        Accessors.totalStrengthA = new FieldAccessor<>(lookup, clsGlobalVariable, totalStrengthA);
        Accessors.maxStrengthA = new FieldAccessor<>(lookup, clsGlobalVariable, maxStrengthA);
        Accessors.remoteStrengthA = new FieldAccessor<>(lookup, clsGlobalVariable, remoteStrengthA);
        Accessors.localStrengthB = new FieldAccessor<>(lookup, clsGlobalVariable, localStrengthB);
        Accessors.totalStrengthB = new FieldAccessor<>(lookup, clsGlobalVariable, totalStrengthB);
        Accessors.maxStrengthB = new FieldAccessor<>(lookup, clsGlobalVariable, maxStrengthB);
        Accessors.remoteStrengthB = new FieldAccessor<>(lookup, clsGlobalVariable, remoteStrengthB);

        if (class_HomeActivity != null) {
            Class<?> clsHomeActivity = Class.forName(class_HomeActivity, true, classLoader);
            Accessors.textHomeActivityStrengthA = new FieldAccessor<>(lookup, clsHomeActivity, field_HomeActivity_strengthTextA);
            Accessors.textHomeActivityStrengthB = new FieldAccessor<>(lookup, clsHomeActivity, field_HomeActivity_strengthTextB);
        }
    }
}
