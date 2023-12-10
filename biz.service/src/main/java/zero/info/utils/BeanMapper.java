
package zero.info.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Slf4j
public class BeanMapper {

    private static List<String> init() {
        List<String> files = Lists.newArrayList();
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath*:/config/dozer/*.xml");
            for (Resource resource : resources) {
                files.add(resource.getURL().toString());
            }

        } catch (IOException e) {
            log.error("dozer init err", e);
        }
        return files;
    }

    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper(init());

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(sourceList)){
            return destinationList;
        }
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * 基于Dozer将对象A的值拷贝到对象B中.
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

    public static <T> T copy(Object source, Class<T> clazz) {
        T destinationObject = null;
        try {
            destinationObject = clazz.newInstance();
            dozer.map(source, destinationObject);
        } catch (Exception e) {
            log.error("copy object error", e);
        }
        return destinationObject;
    }

}