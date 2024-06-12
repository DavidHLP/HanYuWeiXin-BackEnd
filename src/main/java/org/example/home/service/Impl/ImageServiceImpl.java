package org.example.home.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.constant.Desource;
import org.example.home.controller.utils.FileUploader;
import org.example.home.controller.utils.UrlAndBollean;
import org.example.home.domain.ImageUrl;
import org.example.home.mapper.ImageMapper;
import org.example.home.service.ImageService;
import org.example.system.domain.LoginUser;
import org.example.system.utils.JSONFileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
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
    @Resource
    private FileUploader fileUploader;

    /**
     * 根据父级ID查询图像列表。
     *
     * @param parentId 父级ID
     * @return 图像URL列表
     */
    @Override
    public List<ImageUrl> selectById(int parentId) {
        return imageMapper.selectById(parentId);
    }

    public UrlAndBollean avatarUpload(MultipartFile[] files, String path, LoginUser user) throws Exception {
        UrlAndBollean urlAndBollean = insertAndUpload(files, path, user);
        if (urlAndBollean.getFlag()) {
            imageMapper.updateAvatar(user.getId(), urlAndBollean.getUrl());
        }else {
            return new UrlAndBollean(null, false);
        }
        return urlAndBollean;
    }

    /**
     * 插入图像URL并上传图像文件。
     *
     * @param files 上传的图像文件数组
     * @param path 保存图像文件的路径
     * @param user 当前登录用户
     * @return 包含上传状态和URL的对象
     * @throws Exception 如果处理文件时发生错误
     */
    @Override
    public UrlAndBollean insertAndUpload(MultipartFile[] files, String path, LoginUser user) throws Exception {
        if (files == null || files.length == 0) {
            return new UrlAndBollean(null, false);
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Desource> list = new ArrayList<>();
        List<Desource> downloadList = new ArrayList<>();

        // 确保目录存在
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        UrlAndBollean url = null;
        if (files != null && files.length > 0) {
            String json = JSONFileUtils.readFile(path + "files.json");
            String downloadJson = JSONFileUtils.readFile(path + "download.json");
            if (json != null && !json.isEmpty() && downloadJson != null && !downloadJson.isEmpty()) {
                list = mapper.readValue(json, new TypeReference<List<Desource>>() {});
                downloadList = mapper.readValue(downloadJson, new TypeReference<List<Desource>>() {});
            }

            for (MultipartFile file : files) {
                String filename = file.getOriginalFilename();
                String filePath = path + filename;

                // 避免文件名重复
                File destFile = new File(filePath);
                int count = 1;
                while (destFile.exists()) {
                    String name = filename.substring(0, filename.lastIndexOf('.'));
                    String extension = filename.substring(filename.lastIndexOf('.'));
                    filename = name + "(" + count + ")" + extension;
                    filePath = path + filename;
                    destFile = new File(filePath);
                    count++;
                }

                // 保存文件
                file.transferTo(destFile);
                list.add(new Desource(filename));
                url = fileUploader.uploadImage(filePath);
                downloadList.add(new Desource(url.getUrl()));
            }

            // 更新文件列表
            json = mapper.writeValueAsString(list);
            downloadJson = mapper.writeValueAsString(downloadList);
            JSONFileUtils.writeFile(json, path + "files.json");
            JSONFileUtils.writeFile(downloadJson, path + "download.json");

            return new UrlAndBollean(url.getUrl(), true);
        }
        return new UrlAndBollean(url.getUrl(), false);
    }

    /**
     * 获取图像列表。
     *
     * @param path 文件路径
     * @return 图像列表
     * @throws Exception 如果读取文件时发生错误
     */
    @Override
    public List<Desource> getImage(String path) throws Exception {
        String json = JSONFileUtils.readFile(path);
        List<Desource> downloadList = new ArrayList<>();
        if (json != null && !json.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            downloadList = mapper.readValue(json, new TypeReference<List<Desource>>() {});
        }
        return downloadList;
    }
}
