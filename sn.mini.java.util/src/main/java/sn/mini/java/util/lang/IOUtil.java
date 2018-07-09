package sn.mini.java.util.lang;

import sn.mini.java.util.logger.Log;

import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {
    /**
     * 需要调用者自己关闭流
     *
     * @param is
     * @param os
     */
    public static void copy(InputStream input, OutputStream out) {
        try {
            int length;
            byte[] buf = new byte[2048];
            while ((length = input.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
