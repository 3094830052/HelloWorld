package com.hxts.unicorn.modules.resident.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxts.unicorn.modules.resident.dto.ResidentTempDto;
import com.hxts.unicorn.modules.resident.model.ResidentInfoTemp;
import com.hxts.unicorn.modules.resident.model.ResidentScoreRecord;
import com.hxts.unicorn.modules.resident.service.ISrvResidentScore;
import com.hxts.unicorn.modules.resident.service.ISrvResidentTempInfo;
import com.hxts.unicorn.platform.AppModuleErrorException;
import com.hxts.unicorn.platform.interfaces.biz.ResidentScoreInfo;
import com.hxts.unicorn.platform.interfaces.biz.ResidentTemp;

@RestController
@RequestMapping("/m/gridman")
public class GridManController {
	@Autowired
	private ISrvResidentTempInfo srvResidentTempInfo;
	@Autowired
	private ISrvResidentScore srvResidentScore;

	/**
	 * <根据网格员状态查询基础人口信息>
	 * 
	 * @param 网格员确认状态status
	 * @return 
	 */
//	@GetMapping("list")
//	public PageInfo<ResidentTempDto> view(@RequestParam(required = false) Integer status,
//			@RequestParam(required = false, defaultValue = "1") int pageNum,
//			@RequestParam(required = false, defaultValue = "10") int pageSize) {
//		PageHelper.startPage(pageNum, pageSize);
//		PageInfo<ResidentTempDto> r = new PageInfo<ResidentTempDto>(srvResidentTempInfo.getResidentTemp(status));
//		return r;
//	}

	/**
	 * <网格员根据residentBaseId查询基础人口信息>
	 * 
	 * @param residentBaseId
	 * @return 
	 */
	@GetMapping("view/{residentBaseId}")
	public ResidentTemp view(@PathVariable Integer residentBaseId) {
		if (srvResidentTempInfo.selectByResidentBaseId(residentBaseId).getStatus() == 1) {
			throw new AppModuleErrorException("信息已保存，无法查看！");
		} else
			return srvResidentTempInfo.selectByResidentBaseId(residentBaseId);
	}

	/**
	 * <网格员保存信息到正式表>
	 * 
	 * @param residentBaseId
	 * @return
	 * 
	 */
	@GetMapping("save")
	public int save(Integer residentBaseId) {
		ResidentTemp ResidentTemp = srvResidentTempInfo.selectByResidentBaseId(residentBaseId);
		String name=srvResidentTempInfo.selectByResidentBaseId(residentBaseId).getName();
		String openId = srvResidentTempInfo.selectByResidentBaseId(residentBaseId).getOpenId();
		int res=srvResidentScore.selectByOpenId(openId).getResidentBaseId();
		int score = srvResidentTempInfo.getPoints();
		ResidentScoreInfo r = new ResidentScoreInfo();
		ResidentScoreRecord re = new ResidentScoreRecord();
		if (srvResidentScore.selectByOpenId(openId).getScore() == null) {
			r.setResidentBaseId(srvResidentScore.selectByOpenId(openId).getResidentBaseId());
			r.setOpenId(openId);
			r.setScore(score);
			re.setResidentBaseId(res);
			re.setOpenId(openId);
			re.setScore(100);
			re.setType("人口信息提交");
			re.setRecordId(res);
			re.setScoreDetails(" 新增"+name+"基础信息  ");
			srvResidentScore.save(r, re);
		}
		if (ResidentTemp.getHouseholderRelationship() != null || ResidentTemp.getInflowReason() != null
				|| ResidentTemp.getPurpose() != null) {
			r.setScore(score + srvResidentScore.selectByOpenId(openId).getScore());
			re.setOpenId(openId);
			re.setResidentBaseId(res);
			re.setScore(100);
			re.setType("人口信息提交");
			re.setRecordId(res);
			re.setScoreDetails("新增"+name+"其他信息");
			srvResidentScore.save(r, re);
		}
		if (ResidentTemp.getHouseId() != null) {
			r.setScore(score + srvResidentScore.selectByOpenId(openId).getScore());
			re.setResidentBaseId(res);
			re.setOpenId(openId);
			re.setScore(100);
			re.setType("人口信息提交");
			re.setRecordId(res);
			re.setScoreDetails("新增"+name+"房屋信息");
			srvResidentScore.save(r, re);
		}
		return srvResidentTempInfo.save(ResidentTemp);
	}

	/**
	 * <网格员驳回用户提交信息>
	 * 
	 * @param residentBaseId
	 * @return
	 */
	@GetMapping("reject")
	public int reject(Integer residentBaseId) {
		ResidentTemp ResidentTemp = srvResidentTempInfo.selectByResidentBaseId(residentBaseId);
		return srvResidentTempInfo.reject(ResidentTemp);
	}

	/**
	 * <网格员修改用户信息>
	 * 
	 * @param residentBaseId
	 * @return
	 */
	@PutMapping("update")
	public int update(ResidentTemp ResidentTemp) {
		// ResidentTemp
		// ResidentTemp=srvResidentTempInfo.selectByResidentBaseId(residentBaseId);
		return srvResidentTempInfo.update(ResidentTemp);
	}

	/**
	 * <网格员删除用户信息>
	 * 
	 * @param residentBaseId
	 * @return
	 */
	@DeleteMapping("delete/{residentBaseId}")
	public int delete(@PathVariable Integer residentBaseId) {
		return srvResidentTempInfo.deleteByResidentId(residentBaseId);
	}

}
