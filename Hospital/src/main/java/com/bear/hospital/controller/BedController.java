package com.bear.hospital.controller;

import com.bear.hospital.pojo.Bed;
import com.bear.hospital.service.BedService;
import com.bear.hospital.utils.ResponseData;
import com.bear.hospital.utils.TodayUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("bed")
public class BedController {
    @Autowired
    private BedService bedService;

    /**
     * 查找所有空床位
     */
    @GetMapping("findNullBed")
    public ResponseData findNullBed(){
        return ResponseData.success("查找所有空床位成功", this.bedService.findNullBed());
    }

    /**
     * 增加床位信息
     */
    @GetMapping("updateBed")
    public ResponseData updateBed(Bed bed) {
        if (this.bedService.updateBed(bed))
        return ResponseData.success("增加床位成功！");
        return ResponseData.fail("增加床位失败！");
    }
    /**
     * 根据pId查询住院
     */
    @GetMapping("findBedByPid")
    public ResponseData findBedByPid(@RequestParam(value = "pId") int pId){
        return ResponseData.success("根据pId查询住院成功", this.bedService.findBedByPid(pId)) ;
    }
    /**
     * 分页模糊查询所有床位信息
     */
    @GetMapping("findAllBeds")
    public ResponseData findAllBeds(int pageNumber, int size, String query){
        return ResponseData.success("返回所有床位信息成功", this.bedService.findAllBeds(pageNumber, size, query));
    }
    /**
     * 根据id查找床位
     */
    @GetMapping("findBed")
    public ResponseData findBed(int bId){
        return ResponseData.success("根据id查找床位成功", this.bedService.findBed(bId));
    }
    /**
      * 增加床位信息
     */
    @GetMapping("addBed")
    @ResponseBody
    public ResponseData addBed(Bed bed) {
        Boolean bo = this.bedService.addBed(bed);
        if (bo) {
            return ResponseData.success("增加床位信息成功");
        }
        return ResponseData.fail("增加床位信息失败！床号或已被占用");
    }
    /**
     * 删除药物信息
     */
    @GetMapping("deleteBed")
    public ResponseData deleteBed(@RequestParam(value = "bId") int bId) {
        Boolean bo = this.bedService.deleteBed(bId);
        if (bo){
            return ResponseData.success("删除床位信息成功");
        }
        return ResponseData.fail("删除床位信息失败");
    }
    /**
     * 清空床位信息
     */
    @GetMapping("emptyBed")
    public ResponseData emptyBed(int bId){
        if(this.bedService.emptyBed(bId))
            return ResponseData.success("清空床位信息成功");
        return ResponseData.fail("清空床位信息失败");
    }
    /**
     * 统计今天挂号人数
     */
    @GetMapping("bedPeople")
    public ResponseData bedPeople(){
        String bStart = TodayUtil.getTodayYmd();
        return ResponseData.success("统计今天住院人数成功", this.bedService.bedPeople(bStart));
    }
}
