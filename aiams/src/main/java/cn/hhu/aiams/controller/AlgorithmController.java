package cn.hhu.aiams.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hhu.aiams.entity.Algorithm;
import cn.hhu.aiams.mapper.AlgorithmMapper;
import cn.hhu.aiams.util.JsonResult;


@RestController
@CrossOrigin
public class AlgorithmController {

	@Autowired
	private AlgorithmMapper algorithmMapper;
	
	
	@RequestMapping("findAllAlg")
	public List<Algorithm> findall() {
		List<Algorithm> list = algorithmMapper.findAll();
		return list;
	}
	
	@RequestMapping("addalg")
	public JsonResult add(Algorithm alg) {
		JsonResult jsonResult = new JsonResult();
		//找重名
		Algorithm result = algorithmMapper.findByAlgorithmName(alg.getName());
		if (result == null) {
			//无重名，可添加
			algorithmMapper.addAlgorithm(alg);
			jsonResult.setState(1);
		} else {
			//重名，添加失败
			jsonResult.setState(2);
			jsonResult.setMessage("添加失败！算法已经存在！");
		}
		return jsonResult;
	}
	
	@RequestMapping("delalg")
	public JsonResult del(Integer id) {
		JsonResult jsonResult = new JsonResult();
		Algorithm result = algorithmMapper.findByID(id);
		if (result == null) {
			jsonResult.setMessage("找不到目标！");
			jsonResult.setState(1);
		} else {
			//删除
			algorithmMapper.delAlgorithm(id);
			jsonResult.setState(2);			
		}
		return jsonResult;
	}

	@RequestMapping("updalg")
	public JsonResult upd(Algorithm alg) {
		JsonResult jsonResult = new JsonResult();
		//result为未更新的算法数据,alg为待更新的算法数据
		Algorithm result = algorithmMapper.findByID(alg.getId());
		if (result == null) {
			jsonResult.setMessage("找不到目标！");
			jsonResult.setState(1);
		} else {
			//名称未修改，可更新
			if(result.getName().equals(alg.getName())) {
				algorithmMapper.updAlgorithm(alg);
				jsonResult.setState(2);	
			}else {
				//名称修改后可能重名，找重名
				Algorithm result1 = algorithmMapper.findByAlgorithmName(alg.getName());
				if(result1 == null) {
					//无重名，可更新
					algorithmMapper.updAlgorithm(alg);
					jsonResult.setState(2);	
				}
				else{
					//重名
					jsonResult.setMessage("修改后的算法名称已存在！");
					jsonResult.setState(1);
				}
			}
		}
		return jsonResult;
	}
	
//	@RequestMapping("findByAlgname")
//	public Algorithm findbyname(String nowname) {
//		Algorithm result = algorithmMapper.findByAlgorithmName(nowname);
//		return result;
//	}
	
}
