package sakura.kooi.dglabunlocker.ver;

import static sakura.kooi.dglabunlocker.variables.InjectPoints.class_HomeActivity;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_HomeActivity_strengthTextA;
import static sakura.kooi.dglabunlocker.variables.InjectPoints.field_HomeActivity_strengthTextB;

import java.lang.invoke.MethodHandles;

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

    protected abstract void initializeNames();

    public void initializeAccessors(ClassLoader classLoader) throws ReflectiveOperationException {
        initializeNames();
        MethodHandles.Lookup lookup = MethodHandles.lookup();

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
