package org.example.home.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.home.domain.ImageUrl;

import java.util.List;

/**
 * ImageMapper 接口定义了与数据库交互的图像查询操作。
 *
 * @since 1.0
 * @author David
 */
@Mapper
public interface ImageMapper {

    /**
     * 根据父级ID查询图像列表。
     *
     * @param parentId 父级ID
     * @return 图像URL列表
     */
    @Select("select * from image_url where parent_id = #{parentId}")
    public List<ImageUrl> selectById(@Param("parentId") int parentId);
}
