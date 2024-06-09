package org.example.home.service.Impl;

import org.example.home.domain.ImageUrl;
import org.example.home.mapper.ImageMapper;
import org.example.home.service.ImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ImageServiceImpl 是 ImageService 接口的实现类，处理与图像相关的服务操作。
 *
 * @since 1.0
 * @author David
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Resource
    private ImageMapper imageMapper;

    /**
     * 根据父级ID查询图像列表。
     *
     * @param parentId 父级ID
     * @return 图像URL列表
     */
    public List<ImageUrl> selectById(int parentId) {
        return imageMapper.selectById(parentId);
    }
}
