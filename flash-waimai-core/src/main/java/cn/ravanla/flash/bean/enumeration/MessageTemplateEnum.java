package cn.ravanla.flash.bean.enumeration;

/**
 * descript
 *
 * @Author ravanla
 * @date ：Created in 2021/6/10 20:10
 */
public enum MessageTemplateEnum {
    /**
     * 注册验证码
     */
    REGISTER_CODE("REGISTER_CODE","注册验证码");
    private String code;
    private String name;
    MessageTemplateEnum(String code,String name){
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }}
