package com.example.gateway;

import com.example.request.requestvo.QueryPersonalAccountRelDBCardRequestVO;
import com.example.response.QueryPersonalAccountRelCardResponseVO;
import com.example.service.QueryPersonalAccountRelDBCardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class QueryPersonalAccountRelDBCardInfoGateway {

    @Autowired
    QueryPersonalAccountRelDBCardInfoService queryPersonalAccountRelDBCardInfoService;

    /**
     * 查询个人借记卡账户关联的卡片
     * @param requestVO
     * @return
     */
    @PostMapping("/queryPersonalAccountRelDBCardInfo")
    @ResponseBody
    public QueryPersonalAccountRelCardResponseVO queryPersonalAccountRelDBCardInfo(@RequestBody QueryPersonalAccountRelDBCardRequestVO requestVO){

        try{
            QueryPersonalAccountRelCardResponseVO responseVO =
                    queryPersonalAccountRelDBCardInfoService.queryPersonalAccountRelDBCardInfo(requestVO);
            return responseVO;
        }catch (Exception e){
           e.printStackTrace();
        }
        return null;
    }

}
