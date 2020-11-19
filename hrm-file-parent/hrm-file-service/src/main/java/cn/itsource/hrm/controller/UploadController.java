package cn.itsource.hrm.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.util.FastDfsApiOpr;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/fastdfs")
    public AjaxResult uploadFastdfs(MultipartFile file){
        //文件名
        String fileName = file.getOriginalFilename();
        //后缀
        String extName = fileName.substring(fileName.indexOf(".") + 1);
        //调用工具类上传
        try {
            String file_id = FastDfsApiOpr.upload(file.getBytes(), extName);
            return AjaxResult.me().setResultObj(file_id);
        } catch (Exception e) {
            e.printStackTrace();
            return  AjaxResult.me().setSuccess(false).setMessage("上传失败");
        }
    }



}
