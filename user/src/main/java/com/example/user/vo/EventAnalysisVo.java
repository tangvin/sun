package com.example.user.vo;

import java.util.List;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/4 14:31
 */
public class EventAnalysisVo {


    private List<FirstPageVo> firstPageVoList;

    public List<FirstPageVo> getFirstPageVoList(){
        return firstPageVoList;
    }

    public void setFirstPageVoToList(FirstPageVo firstPageVo) {
        this.firstPageVoList.add(firstPageVo);
    }

//    public static class FirstPageVo{
//
//        private String pageSn;
//        private String pageName;
//
//        public FirstPageVo(String pageSn,String pageName){
//            this.pageSn = pageSn;
//            this.pageName = pageName;
//        }
//
//
//        List<SecondPageVo> secondPageVoList = new ArrayList<>();
//        //json格式化不显示该字段
//        Map<String,SecondPageVo> areaSet = new ConcurrentHashMap<>();
//
//
//        public String getPageSn() {
//            return pageSn;
//        }
//
//        public void setPageSn(String pageSn) {
//            this.pageSn = pageSn;
//        }
//
//        public String getPageName() {
//            return pageName;
//        }
//
//        public void setPageName(String pageName) {
//            this.pageName = pageName;
//        }
//
//        public List<SecondPageVo> getSecondPageVoList() {
//            return secondPageVoList;
//        }
//
//        public void setSecondPageVoList(List<SecondPageVo> secondPageVoList) {
//            this.secondPageVoList = secondPageVoList;
//        }
//
//        public Map<String, SecondPageVo> getAreaSet() {
//            return areaSet;
//        }
//
//        public void setAreaSet(Map<String, SecondPageVo> areaSet) {
//            this.areaSet = areaSet;
//        }
//
//        public void setAreaToAreaSet(SecondPageVo secondPageVo){
//            SecondPageVo put = this.areaSet.put(secondPageVo.getAreaSn(), secondPageVo);
//            if(put == null){
//                secondPageVoList.add(secondPageVo);
//            }
//        }
//    }
//
//
//
//
//
//    public static class SecondPageVo{
//
//        private String areaSn;
//        private String areaName;
//
//        public SecondPageVo(String areaSn,String areaName){
//            this.areaSn = areaSn;
//            this.areaName = areaName;
//        }
//
//
//        List<ThirdPageVo> thirdPageVoList = new ArrayList<>();
//        //json格式化不显示该字段
//        Map<String,ThirdPageVo> buttonSet = new ConcurrentHashMap<>();
//
////        public Map<String,ThirdPageVo> buttonSet =
//
//
//        public String getAreaSn() {
//            return areaSn;
//        }
//
//        public void setAreaSn(String areaSn) {
//            this.areaSn = areaSn;
//        }
//
//        public String getAreaName() {
//            return areaName;
//        }
//
//        public void setAreaName(String areaName) {
//            this.areaName = areaName;
//        }
//
//        public List<ThirdPageVo> getThirdPageVoList() {
//            return thirdPageVoList;
//        }
//
//        public void setThirdPageVoList(List<ThirdPageVo> thirdPageVoList) {
//            this.thirdPageVoList = thirdPageVoList;
//        }
//
//        public Map<String, ThirdPageVo> getButtonSet() {
//            return buttonSet;
//        }
//
//        public void setButtonSet(Map<String, ThirdPageVo> buttonSet) {
//            this.buttonSet = buttonSet;
//        }
//
//       public void setbuttonToButtonSet(ThirdPageVo thirdPageVo){
//           ThirdPageVo put = this.buttonSet.put(thirdPageVo.getButtonSn(), thirdPageVo);
//           if(put  == null){
//               this.thirdPageVoList.add(thirdPageVo);
//           }
//       }
//
//    }
//
//
//
//
//
//    public static class ThirdPageVo{
//
//        private String buttonSn;
//        private String buttonName;
//
//        public ThirdPageVo(String buttonSn,String buttonName){
//            this.buttonSn = buttonSn;
//            this.buttonName = buttonName;
//        }
//
//        public String getButtonSn() {
//            return buttonSn;
//        }
//
//        public void setButtonSn(String buttonSn) {
//            this.buttonSn = buttonSn;
//        }
//
//        public String getButtonName() {
//            return buttonName;
//        }
//
//        public void setButtonName(String buttonName) {
//            this.buttonName = buttonName;
//        }
//    }


}
