package cn.ravanla.flash.api.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * fastjson配置类
 *
 * @author fengshuonan
 * @date 2021-05-23 22:56
 */
//声明这是一个配置类，并指定其名称为 defaultFastjsonConfig
@Configuration("defaultFastjsonConfig")
//只有当类路径中存在 com.alibaba.fastjson.JSON 类时，才会加载这个配置类
@ConditionalOnClass(com.alibaba.fastjson.JSON.class)
//只有当容器中不存在 FastJsonHttpMessageConverter 类型的 Bean 时，才会加载这个配置类
@ConditionalOnMissingBean(FastJsonHttpMessageConverter.class)
//只有当应用程序是一个 Web 应用程序时，才会加载这个配置类
@ConditionalOnWebApplication
public class DefaultFastjsonConfig {

    // 声明一个 Bean 方法，返回一个 FastJsonHttpMessageConverter 类型的对象
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        // 创建一个 FastJsonHttpMessageConverter 对象
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        // 设置该对象的 FastJsonConfig 属性为 fastjsonConfig() 方法返回的值
        converter.setFastJsonConfig(fastjsonConfig());
        // 设置该对象支持的媒体类型为 getSupportedMediaType() 方法返回的值
        converter.setSupportedMediaTypes(getSupportedMediaType());
        // 返回该对象
        return converter;
    }

    /**
     * fastjson的配置
     */
    public FastJsonConfig fastjsonConfig() {
        // 创建一个 FastJsonConfig 对象
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 设置该对象的序列化特性，包括格式化输出、输出空值、使用枚举类型的 toString 方法、禁用循环引用检测等
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.DisableCircularReferenceDetect
        );
        // 设置该对象的日期格式为 yyyy-MM-dd HH:mm:ss
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 创建一个 ValueFilter 对象，用于处理空值问题，将空值替换为空字符串
        ValueFilter valueFilter = new ValueFilter() {
            @Override
            public Object process(Object o, String s, Object o1) {
                if (null == o1) {
                    o1 = "";
                }
                return o1;
            }
        };
        // 设置该对象的字符集为 utf-8
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        // 设置该对象的序列化过滤器为 valueFilter
        fastJsonConfig.setSerializeFilters(valueFilter);

        // 解决Long转json精度丢失的问题
        // 获取全局的 SerializeConfig 对象
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        // 将 BigInteger 类型、Long 类型和 long 类型都注册为 ToStringSerializer 实例，即将数值类型转换为字符串类型
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        // 设置该对象的序列化配置为 serializeConfig
        fastJsonConfig.setSerializeConfig(serializeConfig);

        // 返回该对象
        return fastJsonConfig;
    }

    /**
     * 支持的mediaType类型
     */
    public List<MediaType> getSupportedMediaType() {
        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        return mediaTypes;
    }

}
