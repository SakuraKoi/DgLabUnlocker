package sakura.kooi.dglabunlocker.ver;

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

    protected String isRemote;

    protected String classBugDialog;

    protected abstract void initializeNames();

    public void initializeAccessors(ClassLoader classLoader, Context context) throws ReflectiveOperationException {
        initializeNames();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Class<?> clsGlobalVariable = Class.forName("com.bjsm.dungeonlab.global.b", true, classLoader);

        Accessors.localStrengthA = new FieldAccessor<>(lookup, clsGlobalVariable, localStrengthA);
        Accessors.totalStrengthA = new FieldAccessor<>(lookup, clsGlobalVariable, totalStrengthA);
        Accessors.maxStrengthA = new FieldAccessor<>(lookup, clsGlobalVariable, maxStrengthA);
        Accessors.remoteStrengthA = new FieldAccessor<>(lookup, clsGlobalVariable, remoteStrengthA);
        Accessors.localStrengthB = new FieldAccessor<>(lookup, clsGlobalVariable, localStrengthB);
        Accessors.totalStrengthB = new FieldAccessor<>(lookup, clsGlobalVariable, totalStrengthB);
        Accessors.maxStrengthB = new FieldAccessor<>(lookup, clsGlobalVariable, maxStrengthB);
        Accessors.remoteStrengthB = new FieldAccessor<>(lookup, clsGlobalVariable, remoteStrengthB);
        Accessors.isRemote = new FieldAccessor<>(lookup, clsGlobalVariable, isRemote);

        Class<?> clsBugDialog = Class.forName(classBugDialog, true, classLoader);
        Constructor<Dialog> constBugDialog = (Constructor<Dialog>) clsBugDialog.getDeclaredConstructor(Context.class);
        Accessors.constructorBugDialog = lookup.unreflectConstructor(constBugDialog);
    }
}