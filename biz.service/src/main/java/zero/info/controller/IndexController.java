package zero.info.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zero.info.utils.SpringBeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
public class IndexController {
    /**
     * http相关参数
     **/
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_CONTENT_TYPE_NAME = "content-typ";
    private static final String DEFAULT_CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";


    @RequestMapping(value = "/service")
    public void service(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        Object result = null;
        String serviceName = request.getParameter("serviceName");
        String methodName = request.getParameter("methodName");
        String parameterInput = request.getParameter("paramterInput");
        boolean format = Boolean.valueOf(request.getParameter("format"));
        if (StringUtils.isEmpty(serviceName) || StringUtils.isEmpty(methodName)) {
            handleResponse(response, "error params", false);
            return;
        }
        if (parameterInput != null) {
            parameterInput = parameterInput.trim();
        }
        try {


            // 每个接口均存在InvokeParamVo参数，加入到参数列表
            // 1、单个参数{}，构造成[]
            // 2/多参数[]，直接将InvokeParamVo加在第一个参数
            List<String> paramList = new ArrayList<String>();
            Object bean;
            if (serviceName.contains(".")) {
                bean = SpringBeanUtils.getBean(Class.forName(serviceName));
            } else {
                bean = SpringBeanUtils.getBean(serviceName);
            }

            boolean isInvoked = false;
            // 必须用getInterfaces，这样才能取到方法参数的泛型，JSON参数才会争取
            if (bean.getClass().isInterface()) {
                // 获取接口或类的所有方法
                List<Method> methodList = new ArrayList<Method>();
                for (Class<?> beanInterface : bean.getClass().getInterfaces()) {
                    for (Method method : beanInterface.getMethods()) {
                        if (!methodList.contains(method)) {
                            methodList.add(method);
                        }
                    }
                }
                //cglib动态代理
                for (Method method : bean.getClass().getDeclaredMethods()) {
                    if (!methodList.contains(method)) {
                        methodList.add(method);
                    }
                }

                for (Method method : methodList) {
                    try {
                        if (methodName.equals(method.getName())) {
                            method.setAccessible(true);
                            Type[] types = method.getGenericParameterTypes();
                            List<Object> params = new ArrayList<Object>();
                            if (types.length == 1) {
                                paramList.add(parameterInput);
                            }
                            if (types.length > 1) {// 超过2个参数，将JSON参数转为List<String>，后续再逐个转为对应的对象
                                List<String> tmepList = JSON.parseArray(parameterInput, String.class);
                                paramList.addAll(tmepList);
                            }
                            if (types.length == paramList.size()) {// 参数数量必须相同
                                for (int i = 0; i < types.length; i++) {
                                    if (types[i] == String.class) {
                                        params.add(paramList.get(i));
                                    } else {
                                        params.add(JSON.parseObject(paramList.get(i), types[i]));
                                    }
                                }
                                isInvoked = true;
                                result = method.invoke(bean, params.toArray());
                                break;
                            }
                        }
                    } catch (Exception e) {
                        log.error("调用服务方法通信异常", e);
                    }
                    if (isInvoked) {
                        break;
                    }
                }
            } else {
                String className = bean.getClass().getName();
                if (className.indexOf("$$") != -1) {
                    className = className.substring(0, className.indexOf("$$"));
                }
                Method[] methods = Class.forName(className).getDeclaredMethods();
                //  非接口实现类
                for (Method method : methods) {
                    try {
                        if (methodName.equals(method.getName())) {
                            method.setAccessible(true);
                            Type[] types = method.getGenericParameterTypes();
                            List<Object> params = new ArrayList<Object>();
                            if (types.length == 1) {
                                paramList.add(parameterInput);
                            }
                            if (types.length > 1) {// 超过2个参数，将JSON参数转为List<String>，后续再逐个转为对应的对象
                                List<String> tmepList = JSON.parseArray(parameterInput, String.class);
                                paramList.addAll(tmepList);
                            }
                            if (types.length == paramList.size()) {// 参数数量必须相同
                                for (int i = 0; i < types.length; i++) {
                                    Object object = null;
                                    try {
                                        object = JSON.parseObject(paramList.get(i), types[i]);
                                    } catch (Exception e) {
                                        log.error("参数转换异常", e);
                                        object = paramList.get(i);
                                    }
                                    params.add(object);
                                }
                                isInvoked = true;
                                result = method.invoke(bean, params.toArray());
                                break;
                            }
                        }
                    } catch (Exception e) {
                        log.error("调用服务方法通信异常", e);
                    }
                    if (isInvoked) {
                        break;
                    }
                }

            }
        } catch (Exception e) {
            log.error("调用服务方法通信异常", e);
        } finally {
            handleResponse(response, result, format);
        }
    }


    /**
     * 处理输出结果
     *
     * @param response
     */
    private void handleResponse(HttpServletResponse response, Object result, boolean prettyFormat) {
        response.setCharacterEncoding(DEFAULT_CHARSET);
        response.setHeader(DEFAULT_CONTENT_TYPE_NAME, DEFAULT_CONTENT_TYPE_VALUE);
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            if (result instanceof String) {
                pw.print(result);
            } else {
                String data = JSON.toJSONString(result, prettyFormat);
                pw.print(data);
            }

        } catch (Exception e) {
            log.error("调用服务方法通信异常", e);
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();

            }
        }
    }
}
