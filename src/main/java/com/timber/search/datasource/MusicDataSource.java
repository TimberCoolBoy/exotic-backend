package com.timber.search.datasource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rholder.retry.Retryer;
import com.timber.search.common.ErrorCode;
import com.timber.search.exception.BusinessException;
import com.timber.search.model.entity.Music;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j
public class MusicDataSource implements DataSource{

    @Resource
    private Retryer<String> retryer;

    @Override
    public Page<Music> doSearch(String searchText, long pageNum, long pageSize) {
        String url = String.format("https://autumnfish.cn/search?keywords=%s", searchText);
        String strJson = "";
        try {
            strJson = retryer.call(() -> Jsoup.connect(url)
                    .ignoreContentType(true)
                    .execute().body());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
        }

        JSONObject jsonObject = JSONObject.parseObject(strJson);
        JSONArray musicArray = jsonObject.getJSONObject("result").getJSONArray("songs");
        ArrayList<Music> musicList = new ArrayList<>();
        for (int i = 0; i < musicArray.size(); i++) {
            Music music = new Music();
            music.setId((Integer) musicArray.getJSONObject(i).get("id"));
            music.setMvid((Integer) musicArray.getJSONObject(i).get("mvid"));
            music.setName((String) musicArray.getJSONObject(i).get("name"));
            musicList.add(music);
            if (musicList.size() >= pageSize){
                break;
            }
        }
        Page<Music> page = new Page<>(pageNum, pageSize);
        page.setRecords(musicList);
        return page;
    }
}
