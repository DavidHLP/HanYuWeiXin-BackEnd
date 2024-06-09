package org.example.home.service;

import org.example.home.domain.ImageUrl;

import java.util.List;

/**
 * ImageService 接口定义了与图像相关的服务操作。
 *
 * @since 1.0
 * @author David
 */
public interface ImageService {

    /**
     * 根据父级ID查询图像列表。
     *
     * @param parentId 父级ID
     * @return 图像URL列表
     */
    public List<ImageUrl> selectById(int parentId);
}
