package org.example.home.controller;

import org.example.home.domain.ImageUrl;
import org.example.home.service.ImageService;
import org.example.system.controller.BaseController;
import org.example.system.utils.ResultMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
     * 上传图像接口
     *
     * @return 包含上传结果的 ResultMap
     */
    @PostMapping("/upload")
    public ResultMap upload() {
        return success("上传成功");
    }

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
}
