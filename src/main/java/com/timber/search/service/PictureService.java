package com.timber.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timber.search.model.entity.Picture;

/**
 * @author timber
 */
public interface PictureService {

    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}
