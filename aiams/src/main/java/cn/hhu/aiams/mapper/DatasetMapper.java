package cn.hhu.aiams.mapper;

import java.util.List;

import cn.hhu.aiams.entity.Algorithm;
import cn.hhu.aiams.entity.Dataset;

public interface DatasetMapper {

	Integer addDataset(Dataset dataset);
	Dataset findByDatasetName(String name);	
	List<Dataset> findAll();
	Dataset findByID(Integer id);
	Integer delDataset(Integer id);
	Integer updDataset(Dataset dataset);

}
