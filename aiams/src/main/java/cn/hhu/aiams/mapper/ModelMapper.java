package cn.hhu.aiams.mapper;

import java.util.List;

import cn.hhu.aiams.entity.Model;

public interface ModelMapper {

	List<Model> findAll();
	void delModel(Integer id);
	Model findByModelName(String modelname);
	void addModel(Model mod);
	Model findByID(Integer id);
	void updModel(Model mod);

}
