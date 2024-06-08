package org.example.home.controller;

import org.example.home.domain.ImageUrl;
import org.example.home.service.ImageService;
import org.example.system.controller.BaseController;
import org.example.system.utils.ResultMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/images")
public class ImageController extends BaseController {
    @Resource
    private ImageService imageService;

    @PostMapping("/upload")
    public ResultMap upload() {
        return success("上传成功");
    }

    @GetMapping("/list")
    public ResultMap list(@RequestParam("parentId") int parentId) {
        List<ImageUrl> res = imageService.selectById(parentId);
        return success("查询成功", res);
    }
}
