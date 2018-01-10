package com.hxts.unicorn.modules.resident.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.modules.resident.service.ISrvCar;
import com.hxts.unicorn.platform.interfaces.biz.CarInfo;

@RestController
@RequestMapping("/m/resident/car")
public class CarController {

	@Autowired
	private ISrvCar srvCar;

	/**
	 * <根据人员id查询人车标签信息>
	 * 
	 * @param residentBaseId
	 *            
	 * @return 人车标签信息
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping("view")
	public PageInfo<CarInfo> view(@RequestParam(required = false) Integer residentBaseId,
			@RequestParam(required = false, defaultValue = "1") int pageNum,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<CarInfo> c = new PageInfo<>(srvCar.getcarInfo(residentBaseId));
		return c;
	}
	/**
	 * <根据人员id查询人车标签信息>
	 * 
	 * @param residentBaseId
	 *            
	 * @return 人车标签信息
	 * @see [类、类#方法、类#成员]
	 */
	@GetMapping("view/{carId}")
	public CarInfo getcarInfo(@PathVariable Integer carId){
		return srvCar.selectByCarInfoId(carId);
	}
	/**
	 * <保存新增或编辑人车标签信息>
	 * 
	 * @param carInfo
	 *            
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@PostMapping("save")
	public int save(CarInfo carInfo){
		return srvCar.save(carInfo);
	}
	
	/**
	 * 
	 * <根据人车信息id删除人车标签信息>
	 * 
	 * @param carId
	 *            
	 * @return 受影响行数
	 * @see [类、类#方法、类#成员]
	 */
	@DeleteMapping("delete/{carId}")
	public int deleteByCarId(@PathVariable Integer carId){
		return srvCar.deleteByCarInfo(carId);
	}
	
	
}
