package com.example.service.impl;

import com.example.consumer.manager.ibb.PreQueryPersonalAccountRelDBCardInfoIBBManager;
import com.example.consumer.manager.ibb.QueryPersonalAccountRelDBCardInfoIBBManager;
import com.example.request.requestvo.QueryPersonalAccountRelDBCardRequestVO;
import com.example.response.QueryPersonalAccountRelCardResponseVO;
import com.example.service.QueryPersonalAccountRelDBCardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryPersonalAccountRelDBCardInfoServiceImpl implements QueryPersonalAccountRelDBCardInfoService {

    @Autowired
    PreQueryPersonalAccountRelDBCardInfoIBBManager preIBBManager;

    @Autowired
    QueryPersonalAccountRelDBCardInfoIBBManager queryPersonalAccountRelDBCardInfoIBBManager;

    /**
     *
     * @param requestVO
     * @return
     */
    public QueryPersonalAccountRelCardResponseVO queryPersonalAccountRelDBCardInfo(QueryPersonalAccountRelDBCardRequestVO requestVO) throws Exception{

        //1-前置查询
        preIBBManager.preQuery(requestVO);

        //2-数据组装
        QueryPersonalAccountRelCardResponseVO responseVO = queryPersonalAccountRelDBCardInfoIBBManager.queryPersonalAccountRelDBCardInfo(requestVO);

        return responseVO;
    }


}
