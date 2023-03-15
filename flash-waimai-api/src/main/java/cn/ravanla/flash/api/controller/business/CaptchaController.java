package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.cache.TokenCache;
import cn.ravanla.flash.utils.CaptchaCode;
import cn.ravanla.flash.utils.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

/**
 * Created  on 2020/1/5 0005.
 *
 *@Author ravanla
 */
// 生成一个验证码图片，并将图片以Base64编码的方式封装成JSON格式的响应数据返回给客户端
// 同时，该方法将验证码字符串保存在缓存中，
// 并将随机生成的验证码字符串ID作为响应数据的一部分返回给客户端，
// 以便客户端可以在后续请求中使用该ID来验证用户输入的验证码。
@RestController
public class CaptchaController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CaptchaController.class);
    @Autowired
    private TokenCache tokenCache;

    @RequestMapping(value = "/v1/captchas", method = RequestMethod.POST)
    public Object get() throws IOException {

        // 创建了一个ByteArrayOutputStream对象用于保存验证码图片的输出流。
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 使用CaptchaCode.getImageCode()方法生成一个60*20的验证码图片，并将验证码字符串和验证码图片输出流保存在一个Map中。
        Map<String, Object> map = CaptchaCode.getImageCode(60, 20, outputStream);

        // 生成一个随机的验证码ID，并将验证码字符串保存在缓存中，以验证码ID为key。
        String captchCodeId = UUID.randomUUID().toString();

        tokenCache.put(captchCodeId, map.get("strEnsure").toString().toLowerCase());
        logger.info("captchCode:{}", map.get("strEnsure").toString().toLowerCase());


        try {
            // 将验证码图片使用Base64编码，并将编码后的字符串封装成JSON格式的响应数据返回给客户端。
            ImageIO.write((BufferedImage) map.get("image"), "png", outputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            String base64 =  new String(encoder.encode(outputStream.toByteArray()));;
            String captchaBase64 = "data:image/png;base64," + base64.replaceAll("\r\n", "");
            return Rets.success(Maps.newHashMap("captchCodeId",captchCodeId,"code",captchaBase64));
        } catch (IOException e) {
            // 如果在编码过程中发生了异常，则将异常信息封装成JSON格式的错误响应数据返回给客户端。
            return Rets.failure(e.getMessage());
        }

    }
}
