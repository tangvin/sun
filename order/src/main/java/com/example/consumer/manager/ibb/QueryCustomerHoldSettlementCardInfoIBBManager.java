package com.example.consumer.manager.ibb;

import com.example.constant.DebitCardConstant;
import com.example.consumer.manager.kbb.QueryCustomerHoldSettlementCardInfoKBBManager;
import com.example.dao.DebitCardContractDAO;
import com.example.dao.DebitCardDAO;
import com.example.entity.DebitCardBO;
import com.example.entity.DebitCardContractBO;
import com.example.request.QueryDebitCardRequest;
import com.example.response.QueryDebitCardResponseVO;
import com.example.vo.ComposeResultFirstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 客户名下的卡片信息
 * 根据客户号 查询卡片信息
 * 客户号 = 卡片信息中的 客户号
 * 获取合约编号
 */

@Component
public class QueryCustomerHoldSettlementCardInfoIBBManager {

    @Autowired
    DebitCardContractDAO debitCardContractDAO;

    @Autowired
    DebitCardDAO debitCardDAO;

    @Autowired
    QueryCustomerHoldSettlementCardInfoKBBManager queryCustomerHoldSettlementCardInfoKBBManager;

    /**
     * 客户号 =  《合约表》单位持卡人编号   再 查询卡片表
     *
     * @param queryDebitCardRequest
     * @return
     */
    public QueryDebitCardResponseVO queryCustomerUnderDebitCardInfo(QueryDebitCardRequest queryDebitCardRequest) {
        Integer pageSize = 10;
        if (StringUtils.isEmpty(queryDebitCardRequest.getPageSize())) {
            pageSize = Integer.parseInt(queryDebitCardRequest.getPageSize());
        }
        Integer pageNo = 1;
        if (StringUtils.isEmpty(queryDebitCardRequest.getPageNo())) {
            pageNo = Integer.parseInt(queryDebitCardRequest.getPageNo());
        }

        String validFlag = queryDebitCardRequest.getValidFlag();
        String contractNo = queryDebitCardRequest.getCusNo();
        //根据  客户号 =  单位持卡人编号  获取合约信息
        List<DebitCardContractBO> debitCardContractBOList = debitCardContractDAO.selectByContractNo(contractNo);

        //获取合约信息中的合约编号用于查询卡片表
        List<String> list = new ArrayList<>(0);
        if (!CollectionUtils.isEmpty(debitCardContractBOList)) {
            list = debitCardContractBOList.stream().map(t -> t.getDbcrdArRefno()).collect(Collectors.toList());
        }

        List<DebitCardBO> debitCardList = null;
        if (!CollectionUtils.isEmpty(list)) {
            debitCardList = pageCycleQueryUseThread(list);
        }

        //查询所有的卡片表信息后 过滤
        if (!CollectionUtils.isEmpty(debitCardList)) {
            debitCardList = getDebitCardBOList(debitCardList, validFlag);
        }

        //组装信息
        List<ComposeResultFirstVO> mapList = queryCustomerHoldSettlementCardInfoKBBManager.queryCustomerUnderCardInfo(debitCardList, debitCardContractBOList);
        QueryDebitCardResponseVO responseVO = new QueryDebitCardResponseVO();
        int totalRecords = 0;
        if (!CollectionUtils.isEmpty(mapList)) {
            totalRecords = debitCardContractBOList.size();
        }
        int totalPages = totalRecords / pageSize;
        if (totalRecords % pageSize != 0) {
            totalPages++;
        }
        responseVO.setPageNo(pageNo);
        responseVO.setList(mapList);
        responseVO.setTotalRecords(totalRecords);
        responseVO.setTotalPages(totalPages);
        return responseVO;
    }


    /**
     * @param debitCardBOList
     * @description:
     * @author: Bruce_T
     * @data: 2022/2/14 10:05
     * @modified:
     */
    private List<DebitCardBO> getDebitCardBOList(List<DebitCardBO> debitCardBOList, String validFlag) {
        //有效
        if ("1".equals(validFlag)) {
            debitCardBOList = debitCardBOList.stream().filter(u -> !DebitCardConstant.DEBIT_CARD_LIFECYCLE_00506.getCode().equals(u.getCardLifeCycle())).collect(Collectors.toList());
        }
        //无效
        if ("0".equals(validFlag)) {
            debitCardBOList = debitCardBOList.stream().filter(u -> DebitCardConstant.DEBIT_CARD_LIFECYCLE_00506.getCode().equals(u.getCardLifeCycle())).collect(Collectors.toList());
        }
        return debitCardBOList;
    }

