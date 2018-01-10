package com.hxts.unicorn.modules.resident.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PersonRecordPositionMapper {

	void dropTableAboutPositon(@Param("table_name") String table_name);

	void creatTableAboutPositon(@Param("table_name") String table_name);

	String checkExistence(String position);
	
	void addNewPositions(List<String> positions);
	
	void addNewPosition(String position);

	List<String> selectSimilarPosition(@Param("position") String keyword);

	
}
