package cn.ravanla.flash.security;

/**
 * @Author ravanla
 * @date ：Created in 2021/7/30 23:05
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}