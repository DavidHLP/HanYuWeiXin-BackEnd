package org.example.home.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.home.domain.ImageUrl;

import java.util.List;

@Mapper
public interface ImageMapper {
    @Select("select * from image_url where parent_id = #{parentId}")
    public List<ImageUrl> selectById(@Param("parentId") int parentId);
}
