package cn.ravanla.flash.api.controller;

import cn.ravanla.flash.bean.entity.system.FileInfo;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.service.system.FileService;
import cn.ravanla.flash.utils.CryptUtils;
import cn.ravanla.flash.utils.HttpKit;
import cn.ravanla.flash.utils.Maps;
import cn.ravanla.flash.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {
    @Autowired
    private static  final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @RequiresPermissions(value = {Permission.FILE_UPLOAD})
    public Object upload(@RequestPart("file") MultipartFile multipartFile) {

        try {
            FileInfo fileInfo = fileService.upload(multipartFile);
            return Rets.success(fileInfo);
        } catch (Exception e) {
            logger.error("上传文件异常",e);
            return Rets.failure("上传文件失败");
        }
    }

    /**
     * 下载文件
     * @param idFile
     * @param fileName
     */
    @RequestMapping(value="download",method = RequestMethod.GET)
    public void download(@RequestParam("idFile") Long idFile,
                           @RequestParam(value = "fileName",required = false) String fileName){
        FileInfo fileInfo = fileService.get(idFile);
        fileName = StringUtils.isEmpty(fileName)? fileInfo.getOriginalFileName():fileName;
        HttpServletResponse response = HttpKit.getResponse();
        response.setContentType("application/x-download");
        try {
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        OutputStream os = null;
        try {
            File file = new File(fileInfo.getAblatePath());
            os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer);
                i = bis.read(buffer);
            }

        } catch (Exception e) {
            logger.error("download error",e);
        }finally {
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                logger.error("close inputstream error", e);
            }
        }

    }

    /**
     * 获取base64图片数据
     * @param idFile
     * @return
     */
    @RequestMapping(value="getImgBase64",method = RequestMethod.GET)
    public Object getImgBase64(@RequestParam("idFile")Long idFile){

        FileInfo fileInfo = fileService.get(idFile);
        FileInputStream fis = null;
        try {
            File file = new File(fileInfo.getAblatePath());
            byte[] bytes = new byte[(int) file.length()];
            fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytes);
            String base64 = CryptUtils.encodeBASE64(bytes);
            return Rets.success(Maps.newHashMap("imgContent", base64));
        } catch (Exception e) {
            logger.error("get img error",e);
            return Rets.failure("获取图片异常");
        }finally{
            try {
                fis.close();
            }catch (Exception e){
                logger.error("close getImgBase64 error",e);
            }
        }

    }

    /**
     * 获取图片流
     * @param response
     * @param idFile
     */
    /*
OutputStream out = response.getOutputStream()：
File file = new File(fileInfo.getAblatePath())：
fis.read(b)：
out.write(b)：将数据写入输出流
fis.close()：关闭输入流
*/
    // @RequestMapping将HTTP请求映射到MVC和REST控制器的处理方法
    // value="getImgStream"：定义访问路径
    @RequestMapping(value="getImgStream",method = RequestMethod.GET)

    // @RequestParam注解用于从请求参数中获取参数值
    public void getImgStream(HttpServletResponse response,
                             @RequestParam(value = "idFile",required = false)Long idFile,
                             @RequestParam(value = "fileName",required = false)String fileName){
        FileInfo fileInfo = null;

        if(idFile!=null) {
            fileInfo = fileService.get(idFile);
        }else if(StringUtils.isNotEmpty(fileName)){
            fileInfo = fileService.getByName(fileName);
        }

        // 文件输入流
        FileInputStream fis = null;

        try {
            // 设置返回文件类型
            response.setContentType("image/"+fileInfo.getRealFileName().split("\\.")[1]);

            // 获取响应输出流
            OutputStream out = response.getOutputStream();

            // 根据文件路径获取文件
            File file = new File(fileInfo.getAblatePath());
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];

            // 从输入流中读取数据
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            logger.error("文件不存在",e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("close getImgStream error",e);
                }
            }
        }
    }
}
