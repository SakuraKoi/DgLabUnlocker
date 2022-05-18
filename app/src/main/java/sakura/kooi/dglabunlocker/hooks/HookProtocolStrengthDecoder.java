package sakura.kooi.dglabunlocker.hooks;

import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import sakura.kooi.dglabunlocker.variables.InjectPoints;

public class HookProtocolStrengthDecoder extends AbstractHook<HookProtocolStrengthDecoder.IProtocolDecoderInterceptor> {
    public HookProtocolStrengthDecoder() {
        super(IProtocolDecoderInterceptor.class);
    }

    @Override
    public String getName() {
        return "强度协议解码";
    }

    @Override
    public void apply(Context context, ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod(InjectPoints.class_BluetoothServiceDecoder, classLoader, InjectPoints.method_BluetoothServiceDecoder_decode, byte[].class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        callHandlers(handler -> {
                            handler.beforeStrengthDecode(context);
                        });
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        callHandlers(handler -> {
                            handler.afterStrengthDecode(context);
                        });
                    }
                });
    }

    public interface IProtocolDecoderInterceptor {
        void beforeStrengthDecode(Context context) throws ReflectiveOperationException;
        void afterStrengthDecode(Context context) throws ReflectiveOperationException;
    }
}
