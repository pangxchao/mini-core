/**
 * Created the com.cfinal.util.lang.CFClazz.java
 *
 * @created 2016骞�8鏈�14鏃� 涓婂崍10:22:25
 * @version 1.0.0
 */
package sn.mini.java.util.lang;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * java.lang.Class宸ュ叿绫�
 *
 * @author xchao
 */
public class ClassUtil {
    /**
     * 璋冪敤 Class.forName() 骞惰浆鎹㈡垚 clazz 瀵瑰簲绫荤殑瀛愮被Class瀵硅薄
     *
     * @param name
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> Class<? extends T> forName(String name, Class<T> clazz) {
        try {
            return Class.forName(name, false, ClassUtil.getDefaultClassLoader()).asSubclass(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 灏� 鍔犺浇 绫诲悕涓� name 鐨勭被,灏嗗己鍒惰浆鎹㈡垚 T鐨勫瓙绫籆lass瀵硅薄,鐒跺悗鍒涘缓璇ュ璞″疄渚嬭繑鍥�
     *
     * @param name
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T newInstence(String name, Class<T> clazz) {
        try {
            return Class.forName(name, false, ClassUtil.getDefaultClassLoader()).asSubclass(clazz).getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 灏� name 绫� 灏嗗己鍒惰浆鎹㈡垚 T鐨勫瓙绫籆lass瀵硅薄,鐒跺悗鍒涘缓璇ュ璞″疄渚嬭繑鍥�
     *
     * @param name
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T newInstence(Class<?> name, Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 鍒涘缓clazz绫荤殑瀹炰緥
     *
     * @param clazz
     * @return
     */
    public static <T> T newInstence(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 鑾峰彇榛樿绫诲姞杞藉櫒
     *
     * @return Thread.currentThread().getContextClassLoader() || ClassUtil.class.getClassLoader()
     */
    public static ClassLoader getDefaultClassLoader() {
        try {
            return Thread.currentThread().getContextClassLoader();
        } catch (Throwable e) {
            return ClassUtil.class.getClassLoader();
        }
    }

    /**
     * 鎵弿鎵�鏈夋櫘閫氬寘涓嬬殑绫伙紝闈瀓ar鍖呬笅鐨勭被銆� 鎵弿鏃朵細璺宠繃鍐呴儴绫伙紝鍜屼笉鏄互 publick 淇グ绗︿慨鏀圭殑绫汇��
     *
     * @param packageName 鎸囧畾鍖呭悕锛屾壂鎻忔墍鏈夊寘鏃朵紶null鎴栬�� ""
     * @param recursive   鏄惁閫掑綊鎵弿锛� 濡傛灉涓簍rue:鍒欐壂鎻忓綋鍓嶅寘鍙婂綋鍓嶅寘涓嬬殑鎵�鏈夊瓙鍖�
     * @param annotation  鎸囧畾娉ㄨВ锛屽鏋滀笉涓虹┖锛岃繑鍥炵粨鏋滀腑鐨勭被鍙寘鍚湁褰撳墠瑙ｅ喅鐨勭被
     */
    public static List<Class<?>> scanning(String packageName, boolean recursive,
                                          Class<? extends Annotation> annotation) {
        if (StringUtil.isBlank(packageName)) {
            packageName = "";
        }
        List<Class<?>> clazzList = new ArrayList<>();
        scanning(clazzList, packageName, recursive, annotation);
        return clazzList;
    }

    /**
     * 鎵弿鎵�鏈塲ar鍖呬腑鐨勭被锛屾櫘閫氬寘涓嬬殑class鏃犳硶鎵弿 銆� 鎵弿鏃朵細璺宠繃鍐呴儴绫伙紝鍜屼笉鏄互 publick 淇グ绗︿慨鏀圭殑绫汇��
     *
     * @param packageName 鎸囧畾鍖呭悕锛屾壂鎻忔墍鏈夊寘鏃朵紶null鎴栬�� ""
     * @param recursive   鏄惁閫掑綊鎵弿锛� 濡傛灉涓簍rue:鍒欐壂鎻忓綋鍓嶅寘鍙婂綋鍓嶅寘涓嬬殑鎵�鏈夊瓙鍖�
     * @param annotation  鎸囧畾娉ㄨВ锛屽鏋滀笉涓虹┖锛岃繑鍥炵粨鏋滀腑鐨勭被鍙寘鍚湁褰撳墠瑙ｅ喅鐨勭被
     */
    public static List<Class<?>> scanningjars(String packageName, boolean recursive,
                                              Class<? extends Annotation> annotation) {
        if (StringUtil.isBlank(packageName)) {
            packageName = "";
        }
        List<Class<?>> clazzList = new ArrayList<>();
        scanning_jar(clazzList, packageName, recursive, annotation);
        return clazzList;
    }

    /**
     * 鎵弿鎵�鏈夋櫘閫氬寘鍜宩ar鍖呬腑鐨勭被銆� 鎵弿鏃朵細璺宠繃鍐呴儴绫伙紝鍜屼笉鏄互 publick 淇グ绗︿慨鏀圭殑绫汇��
     *
     * @param packageName 鎸囧畾鍖呭悕锛屾壂鎻忔墍鏈夊寘鏃朵紶null鎴栬�� ""
     * @param recursive   鏄惁閫掑綊鎵弿锛� 濡傛灉涓簍rue:鍒欐壂鎻忓綋鍓嶅寘鍙婂綋鍓嶅寘涓嬬殑鎵�鏈夊瓙鍖�
     * @param annotation  鎸囧畾娉ㄨВ锛屽鏋滀笉涓虹┖锛岃繑鍥炵粨鏋滀腑鐨勭被鍙寘鍚湁褰撳墠瑙ｅ喅鐨勭被
     */
    public static List<Class<?>> scanningall(String packageName, boolean recursive,
                                             Class<? extends Annotation> annotation) {
        if (StringUtil.isBlank(packageName)) {
            packageName = "";
        }
        List<Class<?>> clazzList = new ArrayList<>();
        scanning(clazzList, packageName, recursive, annotation);
        scanning_jar(clazzList, packageName, recursive, annotation);
        return clazzList;
    }

    /**
     * 鎵弿鎵�鏈夋櫘閫氬寘涓嬬殑绫伙紝闈瀓ar鍖呬笅鐨勭被
     *
     * @param clazzList   鎵弿缁撴灉瀹瑰櫒
     * @param packageName 鎸囧畾鍖呭悕锛屾壂鎻忔墍鏈夊寘鏃朵紶null鎴栬�� ""
     * @param recursive   鏄惁閫掑綊鎵弿锛� 濡傛灉涓簍rue:鍒欐壂鎻忓綋鍓嶅寘鍙婂綋鍓嶅寘涓嬬殑鎵�鏈夊瓙鍖�
     * @param annotation  鎸囧畾娉ㄨВ锛屽鏋滀笉涓虹┖锛岃繑鍥炵粨鏋滀腑鐨勭被鍙寘鍚湁褰撳墠瑙ｅ喅鐨勭被
     */
    private static void scanning(List<Class<?>> clazzList, String packageName, //
                                 boolean recursive, Class<? extends Annotation> annotation) {
        try {
            ClassLoader classLoader = getDefaultClassLoader();
            String packagePath = packageNameToFilePath(packageName);
            Enumeration<URL> urls = classLoader.getResources(packagePath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if ("file".equals(url.getProtocol())) {
                    scanning(clazzList, url.toURI(), packageName, recursive, annotation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * 鎵弿鎵�鏈塲ar鍖呬腑鐨勭被锛屾櫘閫氬寘涓嬬殑class鏃犳硶鎵弿
     *
     * @param clazzList   鎵弿缁撴灉瀹瑰櫒
     * @param packageName 鎸囧畾鍖呭悕锛屾壂鎻忔墍鏈夊寘鏃朵紶null鎴栬�� ""
     * @param recursive   鏄惁閫掑綊鎵弿锛� 濡傛灉涓簍rue:鍒欐壂鎻忓綋鍓嶅寘鍙婂綋鍓嶅寘涓嬬殑鎵�鏈夊瓙鍖�
     * @param annotation  鎸囧畾娉ㄨВ锛屽鏋滀笉涓虹┖锛岃繑鍥炵粨鏋滀腑鐨勭被鍙寘鍚湁褰撳墠瑙ｅ喅鐨勭被
     */
    private static void scanning_jar(List<Class<?>> clazzList, String packageName, //
                                     boolean recursive, Class<? extends Annotation> annotation) {
        ClassLoader classLoader = getDefaultClassLoader();
        scanning_jar(classLoader, clazzList, packageName, recursive, annotation);
    }

    /**
     * 鑾峰彇鎵�鏈夋櫘閫氬寘锛堟枃浠跺す锛夐噷鐨勬墍鏈夌被
     *
     * @param clazzList   鎵弿缁撴灉瀹瑰櫒
     * @param packageURI  绫绘墍灞炶矾寰�
     * @param packageName 鍖呭悕锛� com.ms.mvc.controller
     * @param recursive   鏄惁閫掑綊鏌ヨ鎵�鏈夌被 锛� true: 鏄� 锛� false锛� 鍚�
     * @param annotation  鎸囧畾娉ㄨВ, null锛� 涓嶆寚瀹�
     */
    private static void scanning(List<Class<?>> clazzList, URI packageURI, String packageName, //
                                 boolean recursive, Class<? extends Annotation> annotation) {
        File[] files = getClassFileOrDirector(packageURI);
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            scanningToClazzList(clazzList, file, packageURI, packageName, //
                                recursive, annotation);
        }
    }

    /**
     * 鑾峰彇鎵�鏈夋櫘閫氬寘锛堟枃浠跺す锛夐噷鐨勬墍鏈夌被
     *
     * @param clazzList   鎵弿缁撴灉瀹瑰櫒
     * @param file        鏂囦欢鎴栬�呮枃浠跺す瀵硅薄
     * @param packageURI
     * @param packageName 鍖呭悕锛� com.ms.mvc.controller
     * @param recursive   鏄惁閫掑綊鏌ヨ鎵�鏈夌被 锛� true: 鏄� 锛� false锛� 鍚�
     * @param annotation  鎸囧畾娉ㄨВ, null锛� 涓嶆寚瀹�
     */
    private static void scanningToClazzList(List<Class<?>> clazzList, File file, URI packageURI, String packageName, boolean recursive, Class<? extends
            Annotation> annotation) {
        try {
            String fileName = file.getName();
            if (file.isFile() && file.getName().endsWith(".class")) {
                classNameToResultList(clazzList, getClassAllNameByFilePath(
                        packageName, fileName), annotation);
            } else if (recursive && file.isDirectory()) {
                scanning(clazzList, file.toURI(), packageName + "." + fileName, true, annotation);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * 鎵弿鎵�鏈塲ar鍖呬腑鐨勭被锛屾櫘閫氬寘涓嬬殑class鏃犳硶鎵弿
     *
     * @param classLoader 绫诲姞杞藉櫒
     * @param clazzList   鎵弿缁撴灉瀹瑰櫒
     * @param packageName 鎸囧畾鍖呭悕锛屾壂鎻忔墍鏈夊寘鏃朵紶null鎴栬�� ""
     * @param recursive   鏄惁閫掑綊鎵弿锛� 濡傛灉涓簍rue:鍒欐壂鎻忓綋鍓嶅寘鍙婂綋鍓嶅寘涓嬬殑鎵�鏈夊瓙鍖�
     * @param annotation  鎸囧畾娉ㄨВ锛屽鏋滀笉涓虹┖锛岃繑鍥炵粨鏋滀腑鐨勭被鍙寘鍚湁褰撳墠瑙ｅ喅鐨勭被
     */
    private static void scanning_jar(ClassLoader classLoader, List<Class<?>> clazzList, String packageName,
                                     boolean recursive, Class<? extends Annotation> annotation) {
        try {
            if (classLoader instanceof URLClassLoader) {
                for (URL url : ((URLClassLoader) classLoader).getURLs()) {
                    if (url.getFile().endsWith(".jar")) {
                        URL jarUrl = new URL("jar:" + url.toString() + "!/");
                        scanning_jar(clazzList, jarUrl, packageName, recursive, annotation);
                    }
                }
            }
            if (classLoader != null) {
                scanning_jar(classLoader.getParent(), clazzList, packageName, recursive, annotation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 鎵弿鎵�鏈塲ar鍖呬腑鐨勭被锛屾櫘閫氬寘涓嬬殑class鏃犳硶鎵弿
     *
     * @param clazzList   鎵弿缁撴灉瀹瑰櫒
     * @param packageName 鎸囧畾鍖呭悕锛屾壂鎻忔墍鏈夊寘鏃朵紶null鎴栬�� ""
     * @param jarUrl      jar鍖匲RL璺緞
     * @param recursive   鏄惁閫掑綊鎵弿锛� 濡傛灉涓簍rue:鍒欐壂鎻忓綋鍓嶅寘鍙婂綋鍓嶅寘涓嬬殑鎵�鏈夊瓙鍖�
     * @param annotation  鎸囧畾娉ㄨВ锛屽鏋滀笉涓虹┖锛岃繑鍥炵粨鏋滀腑鐨勭被鍙寘鍚湁褰撳墠瑙ｅ喅鐨勭被
     */
    private static void scanning_jar(List<Class<?>> clazzList, URL jarUrl, String packageName, boolean recursive,
                                     Class<? extends Annotation> annotation) {
        try {
            Enumeration<JarEntry> jarEntries = ((JarURLConnection) jarUrl.openConnection()).getJarFile().entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                if (jarEntry.getName() != null && jarEntry.getName().endsWith(".class")) {
                    String clazzName = jarEntry.getName().replaceAll("([/\\\\])", ".");
                    clazzName = clazzName.substring(0, clazzName.length() - 6);
                    int lastIndex = clazzName.lastIndexOf(".");
                    String prefix = clazzName.substring(0, ((lastIndex < 0) ? 0 : lastIndex));
                    if ((recursive && prefix.startsWith(packageName)) || prefix.equals(packageName)) {
                        classNameToResultList(clazzList, clazzName, annotation);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 灏� Class 娣诲姞鍒� clazzList
     *
     * @param clazzList
     * @param clazzName
     * @param annotation
     */
    private static void classNameToResultList(List<Class<?>> clazzList, String clazzName,
                                              Class<? extends Annotation> annotation) {
        try {
            if (StringUtil.isNotBlank(clazzName)) {
                Class<?> clazz = Class.forName(clazzName, false, getDefaultClassLoader());
                if (clazz != null && clazzName.equals(clazz.getCanonicalName())
                        && Modifier.isPublic(clazz.getModifiers())) {
                    if (annotation != null && clazz.getAnnotation(annotation) == null) {
                        return;
                    }
                    clazzList.add(clazz);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * 灏嗗寘鍚嶈浆鎹㈡垚鍖呰矾寰�
     *
     * @param packageName
     */
    private static String packageNameToFilePath(String packageName) {
        if (StringUtil.isBlank(packageName)) {
            return "";
        }
        return packageName.replaceAll("[.]", "/");
    }

    /**
     * 鏍规嵁鍖呭悕鍜� .class 鏂囦欢鍚嶈幏鍙栧綋鍓� .class 鏂囦欢鎵�鍦ㄧ被鐨勫叏鍚�
     *
     * @param packageName 鍖呭悕
     * @param fileName    .class 鏂囦欢鍚�
     */
    private static String getClassAllNameByFilePath(String packageName, String fileName) {
        return packageName + "." + (fileName.substring(0, fileName.length() - 6));
    }

    /**
     * 鑾峰彇褰撳墠鍖呰矾寰勪笅鐨勬墍鏈夌被鏂囦欢鍜屾枃浠跺す
     *
     * @param packageURI
     */
    private static File[] getClassFileOrDirector(URI packageURI) {
        return new File(packageURI).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
    }
}
