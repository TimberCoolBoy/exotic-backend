package com.timber.search.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timber.search.common.ErrorCode;
import com.timber.search.datasource.*;
import com.timber.search.exception.ThrowUtils;
import com.timber.search.model.dto.search.SearchRequest;
import com.timber.search.model.entity.Music;
import com.timber.search.model.entity.Picture;
import com.timber.search.model.enums.SearchTypeEnum;
import com.timber.search.model.vo.PostVO;
import com.timber.search.model.vo.SearchVO;
import com.timber.search.model.vo.UserVO;
import com.timber.search.model.vo.VideoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author timber
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    @Resource
    private VideoDataSource videoDataSource;

    @Resource
    private MusicDataSource musicDataSource;


    public SearchVO searchAll(SearchRequest searchRequest, HttpServletRequest request){
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();

        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();


        if (searchTypeEnum == null) {
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText, current, pageSize);
                return picturePage;
            });
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
                return userVOPage;
            });
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
                return postVOPage;
            });
            CompletableFuture<Page<VideoVo>> videoTask = CompletableFuture.supplyAsync(() -> {
                Page<VideoVo> videoVoPage = videoDataSource.doSearch(searchText, current, pageSize);
                return videoVoPage;
            });
            CompletableFuture<Page<Music>> musicTask = CompletableFuture.supplyAsync(() -> {
                Page<Music> musicPage = musicDataSource.doSearch(searchText, current, pageSize);
                return musicPage;
            });

            CompletableFuture.allOf(pictureTask, userTask, pictureTask,videoTask,musicTask).join();

            SearchVO searchVO = new SearchVO();
            try {
                searchVO.setPostList(postTask.get().getRecords());
                searchVO.setUserList(userTask.get().getRecords());
                searchVO.setPictureList(pictureTask.get().getRecords());
                searchVO.setVideoList(videoTask.get().getRecords());
                searchVO.setMusicList(musicTask.get().getRecords());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return searchVO;
        } else {
            DataSource<?> dataSource = dataSourceRegistry.getDataSource(searchTypeEnum.getValue());
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            SearchVO searchVO = new SearchVO();
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }
}
