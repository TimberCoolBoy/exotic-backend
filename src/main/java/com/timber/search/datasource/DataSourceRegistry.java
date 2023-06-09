package com.timber.search.datasource;

import com.timber.search.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Component
public class DataSourceRegistry {

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private VideoDataSource videoDataSource;

    @Resource
    private MusicDataSource musicDataSource;

    private Map<String,DataSource<T>> typeDataSourceMap;

    @PostConstruct
    public void init(){
        typeDataSourceMap = new HashMap(){{
            put(SearchTypeEnum.POST.getValue(),postDataSource);
            put(SearchTypeEnum.USER.getValue(),userDataSource);
            put(SearchTypeEnum.PICTURE.getValue(),pictureDataSource);
            put(SearchTypeEnum.VIDEO.getValue(),videoDataSource);
            put(SearchTypeEnum.MUSIC.getValue(), musicDataSource);
        }};
    }

    public DataSource getDataSource(String type){
        if(type==null){
            return null;
        }
        return typeDataSourceMap.get(type);
    }

}
