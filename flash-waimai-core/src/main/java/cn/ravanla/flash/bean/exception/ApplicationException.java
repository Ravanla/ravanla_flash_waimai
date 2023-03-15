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
