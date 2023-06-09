package com.timber.search.model.vo;

import com.timber.search.model.entity.Music;
import com.timber.search.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author timber
 */
@Data
public class SearchVO implements Serializable {

    private List<PostVO> postList;

    private List<UserVO> userList;

    private List<Picture> pictureList;

    private List<VideoVo> videoList;

    private List<Music> musicList;

    private List<?> dataList;

    private static final long serialVersionUID = 5265687897079265408L;
}