    /**
     * 合约集合中循环查询 卡片信息，in（合约编号）（不是卡片表的主键）
     * 采用多线程
     * 前闭后开
     * 取前5条数据
     * select * from table_name limit 0,5
     * 查询第11到第15条数据
     * select * from table_name limit 10,5
     * limit关键字的用法：
     * LIMIT [offset,] rows
     * offset指定要返回的第一行的偏移量，rows第二个指定返回行的最大数目。初始行的偏移量是0(不是1)。
     * select * from t_table limit #{index},#{num}
     * public List<T> queryByThread(@Param(value = "index")int index, @Param(value = "num")int num);
     *
     * @param
     *
     * @return
     */
    private List<DebitCardBO> pageCycleQueryContractBO(List<String> list) {

        //数据集合大小
        Integer listSize = list.size();
        //开启的线程数
        Integer runSize = 10;
        // 一个线程处理数据条数，如果库中有100条数据，开启20个线程，那么每一个线程执行的条数就是5条
        int count = listSize / runSize;//5
        // 创建一个线程池，数量和开启线程的数量一样
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        // 计算sql语句中每个分页查询的起始和结束数据下标
        // 循环创建线程
        List<DebitCardBO> resultList = Collections.synchronizedList(new ArrayList<>(0)) ;
        //此处调用具体的查询方法
        for (int i = 0; i < runSize; i++) {
            int index = i * count;
            int num = count;
            Future<List<DebitCardBO>> future = executor.submit(new Callable<List<DebitCardBO>>() {
                @Override
                public List<DebitCardBO> call() throws Exception {
                    List<DebitCardBO> resultList = debitCardDAO.selectByThread(index, num);
                    return resultList;
                }
            });
            try {
                List<DebitCardBO> debitCardBOList = future.get();
                resultList.addAll(debitCardBOList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        // 执行完关闭线程池
        executor.shutdown();
        return resultList;
    }


    /**
     * @param * @param null
     * @return {@link null}
     * @description:
     * @author: Bruce_T
     * @data: 2022/2/14 11:33
     * @modified:
     */
    private List<DebitCardBO> pageCycleQueryContractBO2222(List<String> list) {
        //数据集合大小
        Integer listSize = list.size();
        //开启的线程数
        Integer runSize = 3;
        // 一个线程处理数据条数，如果库中有100条数据，开启20个线程，那么每一个线程执行的条数就是5条
        int count = listSize / runSize;//5
        // 创建一个线程池，数量和开启线程的数量一样
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        // 计算sql语句中每个分页查询的起始和结束数据下标
        // 循环创建线程
        List<DebitCardBO> resultList = new ArrayList<>(0);
        //此处调用具体的查询方法
        for (int i = 0; i < runSize; i++) {
            int index = i * count;
            int num = count;
            Future<List<DebitCardBO>> future = executor.submit(new Callable<List<DebitCardBO>>() {
                @Override
                public List<DebitCardBO> call() throws Exception {
                    List<DebitCardBO> resultList = debitCardDAO.selectListByThread(list);
                    return resultList;
                }
            });
            try {
                List<DebitCardBO> debitCardBOList = future.get();
                resultList.addAll(debitCardBOList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        // 执行完关闭线程池
        executor.shutdown();
        return resultList;
    }


    /**
     * @param list
     * @return {@link null}
     * @description: 不适用多线程
     * @author: Bruce_T
     * @data: 2022/2/14 10:07
     * @modified:
     */
    private List<DebitCardBO> pageCycleQuery(List<String> list) {

        List<DebitCardBO> resultList = new ArrayList<>();
        if (list.size() == 1) {
            resultList.add(debitCardDAO.selectDebitCardByPrimaryKey("003", list.get(0)));
            return resultList;
        }
        Integer total = list.size();
        Integer size = 50;
        Integer pages = total % size == 0 ? total / size : total / size + 1;
        for (int i = 0; i < pages; i++) {

            Integer toIndex = (i + 1) * size > total ? total : (i + 1) * size;
            //分批查询
            List<DebitCardBO> tempList = debitCardDAO.selectDebitCardByMutipleContractId(list.subList(i * size, toIndex));
            //组装结果
            resultList.addAll(tempList);
        }
        return resultList;
    }

    /**
     * @param * @param null
     * @return {@link null}
     * @description: 使用多线程并发处理
     * @author: Bruce_T
     * @data: 2022/2/14 10:11
     * @modified:
     */
    private List<DebitCardBO> pageCycleQueryUseThread(List<String> list) {
        //定义全局组装的List为线程安全，防止add的时候数据污染缺失或者数据越界
        List<DebitCardBO> resultList = Collections.synchronizedList(new ArrayList<>());
        //数据集合大小
        Integer total = list.size();
        Integer size = 5;
        //开启的线程数  = 总页数
        Integer runSize = total % size == 0 ? total / size : total / size + 1;
        // 一个线程处理数据条数，如果库中有100条数据，开启20个线程，那么每一个线程执行的条数就是5条
        int count = total / runSize;//5
        //总页数
//        Integer pages = total % size == 0 ? total/size : total/size + 1;
        // 创建一个线程池，数量和开启线程的数量一样
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        for (int i = 0; i < runSize; i++) {
            Integer toIndex = (i + 1) * size > total ? total : (i + 1) * size;
            int finalI = i;
            Future<List<DebitCardBO>> future = executor.submit(() -> {
                List<DebitCardBO> resultList1 = debitCardDAO.selectListByThread(list.subList(finalI * size, toIndex));
                return resultList1;
            });
            try {
                List<DebitCardBO> debitCardBOList = future.get();
                resultList.addAll(debitCardBOList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        // 执行完关闭线程池
        executor.shutdown();

        return resultList;
    }
}
