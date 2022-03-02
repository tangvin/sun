package com.example.consumer.manager.kbb;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/14   11:31
 * @version: 1.0
 * @modified:
 */
public class ObjectToMapKBBManager {

    //    private void test(List<String> list){
//        Integer size = 200;
//        Integer total = list.size();
//        Integer pages = total % size == 0 ? total / size : total / size + 1;
//
//        //线程池
//        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//
//        List<DebitCardBO> callables = new ArrayList<>(0);
//        for (int i = 0; i < pages; i++) {
//            Integer toIndex = (i + 1) * size > total ? total : (i + 1) * size;
//            callables.add(toIndex,callables.get(toIndex));
//        }
//        List<Future<List<DebitCardBO>>> futures = service.invokeAll(callables);
//        List<DebitCardBO> finalRes = new ArrayList<>(total);
//        futures.forEach((f) -> {
//            try {
//                System.out.println(finalRes.addAll(f.get()));
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
//        //释放资源
//        service.shutdown();
//    }

    /**
     *
     * @param
     * @param j
     * @return
     * @throws IllegalAccessException
     */
    private static Map<String, Object> objToMap(Object j) throws IllegalAccessException {
        Map<String, Object> newMap = new HashMap<>(0);
        // 获取所有字段,public和protected和private,但是不包括父类字段
        Field[] fields = j.getClass().getDeclaredFields();
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
//            newMap.put(field.getName(),field.get(j));
            assembleMap(field,newMap,j);
        }
        return newMap;
    }

    /**
     *
     * @param field
     * @param newMap
     * @param j
     * @throws IllegalAccessException
     */
    private static void assembleMap(Field field, Map<String, Object> newMap, Object j) throws IllegalAccessException {
        if ("1".equals(field.getName())) {
            newMap.put(field.getName(), field.get(j));
        } else if ("2".equals(field.getName())) {
            newMap.put(field.getName(), field.get(j));
        } else if ("3".equals(field.getName())) {
            newMap.put(field.getName(), field.get(j));
        } else if ("4".equals(field.getName())) {
            newMap.put(field.getName(), field.get(j));
        }else {

        }
    }
}
