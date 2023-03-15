package cn.ravanla.flash.bean.dictmap.base;

import java.util.HashMap;

/**
 * 字典映射抽象类
 *
 * @author fengshuonan
 * @date 2021-05-06 14:58
 */
public abstract class AbstractDictMap {

    protected HashMap<String, String> dictory = new HashMap<>();
    protected HashMap<String, String> fieldWarpperDictory = new HashMap<>();

    public AbstractDictMap(){
        put("id","主键id");
        init();
        initBeWrapped();
    }

    /**
     * 初始化字段英文名称和中文名称对应的字典
     *
     * @author stylefeng
     * @Date 2021/5/9 19:39
     */
    public abstract void init();

    /**
     * 初始化需要被包装的字段(例如:性别为1:男,2:女,需要被包装为汉字)
     *
     * @author stylefeng
     * @Date 2021/5/9 19:35
     */
    protected abstract void initBeWrapped(); // 定义一个抽象方法，用于初始化包装器
    public String get(String key) { // 获取指定键的字符串值
        return this.dictory.get(key);
    }
    public void put(String key, String value) { // 将指定的键值对放入字典
        this.dictory.put(key, value);
    }
    public String getFieldWarpperMethodName(String key){ // 获取指定字段的包装器方法名
        return this.fieldWarpperDictory.get(key);
    }
    public void putFieldWrapperMethodName(String key,String methodName){ // 将指定的字段和包装器方法名放入字典
        this.fieldWarpperDictory.put(key,methodName);
    }
}
