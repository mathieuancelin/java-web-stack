package org.webstack.lib;

import com.google.common.base.Throwables;

public class Utils {

    public interface Function0<T> {
        T apply() throws Exception;
    }

    public static <T> T unsafe(Function0<T> f) {
        try {
            return f.apply();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
