package cn.ravanla.flash.bean.exception;

/**
 * 封装异常
 *
 * @author fengshuonan
 * @Date 2021/12/28 下午10:32
 */
public class ApplicationException extends RuntimeException {

    private Integer code;

    private String message;

    // 自定义异常类中的构造方法，接收一个枚举类型参数 ServiceExceptionEnum。
    // 在构造方法中，将枚举类型参数中定义的错误码和错误消息赋值给异常类中的属性 code 和 message。
    // 在抛出异常时，可以通过 code 和 message 属性获取对应的错误码和错误信息。
    // 这个方法的作用是方便开发人员在代码中抛出指定错误类型的异常。
    public ApplicationException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
