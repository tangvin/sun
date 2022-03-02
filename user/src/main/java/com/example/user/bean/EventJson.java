package com.example.user.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/25 9:37
 */
public class EventJson {
    private JSONObject jsonObject;

    private String jsonData;

    public EventJson() {
        super();
    }

    public EventJson(String jsonData) {
        this.jsonData = "{\n" +
                "    \"icbc_client_type\":\"2\",\n" +
                "    \"icbc_event_bindings\":{\n" +
                "        \"event_bindings\":\n" +
                "            {\n" +
                "                \"add\":[\n" +
                "                    {\n" +
                "                        \"event_id\":\"111\",\n" +
                "                        \"event_name\":\"测试111\",\n" +
                "                        \"event_type\":\"click\",\n" +
                "                        \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.FavoriteMenu\",\n" +
                "                        \"sugo_autotrack_position\":\"1\",\n" +
                "                        \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test1\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"event_id\":\"222\",\n" +
                "                        \"event_name\":\"测试2\",\n" +
                "                        \"event_type\":\"click\",\n" +
                "                        \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.Test2\",\n" +
                "                        \"sugo_autotrack_position\":\"1\",\n" +
                "                        \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test2\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"event_id\":\"333\",\n" +
                "                        \"event_name\":\"测试3\",\n" +
                "                        \"event_type\":\"click\",\n" +
                "                        \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.Test3\",\n" +
                "                        \"sugo_autotrack_position\":\"1\",\n" +
                "                        \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test3\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"update\":[\n" +
                "                    {\n" +
                "                        \"event_id\":\"111111\",\n" +
                "                        \"event_name\":\"测试update\",\n" +
                "                        \"event_type\":\"click\",\n" +
                "                        \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.FavoriteMenu\",\n" +
                "                        \"sugo_autotrack_position\":\"1\",\n" +
                "                        \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test1\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"event_id\":\"222\",\n" +
                "                        \"event_name\":\"测试2update\",\n" +
                "                        \"event_type\":\"click\",\n" +
                "                        \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.Test2\",\n" +
                "                        \"sugo_autotrack_position\":\"1\",\n" +
                "                        \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test2\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"event_id\":\"333\",\n" +
                "                        \"event_name\":\"测试3update\",\n" +
                "                        \"event_type\":\"click\",\n" +
                "                        \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.Test3\",\n" +
                "                        \"sugo_autotrack_position\":\"1\",\n" +
                "                        \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test3\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ,\n" +
                "        \"h5_event_bindings\":{\n" +
                "            \"add\":[\n" +
                "                {\n" +
                "                    \"event_id\":\"h5_111\",\n" +
                "                    \"event_name\":\"测试111\",\n" +
                "                    \"event_type\":\"click\",\n" +
                "                    \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.FavoriteMenu\",\n" +
                "                    \"sugo_autotrack_position\":\"1\",\n" +
                "                    \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test1\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"event_id\":\"h5_222\",\n" +
                "                    \"event_name\":\"测试2\",\n" +
                "                    \"event_type\":\"click\",\n" +
                "                    \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.Test2\",\n" +
                "                    \"sugo_autotrack_position\":\"1\",\n" +
                "                    \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"event_id\":\"h5_333\",\n" +
                "                    \"event_name\":\"测试3\",\n" +
                "                    \"event_type\":\"click\",\n" +
                "                    \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.Test3\",\n" +
                "                    \"sugo_autotrack_position\":\"1\",\n" +
                "                    \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test3\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"update\":[\n" +
                "                {\n" +
                "                    \"event_id\":\"h5_111\",\n" +
                "                    \"event_name\":\"测试111update\",\n" +
                "                    \"event_type\":\"click\",\n" +
                "                    \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.FavoriteMenu\",\n" +
                "                    \"sugo_autotrack_position\":\"1\",\n" +
                "                    \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test1\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"event_id\":\"h5_222\",\n" +
                "                    \"event_name\":\"测试2_update\",\n" +
                "                    \"event_type\":\"click\",\n" +
                "                    \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.Test2\",\n" +
                "                    \"sugo_autotrack_position\":\"1\",\n" +
                "                    \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"event_id\":\"h5_333\",\n" +
                "                    \"event_name\":\"测试3_update\",\n" +
                "                    \"event_type\":\"click\",\n" +
                "                    \"sugo_autotrack_page_path\":\"com.icbc.activity.fifthEditionUI.Test3\",\n" +
                "                    \"sugo_autotrack_position\":\"1\",\n" +
                "                    \"target_activity\":\"com.icbc.activity.fifthEditionUI.Test3\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"page_info\":{\n" +
                "            \"add\":[\n" +
                "                {\n" +
                "                    \"page_num\":\"111\",\n" +
                "                    \"page_name\":\"会精选页面\",\n" +
                "                    \"page\":\"com.icbc.activity.fifthEditionUI.Test1\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"page_num\":\"222\",\n" +
                "                    \"page_name\":\"测试页面2\",\n" +
                "                    \"page\":\"com.icbc.activity.fifthEditionUI.Test2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"page_num\":\"333\",\n" +
                "                    \"page_name\":\"测试页面3\",\n" +
                "                    \"page\":\"com.icbc.activity.fifthEditionUI.Test3\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"update\":[\n" +
                "                {\n" +
                "                    \"page_num\":\"111\",\n" +
                "                    \"page_name\":\"会精选页面update\",\n" +
                "                    \"page\":\"com.icbc.activity.fifthEditionUI.Test1\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"page_num\":\"222\",\n" +
                "                    \"page_name\":\"测试页面2update\",\n" +
                "                    \"page\":\"com.icbc.activity.fifthEditionUI.Test2\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"page_num\":\"333\",\n" +
                "                    \"page_name\":\"测试页面3update\",\n" +
                "                    \"page\":\"com.icbc.activity.fifthEditionUI.Test3\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getJsonData() {
        return jsonData ;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
