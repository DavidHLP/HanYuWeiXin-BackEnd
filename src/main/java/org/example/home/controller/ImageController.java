package org.example.home.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.example.common.constant.Desource;
import org.example.home.controller.utils.FileUploader;
import org.example.home.controller.utils.UrlAndBollean;
import org.example.home.domain.ImageUrl;
import org.example.home.service.ImageService;
import org.example.system.controller.BaseController;
import org.example.system.domain.LoginUser;
import org.example.system.utils.JSONFileUtils;
import org.example.system.utils.ResultMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

/**
 * ImageController 处理与图像相关的请求。
 *
 * @since 1.0
 * @authordavid
 */
@RestController
@RequestMapping("/images")
public class ImageController extends BaseController {

    @Resource
    private ImageService imageService;

    /**
     * 获取图像列表接口
     *
     * @param parentId 父级ID
     * @return 包含查询结果的 ResultMap
     */
    @GetMapping("/list")
    public ResultMap list(@RequestParam("parentId") int parentId) {
        List<ImageUrl> res = imageService.selectById(parentId);
        return success("查询成功", res);
    }

    /**
     * 上传图像接口
     *
     * @return 包含上传结果的 ResultMap
     */
    @PostMapping("/fileUpLoad")
    public ResultMap fileUpload(@RequestParam("avatar") MultipartFile[] files, HttpServletRequest request) throws Exception {
        String path = request.getServletContext().getRealPath("./") + "/files/";
        System.out.println(request.getAttribute("loginUser"));
        LoginUser user = (LoginUser) request.getAttribute("loginUser");
        try {
            UrlAndBollean url = imageService.insertAndUpload(files, path , user);
            if (url.getFlag()) {
                return success("上传成功",url.getUrl());
            } else {
                return fail("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return fail( e.getMessage());
        }
    }

    @GetMapping("/getFilesName")
    public void getFilesName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getServletContext().getRealPath("/") + "files/files.json";
        String json = JSONFileUtils.readFile(path);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> fileDownload(HttpServletRequest request, @RequestParam("filename") String filename) throws Exception {
        String path = request.getServletContext().getRealPath("/files/");
        filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
        File file = new File(path + File.separator + filename);
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        filename = this.getFileName(request, filename);
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }

    public String getFileName(HttpServletRequest request, String filename) throws Exception {
        Base64.Encoder base64Encoder = Base64.getEncoder();
        String agent = request.getHeader("User-Agent");
        if (agent.contains("Firefox")) {
            filename = "=?UTF-8?B?" + base64Encoder.encodeToString(filename.getBytes("UTF-8")) + "?=";
        } else {
            filename = URLEncoder.encode(filename, "UTF-8");
        }
        return filename;
    }

}
