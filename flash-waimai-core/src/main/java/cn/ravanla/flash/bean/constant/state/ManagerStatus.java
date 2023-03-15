package cn.ravanla.flash.bean.constant.state;

/**
 * 管理员的状态
 *
 * @author fengshuonan
 * @Date 2021年1月10日 下午9:54:13
 */
public enum ManagerStatus {

    OK(1, "启用"), FREEZED(2, "冻结"), DELETED(3, "被删除");

    int code;
    String message;

    // 构造函数，传入参数code和message
    ManagerStatus(int code, String message) {
        // 将传入的code赋值给当前对象的code
        this.code = code;
        // 将传入的message赋值给当前对象的message
        this.message = message;
    }

    // 获取当前对象的code
    public int getCode() {
        return code;
    }

    // 设置当前对象的code
    public void setCode(int code) {
        this.code = code;
    }

    // 获取当前对象的message
    public String getMessage() {
        return message;
    }

    // 设置当前对象的message
    public void setMessage(String message) {
        this.message = message;
    }

    // 通过value查找对应的message
    public static String valueOf(Integer value) {
        // 如果value为空，返回空字符串
        if (value == null) {
            return "";
        // 否则，遍历ManagerStatus的所有值
        } else {
            for (ManagerStatus ms : ManagerStatus.values()) {
                // 如果当前对象的code和value相等，返回当前对象的message
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            // 如果没有找到，返回空字符串
            return "";
        }
    }
}
