package sn.mini.java.util.http;


import sn.mini.java.util.lang.IOUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.file.Path;

public abstract class HttpResponse<T> {
    public abstract int getCode();

    public abstract String getMessage();

    public abstract T getBody();

    public boolean isSuccess() {
        return this.getCode() == HttpURLConnection.HTTP_OK;
    }

    public interface BodyHandler<T> {
        HttpResponse<T> apply(int code, String msg, InputStream is);

        static BodyHandler<String> asString() {
            //StandardCharsets.UTF_8.name()
            return asString(Charset.defaultCharset());
        }

        static BodyHandler<String> asString(Charset charset) {
            return (code, msg, is) -> new HttpResponse<String>() {
                public int getCode() {
                    return code;
                }

                public String getMessage() {
                    return msg;
                }

                public String getBody() {
                    if (this.isSuccess() && is != null) {
                        try (InputStream input = is) {
                            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                                IOUtil.copy(input, output);
                                return output.toString();
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                    return null;
                }
            };
        }

        static BodyHandler<InputStream> asStream() {
            return (code, msg, is) -> new HttpResponse<InputStream>() {
                public int getCode() {
                    return code;
                }

                public String getMessage() {
                    return msg;
                }

                public InputStream getBody() {
                    return is;
                }
            };
        }

        static BodyHandler<File> asFile(Path path) {
            return (code, msg, is) -> new HttpResponse<File>() {
                public int getCode() {
                    return code;
                }

                public String getMessage() {
                    return msg;
                }

                public File getBody() {
                    if (this.isSuccess() && is != null) {
                        try (InputStream input = is) {
                            File file = path.toFile();
                            try (OutputStream output = new FileOutputStream(file)) {
                                IOUtil.copy(input, output);
                                return file;
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                    return null;
                }
            };
        }
    }
}
