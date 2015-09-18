package com.ggasoftware.jdi_ui_tests.core.utils.usefulUtils;

import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTEx;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TryCatchUtil {
    public static <T> T tryGetResult(JFuncTEx<T> waitCase) {
        try { return waitCase.invoke(); }
        catch (Exception ex) { throw new RuntimeException(ex.getMessage()); }
    }
}
