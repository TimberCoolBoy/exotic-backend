package com.timber.search.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class MusicServiceTest {

    @Test
    void testMusic() {
        String searchText = "baby";
        String url = String.format("https://autumnfish.cn/search?keywords=%s", searchText);
        String strJson = "";
//        Document doc = null;
        try {
            strJson = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .execute().body();
            System.out.println(strJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = JSONObject.parseObject(strJson);
        JSONArray musicArray = jsonObject.getJSONObject("result").getJSONArray("songs");
        System.out.println(musicArray);
        for (int i = 0; i < musicArray.size(); i++) {
            System.out.print("id:" + musicArray.getJSONObject(i).get("id"));
            System.out.print("mvid:" + musicArray.getJSONObject(i).get("mvid"));
            System.out.println("name" + musicArray.getJSONObject(i).get("name"));
            System.out.println();
        }
    }
}
