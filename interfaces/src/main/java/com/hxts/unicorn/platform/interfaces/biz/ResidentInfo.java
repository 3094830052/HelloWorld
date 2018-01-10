package com.hxts.unicorn.platform.interfaces.biz;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

/**
 * 
 * <人口所有信息>
 * <为方便前台传参，此类的属性名缩写成属性首字母小写，有重复的就再加一个字母区分>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年11月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResidentInfo implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 69841858664692552L;
	/**
	 * 人口基础信息类
	 */
	@Valid
	private ResidentBaseInfo b;
	/**
	 * 户籍人口业务信息类
	 */
	@Valid
	private HouseholdResidentInfo h;
	/**
	 * 流动人口业务信息类
	 */
	@Valid
	private FloatingResidentInfo f;
	/**
	 * 境外人口业务信息类
	 */
	@Valid
	private ForeignerResidentInfo fo;
	/**
	 * 留守人口标签信息类
	 */
	@Valid
	private LeftBehindResidentInfo lb;
	/**
	 * 出租屋承租人标签信息类
	 */
	@Valid
	private RenterInfo r;
	/**
	 * 重点青少年标签信息类
	 */
	@Valid
	private KeyTeenagerInfo k;
	/**
	 * 低保人员标签信息类
	 */
	@Valid
	private SubsistenceAllowanceInfo s;
	/**
	 * 残障人士标签信息类
	 */
	@Valid
	private DisabledPeopleInfo d;
	/**
	 * 独居老人标签信息类
	 */
	@Valid
	private LivingAloneAgedInfo l;
	/**
	 * 吸毒者标签信息类
	 */
	@Valid
	private DrugInfo dr;
	/**
	 * 刑满释放人员标签信息类
	 */
	@Valid
	private EmancipistInfo e;
	/**
	 * 非法上访人员标签信息类
	 */
	@Valid
	private IllegalPertitionerInfo i;
	/**
	 * 返回此人口的所有标签，key为标签类型，value为对应的标签id
	 */
	private Map<String,Integer> labels;
	/**
	 * 家庭关系
	 */
	private List<FamilyRelationInfo> relations;
	/**
	 * 关联房屋
	 */
	private List<ResidentOfHouseInfo> houses;
	
	public ResidentBaseInfo getB() {
		return b;
	}
	public void setB(ResidentBaseInfo b) {
		this.b = b;
	}
	public HouseholdResidentInfo getH() {
		return h;
	}
	public void setH(HouseholdResidentInfo h) {
		this.h = h;
	}
	public FloatingResidentInfo getF() {
		return f;
	}
	public void setF(FloatingResidentInfo f) {
		this.f = f;
	}
	public ForeignerResidentInfo getFo() {
		return fo;
	}
	public void setFo(ForeignerResidentInfo fo) {
		this.fo = fo;
	}
	public LeftBehindResidentInfo getLb() {
		return lb;
	}
	public void setLb(LeftBehindResidentInfo lb) {
		this.lb = lb;
	}
	public RenterInfo getR() {
		return r;
	}
	public void setR(RenterInfo r) {
		this.r = r;
	}
	public KeyTeenagerInfo getK() {
		return k;
	}
	public void setK(KeyTeenagerInfo k) {
		this.k = k;
	}
	public SubsistenceAllowanceInfo getS() {
		return s;
	}
	public void setS(SubsistenceAllowanceInfo s) {
		this.s = s;
	}
	public LivingAloneAgedInfo getL() {
		return l;
	}
	public void setL(LivingAloneAgedInfo l) {
		this.l = l;
	}
	public DisabledPeopleInfo getD() {
		return d;
	}
	public void setD(DisabledPeopleInfo d) {
		this.d = d;
	}
	public DrugInfo getDr() {
		return dr;
	}
	public void setDr(DrugInfo dr) {
		this.dr = dr;
	}
	public EmancipistInfo getE() {
		return e;
	}
	public void setE(EmancipistInfo e) {
		this.e = e;
	}
	public IllegalPertitionerInfo getI() {
		return i;
	}
	public void setI(IllegalPertitionerInfo i) {
		this.i = i;
	}
	public Map<String, Integer> getLabels() {
		return labels;
	}
	public void setLabels(Map<String, Integer> labels) {
		this.labels = labels;
	}
	public List<FamilyRelationInfo> getRelations() {
		return relations;
	}
	public void setRelations(List<FamilyRelationInfo> relations) {
		this.relations = relations;
	}
	public List<ResidentOfHouseInfo> getHouses() {
		return houses;
	}
	public void setHouses(List<ResidentOfHouseInfo> houses) {
		this.houses = houses;
	}
	
}
