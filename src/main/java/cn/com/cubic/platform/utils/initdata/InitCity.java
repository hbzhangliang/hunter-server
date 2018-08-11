package cn.com.cubic.platform.utils.initdata;


import cn.com.cubic.platform.utils.JsonReader;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Liang.Zhang on 2018/8/11.
 **/

public class InitCity {

    private static final Logger log = LoggerFactory.getLogger(InitCity.class);

    public static void main(String[] args){
        JSONObject jsonObject= JsonReader.readJson("initdata/city.json");

        if(null==jsonObject||jsonObject.isEmpty()) return;

        JSONArray jsonArray=jsonObject.getJSONArray("provinces");

        for(Object item:jsonArray){
            JSONObject jsb=(JSONObject)item;
            String provinceName=jsb.getString("provinceName");
            JSONArray jAy=jsb.getJSONArray("citys");
        }
    }

}
