package com.example.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.user.bean.EbankEvent;
import com.example.user.bean.EventJson;
import com.example.user.dao.EventWapMapper;
import com.example.user.service.EventWapService;
import com.example.user.utils.MybatisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/25 9:45
 */
@Service
public class EventWapServiceImpl implements EventWapService {

    private final Logger logger = LoggerFactory.getLogger(EventWapServiceImpl.class);

    @Autowired
    EventWapMapper eventWapMapper;
    @Autowired
    MybatisUtils mybatisUtils;

    @Override
    public void operationDB(String jsonString) {
        //解析数据
        List<EbankEvent> ebankEvents = parseData(jsonString);
        //数据入库
        for (EbankEvent ebankEvent : ebankEvents) {
            logger.info("开始执行selectEventInfo");
            selectEventInfo(ebankEvent);
            logger.info("开始执行selectPageInfo");
            selectPageInfo(ebankEvent);
        }
    }

    private void selectEventInfo(EbankEvent ebankEvent) {
        try {
            EbankEvent eventInfo = eventWapMapper.selectEvent(ebankEvent);
            if (eventInfo != null) {
                eventWapMapper.updateEvent(ebankEvent);
            }else {
                eventWapMapper.insertEvent(ebankEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectPageInfo(EbankEvent ebankEvent) {
        try {
            Map pageInfo = eventWapMapper.selectPage(ebankEvent);
            if (pageInfo != null) {
                eventWapMapper.updatePage(ebankEvent);
            }else {
                eventWapMapper.insertPage(ebankEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<EbankEvent> parseData(String jsonString) {

        EventJson eventJson = new EventJson(jsonString);
        jsonString = eventJson.getJsonData();

        List<EbankEvent> resultList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        JSONObject icbc_event_binding = jsonObject.getJSONObject("icbc_event_bindings");

        JSONObject page_info = icbc_event_binding.getJSONObject("page_info");
        JSONArray page_info_add = page_info.getJSONArray("add");
        List<JSONObject> pageInfoAddList = new ArrayList<>();

        if (!page_info_add.isEmpty() && page_info_add.size() > 0) {
            for (int i = 0; i < page_info_add.size(); i++) {
                JSONObject item = page_info_add.getJSONObject(i);


//                String page_num = item.getString("page_num");
//                String page_name = item.getString("page_name");
                HashMap<String, JSONObject> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put(item.getString("page_num"),item);
                pageInfoAddList.add(item);
            }
        }

        JSONArray page_info_update = page_info.getJSONArray("update");
        List<JSONObject> pageInfoUpdateList = new ArrayList<>();
        if (!page_info_update.isEmpty() && page_info_update.size() > 0) {
            for (int i = 0; i < page_info_update.size(); i++) {
                JSONObject item = page_info_update.getJSONObject(i);
                int i1 = pageInfoAddList.indexOf(item);
                if(i1 == -1){
                    pageInfoAddList.add(item);
                }else {
                    pageInfoAddList.set(i1,item);
                }
                HashMap<String, JSONObject> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put(item.getString("page_num"),item);
                pageInfoAddList.add(item);
            }
        }
        logger.info("pageInfoAddList======",pageInfoAddList);

        //获取event数据
        JSONObject event_bindings = icbc_event_binding.getJSONObject("event_bindings");
        JSONArray event_add = event_bindings.getJSONArray("add");
        if (!event_add.isEmpty() && event_add.size() > 0) {
            for (int i = 0; i < event_add.size(); i++) {
                JSONObject item = event_add.getJSONObject(i);
                EbankEvent ebankEvent = new EbankEvent();
                String targetActivity = item.getString("target_activity");
                //page_info 中的add 匹配页面名称
                String[] addName = searchForAdd(targetActivity, pageInfoAddList);
                if(addName != null){
                    if(!addName[0].isEmpty() && !addName[1].isEmpty()){
                        ebankEvent.setPageNo(addName[0]);
                        ebankEvent.setPageName(addName[1]);
                    }
                }

                ebankEvent.setEventId(item.getString("event_id"));
                ebankEvent.setEventName(item.getString("event_name"));
                String splitField = item.getString("sugo_autotrack_position");
                String[] fillValue = convertSugoAutotrackPositon(splitField);
                ebankEvent.setEventGroup(fillValue[0]);
                ebankEvent.setEventNo(fillValue[1]);
                resultList.add(ebankEvent);
            }
        }

        JSONArray event_update = event_bindings.getJSONArray("update");
        if (!event_update.isEmpty() && event_update.size() > 0) {
            for (int i = 0; i < event_add.size(); i++) {
                JSONObject item = event_add.getJSONObject(i);
                EbankEvent ebankEvent = new EbankEvent();
                String targetActivity = item.getString("target_activity");
                //page_info 中的add 匹配页面名称
                String[] addName = searchForAdd(targetActivity, pageInfoAddList);
                if(addName != null){
                    if(!addName[0].isEmpty() && !addName[1].isEmpty()){
                        ebankEvent.setPageNo(addName[0]);
                        ebankEvent.setPageName(addName[1]);
                    }
                }

                ebankEvent.setEventId(item.getString("event_id"));
                ebankEvent.setEventName(item.getString("event_name"));
                String splitField = item.getString("sugo_autotrack_position");
                String[] fillValue = convertSugoAutotrackPositon(splitField);
                ebankEvent.setEventGroup(fillValue[0]);
                ebankEvent.setEventNo(fillValue[1]);
                resultList.add(ebankEvent);
            }
        }


        //获取h5_event数据
        JSONObject h5_event_bindings = icbc_event_binding.getJSONObject("h5_event_bindings");
        JSONArray h5_add = h5_event_bindings.getJSONArray("add");
        if (!h5_add.isEmpty() && h5_add.size() > 0) {
            for (int i = 0; i < h5_add.size(); i++) {
                JSONObject item = h5_add.getJSONObject(i);
                EbankEvent ebankEvent = new EbankEvent();
                String targetActivity = item.getString("target_activity");
                //page_info 中的add 匹配页面名称
                String[] addName = searchForAdd(targetActivity, pageInfoAddList);
                if(addName != null){
                    if(!addName[0].isEmpty() && !addName[1].isEmpty()){
                        ebankEvent.setPageNo(addName[0]);
                        ebankEvent.setPageName(addName[1]);
                    }
                }

                //page_info 中的update匹配页面名称
                ebankEvent.setEventId(item.getString("event_id"));
                ebankEvent.setEventName(item.getString("event_name"));
                String splitField = item.getString("sugo_autotrack_position");
                String[] fillValue = convertSugoAutotrackPositon(splitField);
                ebankEvent.setEventGroup(fillValue[0]);
                ebankEvent.setEventNo(fillValue[1]);
                resultList.add(ebankEvent);
            }
        }

        JSONArray h5_update = h5_event_bindings.getJSONArray("update");
        if (!h5_update.isEmpty() && h5_update.size() > 0) {
            for (int i = 0; i < h5_update.size(); i++) {
                JSONObject item = h5_add.getJSONObject(i);
                EbankEvent ebankEvent = new EbankEvent();
                String targetActivity = item.getString("target_activity");
                //page_info 中的add 匹配页面名称
                String[] addName = searchForAdd(targetActivity, pageInfoAddList);
                if(addName != null){
                    if(!addName[0].isEmpty() && !addName[1].isEmpty()){
                        ebankEvent.setPageNo(addName[0]);
                        ebankEvent.setPageName(addName[1]);
                    }
                }

                //page_info 中的update匹配页面名称
                ebankEvent.setEventId(item.getString("event_id"));
                ebankEvent.setEventName(item.getString("event_name"));
                String splitField = item.getString("sugo_autotrack_position");
                String[] fillValue = convertSugoAutotrackPositon(splitField);
                ebankEvent.setEventGroup(fillValue[0]);
                ebankEvent.setEventNo(fillValue[1]);
                resultList.add(ebankEvent);
            }
        }
        return resultList;
    }

    private String[] searchForAdd(String targetActivity,List<JSONObject> addList){
        String[] strings = new String[2];
        if(!addList.isEmpty()){
//            for (Map<String, JSONObject> stringJSONObjectMap : addList) {
//                JSONObject jsonObject = stringJSONObjectMap.get("page_num");
//                if(targetActivity.equals(jsonObject.getString("page"))){
//                    strings[0] = jsonObject.getString("page_num");
//                    strings[1] = jsonObject.getString("page_name");
//                }
//            }
            for(JSONObject jsonAdd :addList){
                String page = jsonAdd.getString("page");
                if(targetActivity.equals(page)){
                    strings[0] = jsonAdd.getString("page_num");
                    strings[1] = jsonAdd.getString("page_name");
                }
            }
        }
        return strings;
    }


    //字段解析
    private String[] convertSugoAutotrackPositon(String value) {
        String[] strings = new String[2];
        if (!value.isEmpty()) {
            strings[0] = "1";
            strings[1] = "2";
        }
        return strings;
    }
}
