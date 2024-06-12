package org.example.home.service;

import org.example.common.constant.Desource;
import org.example.home.controller.utils.UrlAndBollean;
import org.example.home.domain.ImageUrl;
import org.example.system.domain.LoginUser;
import org.springframework.web.multipart.MultipartFile;

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

    public UrlAndBollean insertAndUpload(MultipartFile[] files , String path , LoginUser user) throws Exception;
    public List<Desource> getImage(String path) throws Exception;
}
