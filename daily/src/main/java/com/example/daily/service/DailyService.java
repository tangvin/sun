package com.example.daily.service;

import com.example.daily.entity.Daily;
import com.example.daily.mapper.DailyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyService {
    private final Logger logger = LoggerFactory.getLogger(DailyService.class);

    @Autowired
    private DailyMapper dailyMapper;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Daily findById(Integer dailyId) {
        logger.debug("param dailyId : {}", dailyId);
        Daily d = this.dailyMapper.findById(dailyId);
        logger.debug("result daily : {}", d.toString());
        return d;
    }

    /**
     * 根据id删除
     */
    public int deleteDaily(Integer id) {
        logger.debug("删除的id=" + id);
        int i = dailyMapper.deleteDaily(id);
        return i;
    }


    /**
     * 新增一条日记
     */
    public int addDaily(String dailyTitle, String dailyContent) {
        logger.debug("新增的dailyTitle是" + dailyTitle);
        Daily daily = new Daily();
        daily.setByPerson("zhansan");
//		dailyMapper.insert(dailyTitle,dailyContent);
        return 0;
    }

}
