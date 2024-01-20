package cn.hhu.aiams.mapper;

import java.util.List;

import cn.hhu.aiams.entity.Labelset;

public interface LabelsetMapper {
	Integer addLabelset(Labelset labelset);
	Labelset findByLabelsetName(String name);	
	List<Labelset> findAll();
	Labelset findByID(Integer id);
	Integer delLabelset(Integer id);
	Integer updLabelset(Labelset labelset);
}
