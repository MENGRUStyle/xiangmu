package com.kai.oasys.controller;

import com.kai.oasys.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/upload")
public class UploadFileController {

    @RequestMapping(value = "/image",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> image(HttpServletRequest httpServletRequest,MultipartFile file)
    {
        Map<String,Object> map=new HashMap<>();

        //上传到那个相对路径
        String uploadPath=httpServletRequest.getServletContext().getRealPath("\\upload\\image\\");
        System.out.println("uploadPath="+uploadPath+"  "+file.getOriginalFilename());

        try {
            String image=UploadUtil.uploadFile(file,uploadPath);

            System.out.println("upload image="+image);
            map.put("code",0);
            map.put("image",image);
        } catch (IOException e) {
            map.put("code",1);
            e.printStackTrace();
        }

        return map;

    }
}
