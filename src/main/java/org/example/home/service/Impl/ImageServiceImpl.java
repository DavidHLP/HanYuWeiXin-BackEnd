package org.example.home.service.Impl;

import org.example.home.domain.ImageUrl;
import org.example.home.mapper.ImageMapper;
import org.example.home.service.ImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Resource
    private ImageMapper imageMapper;

    public List<ImageUrl> selectById(int parentId) {
        return imageMapper.selectById(parentId);
    }
}
