/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HbaseData;

/**
 *
 * @author zhangbingxue
 */
public class QueryMsg {
    private String station ="*";      //台站
    private String item = "*";         //测项
    private String stationpoint = "*";  //测点
    private String starttime = "*";   //开始时间
    private String endtime = "*";     //结束时间
    private String zoom = "*";        //采样率
    private String aggregator = "sum";   //聚合器
    //获取聚合器 默认 “sum”
    public String getAggregator() {
        return aggregator;
    }
   // 设置聚合器 默认 “sum”
    public void setAggregator(String aggregator) {
        this.aggregator = aggregator;
    }
    
     //获取台站
    public String getStation() {
        return station;
    }
    //设置台站
    public void setStation(String station) {
        this.station = station;
    }
     //获取测项
    public String getItem() {
        return item;
    }
     //设置测项
    public void setItem(String item) {
        this.item = item;
    }
   //获取测点
    public String getStationpoint() {
        return stationpoint;
    }
//设置测点
    public void setStationpoint(String stationpoint) {
        this.stationpoint = stationpoint;
    }
//获取开始时间
    public String getStarttime() {
        return starttime;
    }
//设置开始时间
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
//获取结束时间
    public String getEndtime() {
        return endtime;
    }
  //设置结束时间
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
   //获取采样率
    public String getZoom() {
        return zoom;
    }
 //设置采样率
    public void setZoom(String zoom) {
        this.zoom = zoom;
    }
    
}
