package com.timber.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.timber.search.model.dto.user.UserQueryRequest;
import com.timber.search.model.vo.UserVO;
import com.timber.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现
 *
 * @author timber
 * 
 */
@Service
@Slf4j
public class UserDataSource implements DataSource<UserVO> {

    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setPageSize(pageSize);
        userQueryRequest.setCurrent(pageNum);
        return userService.listUserVoByPage(userQueryRequest);
    }
}
