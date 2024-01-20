package cn.hhu.aiams.mapper;

import java.util.List;

import cn.hhu.aiams.entity.Algorithm;
import cn.hhu.aiams.entity.User;


public interface AlgorithmMapper {

	Integer addAlgorithm(Algorithm alg);
	Algorithm findByAlgorithmName(String name);	
	List<Algorithm> findAll();
	Algorithm findByID(Integer id);
	Integer delAlgorithm(Integer id);
	Integer updAlgorithm(Algorithm alg);
}
