package cn.ravanla.flash.bean.constant.state;

/**
 * 业务是否成功的日志记录
 *
 * @author fengshuonan
 * @Date 2021年1月22日 下午12:14:59
 */
public enum LogSucceed {

    SUCCESS("成功"),
    FAIL("失败");

    String message;

    // LogSucceed 方法，用于记录日志成功的信息
    LogSucceed(String message) {
        // 记录日志消息
        this.message = message;
    }

    // getMessage 方法，用于获取日志消息
    public String getMessage() {
        // 返回 message 字段
        return message;
    }

    // setMessage 方法，用于设置日志消息
    public void setMessage(String message) {
        // 设置 message 字段
        this.message = message;
    }
}
