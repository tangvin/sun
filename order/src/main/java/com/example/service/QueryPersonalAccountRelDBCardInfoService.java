package com.example.service;

import com.example.request.requestvo.QueryPersonalAccountRelDBCardRequestVO;
import com.example.response.QueryPersonalAccountRelCardResponseVO;

public interface QueryPersonalAccountRelDBCardInfoService {

    /**
     * 查询客户名下的借记卡
     * @param requestVO
     * @return
     */
    QueryPersonalAccountRelCardResponseVO queryPersonalAccountRelDBCardInfo(QueryPersonalAccountRelDBCardRequestVO requestVO) throws Exception;

}
