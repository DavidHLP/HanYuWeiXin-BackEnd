package org.example.home.service;

import org.example.home.domain.ImageUrl;

import java.util.List;

public interface ImageService {
    public List<ImageUrl> selectById(int parentId);
}
