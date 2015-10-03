package com.gaoxin.toy4j.framework;

import com.alibaba.fastjson.JSON;
import com.gaoxin.toy4j.framework.bean.Data;
import com.gaoxin.toy4j.framework.bean.Handler;
import com.gaoxin.toy4j.framework.bean.Param;
import com.gaoxin.toy4j.framework.bean.View;
import com.gaoxin.toy4j.framework.core.BeanHolder;
import com.gaoxin.toy4j.framework.core.ControllerHolder;
import com.gaoxin.toy4j.framework.helper.ConfigHelper;
import com.gaoxin.toy4j.framework.utils.CodecUtil;
import com.gaoxin.toy4j.framework.utils.ReflectionUtil;
import com.gaoxin.toy4j.framework.utils.StreamUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gaoxin.wei
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        BootstrapLoader.init();
        ServletContext servletContext = config.getServletContext();

        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 根据请求获取对应的handler
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        Handler handler = ControllerHolder.INSTANCE.getHandler(requestMethod, requestPath);
        if (handler == null) return;

        // 2. 拼凑出对应的入参param
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameter = req.getParameter(parameterName);
            paramMap.put(parameterName, parameter);
        }

        String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
        if (StringUtils.isNotBlank(body)) {
            String[] params = StringUtils.split(body, "&");
            if (ArrayUtils.isNotEmpty(params)) {
                for (String p : params) {
                    String[] st = StringUtils.split(p, "=");
                    if (ArrayUtils.isNotEmpty(st)) {
                        String paramName = st[0];
                        String paramValue = st[1];
                        paramMap.put(paramName, paramValue);
                    }
                }
            }
        }
        Param param = new Param(paramMap);

        // 3. 反射调用action
        Class<?> controllerClass = handler.getControllerClass();
        Object controllerBean = BeanHolder.INSTANCE.getBean(controllerClass);
        Method actionMethod = handler.getMethod();
        Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);

        // 4. 根据返回类型 返回结果
        if (result instanceof View) {
            View view = (View)result;
            String path = view.getPath();
            if (StringUtils.isNotBlank(path)) {
               if (StringUtils.startsWith(path, "/")) {
                   resp.sendRedirect(req.getContextPath() + path);
               } else {
                   Map<String, Object> model = view.getModel();
                   for (Map.Entry<String, Object> entry : model.entrySet()) {
                       req.setAttribute(entry.getKey(), entry.getValue());
                   }
                   req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
               }
            }
        } else if (result instanceof Data) {
            Data data = (Data)result;
            Object model = data.getModel();
            if (model != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter pw = resp.getWriter();
                String json = JSON.toJSONString(model);
                pw.write(json);
                pw.flush();
                pw.close();
            }
        }
    }

}
