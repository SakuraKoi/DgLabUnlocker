package sakura.kooi.dglabunlocker.utils;

import androidx.annotation.NonNull;

public class ModuleException extends RuntimeException {
    public ModuleException(String s) {
        super(s);
    }

    @NonNull
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
