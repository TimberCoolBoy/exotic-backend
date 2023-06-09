package com.timber.search.datasource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timber.search.model.dto.post.PostQueryRequest;
import com.timber.search.model.entity.Post;
import com.timber.search.model.vo.PostVO;
import com.timber.search.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子服务实现
 *
 * @author timber
 * 
 */
@Service
@Slf4j
public class PostDataSource implements DataSource<PostVO> {

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        postQueryRequest.setSearchText(searchText);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        return postService.getPostVOPage(postPage,request);
    }
}




