package sakura.kooi.dglabunlocker.utils;

import android.util.Log;

public class ExceptionLogger {
    public static void withCatch(String doing, ThrowableRunnable lambda) {
        try {
            lambda.run();
        } catch (Exception e) {
            Log.e("DgLabUnlocker", "An error occurred in " + doing, e);
        }
    }
    public interface ThrowableRunnable {
        void run() throws Exception;
    }
}
