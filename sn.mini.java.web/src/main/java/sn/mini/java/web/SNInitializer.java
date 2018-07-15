/**
 * Created the sn.mini.java.web.SNInitializer.java
 *
 * @created 2017年10月9日 下午1:34:58
 * @version 1.0.0
 */
package sn.mini.java.web;

import sn.mini.java.util.lang.ClassUtil;
import sn.mini.java.util.lang.MethodUtil;
import sn.mini.java.util.lang.StringUtil;
import sn.mini.java.util.lang.TypeUtil;
import sn.mini.java.util.lang.reflect.SNMethod;
import sn.mini.java.util.logger.Log;
import sn.mini.java.web.annotaion.Action;
import sn.mini.java.web.annotaion.Before;
import sn.mini.java.web.annotaion.Control;
import sn.mini.java.web.filter.SNAbstractFilter;
import sn.mini.java.web.http.ActionProxy;
import sn.mini.java.web.http.Controller;
import sn.mini.java.web.http.DefaultHttpServlet;
import sn.mini.java.web.http.interceptor.Interceptor;
import sn.mini.java.web.http.rander.IRender;
import sn.mini.java.web.http.view.IView;
import sn.mini.java.web.http.view.JspView;
import sn.mini.java.web.task.ITask;

import javax.servlet.*;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * sn.mini.java.web.SNInitializer.java
 *
 * @author XChao
 */

@HandlesTypes({SNConfig.class, Controller.class, Interceptor.class, IRender.class, IView.class, ServletContextListener.class,
        ServletContextAttributeListener.class, HttpSessionListener.class, HttpSessionAttributeListener.class, HttpSessionIdListener.class,
        ServletRequestListener.class, ServletRequestAttributeListener.class, SNAbstractFilter.class})
public final class SNInitializer implements ServletContainerInitializer {
    private static final Class[] INTERS_EMPTY = new Class[]{};

    private static boolean LOAD_COMPLETE = false;
    private static ServletContext servletContext;
    private static String encoding = "UTF-8"; // 设置编码， 用户系统中的乱码处理
    private static String loginUrl = null; // 设置登录地址, 验证登录的控制器可以自动跳转
    private static String accessControlAllowOrigin;

    private static int fileSizeThreshold = 4096; // 上传文件缓冲区大小
    private static long maxFileSize = -1; // 上传文件单个文件最大限制
    private static long maxRequestSize = -1; // 上传文件单次上传最大限制
    private static String location = "/temp"; // 文件上传临时目录
    private static Class<? extends IView> view = JspView.class; // 视图实现类

    private static final Map<String, ActionProxy> actionProxys = new HashMap<>();
    private static final Map<Class<? extends Controller>, Controller> controllers = new HashMap<>();
    private static final Map<Class<? extends Interceptor>, Interceptor> interceptors = new HashMap<>();
    private static final Map<Class<? extends IRender>, IRender> renders = new HashMap<>();
    private static final Map<Class<? extends IView>, IView> views = new HashMap<>();
    private static final Map<Class<? extends ITask>, ITask> tasks = new HashMap<>();
    private static final Map<Class<? extends EventListener>, EventListener> listeners = new HashMap<>();
    private static final Map<Class<? extends Filter>, Filter> filters = new HashMap<>();
    // 系统公共参数
    private static final Map<String, String> paranamer = new ConcurrentHashMap<>();

    /**
     * 得到web应用的物理地址
     *
     * @return 得到web应用的实际物理根地址
     */
    public static String getWebRealPath() {
        return servletContext.getRealPath("/") + "/";
    }

    /**
     * 获取web地址的根地址,该问该web项目的根URL
     *
     * @return web地址的根地址
     */
    public static String getWebRootPath() {
        return servletContext.getContextPath() + "/";
    }

    /**
     * @return the servletContext
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @param servletContext the servletContext to set
     */
    public static void setServletContext(ServletContext servletContext) {
        SNInitializer.servletContext = servletContext;
    }

