package org.example.home.controller;

import org.example.common.constant.Desource;
import org.example.home.controller.utils.UrlAndBollean;
import org.example.home.domain.ImageUrl;
import org.example.home.service.ImageService;
import org.example.system.controller.BaseController;
import org.example.system.domain.LoginUser;
import org.example.system.utils.ResultMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ImageController 处理与图像相关的请求。
 *
 * @since 1.0
 * @author David
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
     * @param files 上传的图像文件数组
     * @param request HTTP请求对象
     * @return 包含上传结果的 ResultMap
     * @throws Exception 如果处理文件时发生错误
     */
    @PostMapping("/fileUpload")
    public ResultMap fileUpload(@RequestParam("avatar") MultipartFile[] files, HttpServletRequest request) throws Exception {
        String path = request.getServletContext().getRealPath("./") + "/files/";
        LoginUser user = (LoginUser) request.getAttribute("loginUser");
        try {
            UrlAndBollean url = imageService.insertAndUpload(files, path, user);
            if (url.getFlag()) {
                return success("上传成功", url.getUrl());
            } else {
                return fail("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    @PostMapping("/avatarUpload")
    public ResultMap avatarUpload(@RequestParam("avatar") MultipartFile[] files, HttpServletRequest request) throws Exception {
        String path = request.getServletContext().getRealPath("./") + "/files/";
        LoginUser user = (LoginUser) request.getAttribute("loginUser");
        try {
            UrlAndBollean url = imageService.avatarUpload(files, path, user);
            if (url.getFlag()) {
                return success("上传成功", url.getUrl());
            } else {
                return fail("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return fail(e.getMessage());
        }
    }

    /**
     * 获取下载文件名列表接口
     *
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @return 包含查询结果的 ResultMap
     * @throws Exception 如果读取文件时发生错误
     */
    @GetMapping("/getDownloadFilesName")
    public ResultMap getDownloadFilesName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String path = request.getServletContext().getRealPath("./") + "/files/download.json";
        List<Desource> res = imageService.getImage(path);
        return success("查询成功", res);
    }
}
