package cn.ravanla.flash.bean.constant.state;

/**
 * 业务日志类型
 *
 * @author fengshuonan
 * @Date 2021年1月22日 下午12:14:59
 */
public enum BizLogType {

    ALL(0, null),//全部日志
    BUSSINESS(1, "业务日志"),
    EXCEPTION(2, "异常日志");

    Integer val;
    String message;

    /**
     *BizLogType(Integer val, String message)
     *构造函数，用来初始化val和message的值
     */
    BizLogType(Integer val, String message) {
        this.val = val;
        this.message = message;
    }

    /**
     *getMessage()
     *获取message的值
     */
    public String getMessage() {
        return message;
    }

    /**
     *setMessage(String message)
     *设置message的值
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *getVal()
     *获取val的值
     */
    public Integer getVal() {
        return val;
    }

    /**
     *setVal(Integer val)
     *设置val的值
     */
    public void setVal(Integer val) {
        this.val = val;
    }

    /**
     *valueOf(Integer value)
     *根据value获取message的值
     */
    public static String valueOf(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (BizLogType bizLogType : BizLogType.values()) {
                if (bizLogType.getVal().equals(value)) {
                    return bizLogType.getMessage();
                }
            }
            return null;
        }
    }
}
