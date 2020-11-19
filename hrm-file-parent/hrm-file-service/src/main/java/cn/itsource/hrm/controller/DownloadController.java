package cn.itsource.hrm.controller;

import cn.itsource.hrm.util.FastDfsApiOpr;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/download")
public class DownloadController {
    @GetMapping("/fastdfs")
    public void download(String fileId, HttpServletResponse response){
        //fileId 为/group1/M00...
        fileId = fileId.substring(1);//去掉第一个/
        //组名
        String goupName = fileId.substring(0,fileId.indexOf("/"));
        //后面的虚拟存储的文件名
        String fileName = fileId.substring(fileId.indexOf("/")+1);
        byte[] bytes = FastDfsApiOpr.download(goupName, fileName);
        if (bytes==null||bytes.length==0){
            throw new RuntimeException("文件不存在");
        }
        String downloadFileName = fileName.substring(fileName.lastIndexOf("/")+1);
        //准备文件输出流设置响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename="+downloadFileName);

        //文件下载
        ByteArrayInputStream in = null;
        OutputStream out = null;
        try {
            in = new ByteArrayInputStream(bytes);
            out = response.getOutputStream();
            IOUtils.copy(in,out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out!=null){
                    out.close();
                }
                if (in!=null){
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