    /**
     * @return the encoding
     */
    public static String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public static void setEncoding(String encoding) {
        SNInitializer.encoding = encoding;
    }

    /**
     * @return the loginUrl
     */
    public static String getLoginUrl() {
        return loginUrl;
    }

    /**
     * @param loginUrl the loginUrl to set
     */
    public static void setLoginUrl(String loginUrl) {
        SNInitializer.loginUrl = loginUrl;
    }

    /**
     * @return the accessControlAllowOrigin
     */
    public static String getAccessControlAllowOrigin() {
        return accessControlAllowOrigin;
    }

    /**
     * @param accessControlAllowOrigin the accessControlAllowOrigin to set
     */
    public static void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
        SNInitializer.accessControlAllowOrigin = accessControlAllowOrigin;
    }

    /**
     * @return the fileSizeThreshold
     */
    public static int getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    /**
     * @param fileSizeThreshold the fileSizeThreshold to set
     */
    public static void setFileSizeThreshold(int fileSizeThreshold) {
        SNInitializer.fileSizeThreshold = fileSizeThreshold;
    }

    /**
     * @return the maxFileSize
     */
    public static long getMaxFileSize() {
        return maxFileSize;
    }

    /**
     * @param maxFileSize the maxFileSize to set
     */
    public static void setMaxFileSize(long maxFileSize) {
        SNInitializer.maxFileSize = maxFileSize;
    }

    /**
     * @return the maxRequestSize
     */
    public static long getMaxRequestSize() {
        return maxRequestSize;
    }

    /**
     * @param maxRequestSize the maxRequestSize to set
     */
    public static void setMaxRequestSize(long maxRequestSize) {
        SNInitializer.maxRequestSize = maxRequestSize;
    }

    /**
     * @return the location
     */
    public static String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public static void setLocation(String location) {
        SNInitializer.location = location;
    }

    /**
     * @return the view
     */
    public static Class<? extends IView> getView() {
        return SNInitializer.view;
    }

    /**
     * @param view the view to set
     */
    public static void setView(Class<? extends IView> view) {
        SNInitializer.view = view;
    }

    /**
     * @return the actionproxys
     */
    public static Map<String, ActionProxy> getActionProxys() {
        return SNInitializer.actionProxys;
    }

    public static ActionProxy getActionProxy(String name) {
        return SNInitializer.actionProxys.get(name);
    }

    public static void addActionProxy(ActionProxy actionProxy) {
        SNInitializer.actionProxys.put(actionProxy.getName(), actionProxy);
    }

    /**
     * @return the controllers
     */
    public static Map<Class<? extends Controller>, Controller> getControllers() {
        return SNInitializer.controllers;
    }

    public static Controller getController(Class<? extends Controller> controller) {
        return SNInitializer.controllers.get(controller);
    }

    public static void addController(Controller controller) {
        SNInitializer.controllers.put(controller.getClass(), controller);
    }

    /**
     * @return the interceptors
     */
    public static Map<Class<? extends Interceptor>, Interceptor> getInterceptors() {
        return SNInitializer.interceptors;
    }

    public static Interceptor getInterceptor(Class<? extends Interceptor> interceptor) {
        return SNInitializer.interceptors.get(interceptor);
    }

    public static void addInterceptor(Interceptor interceptor) {
        SNInitializer.interceptors.put(interceptor.getClass(), interceptor);
    }

    /**
     * @return the renders
     */
    public static Map<Class<? extends IRender>, IRender> getRenders() {
        return SNInitializer.renders;
    }

    public static IRender getRender(Class<? extends IRender> render) {
        return SNInitializer.renders.get(render);
    }

    public static void addRender(IRender render) {
        SNInitializer.renders.put(render.getClass(), render);
    }

    /**
     * @return the views
     */
    public static Map<Class<? extends IView>, IView> getViews() {
        return SNInitializer.views;
    }

    public static IView getView(Class<? extends IView> view) {
        return SNInitializer.views.get(view);
    }

    public static void addView(IView view) {
        SNInitializer.views.put(view.getClass(), view);
    }

    /**
     * @return the tasks
     */
    public static Map<Class<? extends ITask>, ITask> getTasks() {
        return SNInitializer.tasks;
    }

    public static ITask getTask(Class<? extends ITask> task) {
        return SNInitializer.tasks.get(task);
    }

    public static void addTask(ITask task) {
        SNInitializer.tasks.put(task.getClass(), task);
    }

    /**
     * @return the listeners
     */
    public static Map<Class<? extends EventListener>, EventListener> getListeners() {
        return SNInitializer.listeners;
    }

    public static EventListener getListener(Class<? extends EventListener> listener) {
        return SNInitializer.listeners.get(listener);
    }

    public static void addListener(EventListener listener) {
        SNInitializer.listeners.put(listener.getClass(), listener);
    }

    /**
     * @return the filters
     */
    public static Map<Class<? extends Filter>, Filter> getFilters() {
        return SNInitializer.filters;
    }

    public static Filter getFilter(Class<? extends Filter> filter) {
        return SNInitializer.filters.get(filter);
    }

    public static void addFilter(Filter filter) {
        SNInitializer.filters.put(filter.getClass(), filter);
    }

    public static String get(String key) {
        return paranamer.get(key);
    }

    public static String getOrDef(String key, String def) {
        return paranamer.getOrDefault(key, def);
    }

    public static long getLong(String key) {
        return TypeUtil.castToLongValue(get(key));
    }

    public static long getLongOrDef(String key, long def) {
        return Optional.ofNullable(TypeUtil.castToLong(get(key))).orElse(def);
    }

    public static int getInt(String key) {
        return TypeUtil.castToIntValue(get(key));
    }

    public static int getIntOrDef(String key, int def) {
        return Optional.ofNullable(TypeUtil.castToInt(get(key))).orElse(def);
    }

    public static short getShort(String key) {
        return TypeUtil.castToShortValue(get(key));
    }

    public static short getShortOrDef(String key, short def) {
        return Optional.ofNullable(TypeUtil.castToShort(get(key))).orElse(def);
    }

    public static byte getByte(String key) {
        return TypeUtil.castToByteValue(get(key));
    }

    public static byte getByteOrDef(String key, byte def) {
        return Optional.ofNullable(TypeUtil.castToByte(get(key))).orElse(def);
    }

    public static double getDouble(String key) {
        return TypeUtil.castToDoubleValue(get(key));
    }

    public static double getDoubleOrDef(String key, double def) {
        return Optional.ofNullable(TypeUtil.castToDouble(get(key))).orElse(def);
    }

    public static float getFloat(String key) {
        return TypeUtil.castToFloatValue(get(key));
    }

    public static float getFloatOrDef(String key, float def) {
        return Optional.ofNullable(TypeUtil.castToFloat(get(key))).orElse(def);
    }

    public static boolean getBool(String key) {
        return TypeUtil.castToBoolValue(get(key));
    }

    public static boolean getBoolOrDef(String key, boolean def) {
        return Optional.ofNullable(TypeUtil.castToBool(get(key))).orElse(def);
    }

    public static Date getDate(String key) {
        return TypeUtil.castToDate(get(key));
    }

    public static Date getDateOrDef(String key, Date def) {
        return Optional.ofNullable(getDate(key)).orElse(def);
    }

    public static String set(String key, String value) {
        return paranamer.put(key, value);
    }

    public static void set(Map<String, String> values) {
        paranamer.putAll(values);
    }

    public static String remove(String key) {
        return paranamer.remove(key);
    }


    public void onStartup(Set<Class<?>> initializer, ServletContext servletContext) throws ServletException {
        try {
            SNInitializer.setServletContext(servletContext); // 设置ServletContext到
            List<Class<? extends SNConfig>> configurations = new ArrayList<>(); /// SNConfig
            List<Class<? extends Controller>> controllers = new ArrayList<>(); //// Controller
            List<Class<? extends Interceptor>> interceptors = new ArrayList<>(); // Controller
            List<Class<? extends IRender>> renders = new ArrayList<>(); /////////// IRender
            List<Class<? extends IView>> views = new ArrayList<>(); /////////////// IView
            List<Class<? extends EventListener>> listeners = new ArrayList<>(); /// EventListener
            List<Class<? extends SNAbstractFilter>> filters = new ArrayList<>(); //////////// Filter

            configurations.add(SNConfig.class); // 默认添加一个系统的基础配置
            for (Class<?> clazz : Optional.ofNullable(initializer).orElse(new HashSet<>())) {
                if (!(clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()))) {
                    if (SNConfig.class.isAssignableFrom(clazz)) { ////////////// SNConfig
                        configurations.add(clazz.asSubclass(SNConfig.class));
                    } else if (Controller.class.isAssignableFrom(clazz)) { ///// Controller
                        controllers.add(clazz.asSubclass(Controller.class));
                    } else if (Interceptor.class.isAssignableFrom(clazz)) { //// Interceptor
                        interceptors.add(clazz.asSubclass(Interceptor.class));
                    } else if (IRender.class.isAssignableFrom(clazz)) { //////// IRender
                        renders.add(clazz.asSubclass(IRender.class));
                    } else if (IView.class.isAssignableFrom(clazz)) { ////////// IView
                        views.add(clazz.asSubclass(IView.class));
                    } else if (EventListener.class.isAssignableFrom(clazz)) { // EventListener
                        listeners.add(clazz.asSubclass(EventListener.class));
                    } else if (SNAbstractFilter.class.isAssignableFrom(clazz)) {
                        filters.add(clazz.asSubclass(SNAbstractFilter.class));
                    }
                }
            }
            configurations.sort((v1, v2) -> v1.isAssignableFrom(v2) ? -1 : 1);
            for (Class<? extends SNConfig> configuration : configurations) { // SNConfig
                configuration.getConstructor().newInstance().initialize(servletContext);
            }

            views.sort((v1, v2) -> v1.isAssignableFrom(v2) ? -1 : 1);
            for (Class<? extends IView> view : views) { // IView
                SNInitializer.addView(view.newInstance());
            }

            renders.sort((v1, v2) -> v1.isAssignableFrom(v2) ? -1 : 1);
            for (Class<? extends IRender> render : renders) {// IRender
                SNInitializer.addRender(render.newInstance());
            }

            interceptors.sort((v1, v2) -> v1.isAssignableFrom(v2) ? -1 : 1);
            for (Class<? extends Interceptor> interceptor : interceptors) { // Interceptor
                SNInitializer.addInterceptor(interceptor.newInstance());
            }

            listeners.sort((v1, v2) -> v1.isAssignableFrom(v2) ? -1 : 1);
            for (Class<? extends EventListener> listener : listeners) { // EventListener
                if (listener.getAnnotation(WebListener.class) == null) {
                    EventListener instance = ClassUtil.newInstance(listener);
                    servletContext.addListener(instance); // 添加到缓存
                    SNInitializer.addListener(instance);
                }
            }

            controllers.sort((v1, v2) -> v1.isAssignableFrom(v2) ? -1 : 1);
            this.controller(controllers, servletContext); // Controller

            filters.sort((v1, v2) -> v1.isAssignableFrom(v2) ? -1 : 1);
            for (Class<? extends SNAbstractFilter> filter : filters) { // Filter
                if (filter.getAnnotation(WebFilter.class) == null) {
                    SNAbstractFilter instance = filter.newInstance();
                    SNInitializer.addFilter(instance); // 添加到缓存
                    FilterRegistration.Dynamic dynamic = servletContext.addFilter(filter.getName(), instance);
                    if (instance.getUrlPatterns() != null && instance.getUrlPatterns().length > 0) {
                        dynamic.addMappingForUrlPatterns(instance.getDispatcherType(), true, instance.getUrlPatterns());
                    }
                }
            }
            // 设置初始化参数，
            Enumeration<String> names = servletContext.getInitParameterNames();
            for (String name; names.hasMoreElements(); ) {
                set((name = names.nextElement()), servletContext.getInitParameter(name));
            }
        } catch (Throwable throwable) {
            Log.error("SNInitializer.onStartup error. ", throwable);
        }
    }

    /**
     * Controller 处理
     *
     * @param controllers
     */
    protected void controller(List<Class<? extends Controller>> controllers, ServletContext context) {
        Dynamic register = context.addServlet("sn.mini.java.web.servlet", DefaultHttpServlet.class);
        for (Class<? extends Controller> controller : controllers) {
            Control control = Optional.ofNullable(controller.getAnnotation(Control.class)).orElse(default_control);
            String cUrl = StringUtil.defaultIfEmpty(control.url(), controller.getSimpleName());
            String cPath = StringUtil.defaultIfEmpty(control.name(), controller.getSimpleName());
            Optional<Before> before = Optional.ofNullable(controller.getAnnotation(Before.class));
            Class<? extends Interceptor>[] interceptors = before.map(Before::value).orElse(INTERS_EMPTY);
            try {
                Controller instance = controller.newInstance(); // 创建Controller实例
                for (Method actionMethod : controller.getMethods()) {
                    Optional.ofNullable(actionMethod.getAnnotation(Action.class)).ifPresent(action -> {
                        SNMethod method = MethodUtil.getSNMethod(actionMethod);
                        String aUrl = StringUtil.defaultIfEmpty(action.url(), method.getName());
                        String suffix = StringUtil.defaultIfEmpty(action.suffix(), control.suffix());
                        String url = StringUtil.join("/", cUrl, "/", aUrl).replaceAll("[/]+", "/");
                        url = StringUtil.join((url.endsWith("/") ? url.substring(0, url.length() - 1) : url), suffix);
                        String name = StringUtil.join(context.getContextPath(), url).replaceAll("[/]+", "/");

                        IView view = SNInitializer.getView(getView()); // 根据视力实现类Class获取视图实例
                        // 获取Page渲染方式实现的页面完整路径（该路径强制从WEB-INF文件夹开始）
                        String path = StringUtil.join("pages/", cPath, "/", method.getName()).replaceAll("[/]+", "/");
                        ActionProxy proxy = new ActionProxy(instance, control, method, action, name, path, url, SNInitializer.getRender(action.value()), view);

                        Optional<Before> before1 = Optional.ofNullable(method.getAnnotation(Before.class));
                        for (Class<? extends Interceptor> inter : before1.map(Before::value).orElse(interceptors)) {
                            proxy.addInterceptor(SNInitializer.getInterceptor(inter));
                        }
                        if (SNInitializer.getActionProxy(proxy.getName()) != null) {
                            throw new RuntimeException("Action : '" + name + "' exits. ");
                        }

                        SNInitializer.addController(instance); // 添加实例到 SNInitializer 对象中
                        SNInitializer.addActionProxy(proxy); // 将ActionProxy 对象添加到缓存中
                        // 注册 servlet Url
                        register.addMapping(name.replaceFirst(context.getContextPath(), ""));
                        Log.debug("Scanner Action: " + name);
                    });
                }
                File location = new File(SNInitializer.getLocation()); // 检查文件上传临时目录是否存在, 如果不存在则创建,否则可能会接收不到参数
                if (!location.exists() && location.mkdirs()) { // 如果该文件夹或者文件不存时, 则创建该文件夹或者文件
                }
                register.setMultipartConfig(new MultipartConfigElement(// // 设置文件上传配置信息
                        location.getAbsolutePath(), maxFileSize, maxRequestSize, fileSizeThreshold));
                register.setAsyncSupported(true); // 设置可以异步返回
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    /**
     * 默认如果控制器Control注解为空时，使用该注解实现
     *
     * @author xchao
     */
    private static final Control default_control = new Control() {
        public Class<? extends Annotation> annotationType() {
            return this.getClass();
        }

        public String name() {
            return "";
        }

        public String url() {
            return "";
        }

        public String suffix() {
            return ".html";
        }
    };
}
