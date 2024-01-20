package cn.hhu.aiams.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hhu.aiams.entity.Model;
import cn.hhu.aiams.mapper.ModelMapper;
import cn.hhu.aiams.util.JsonResult;

@RestController
@CrossOrigin
public class ModelController {
	@Autowired
	private ModelMapper modelMapper;
	
	@RequestMapping("findAllMod")
	public List<Model> findall() {
		List<Model> list = modelMapper.findAll();
		return list;
	}
	
	//添加模型
	@RequestMapping("addMod")
	public JsonResult add(Model mod) {
		JsonResult jsonResult = new JsonResult();
		//找重名
		Model result = modelMapper.findByModelName(mod.getModelname());
		if (result == null) {
			//无重名，可添加
			modelMapper.addModel(mod);
			jsonResult.setState(1);
		} else {
			//重名，添加失败
			jsonResult.setState(2);
			jsonResult.setMessage("添加失败！模型名称已经存在！");
		}
		return jsonResult;
	}
	
	//删除模型
	@RequestMapping("delMod")
	public JsonResult del(Integer id) {
		JsonResult jsonResult = new JsonResult();
		Model result = modelMapper.findByID(id);
		if (result == null) {
			jsonResult.setMessage("找不到目标！");
			jsonResult.setState(1);
		} else {
			//删除
			modelMapper.delModel(id);
			jsonResult.setState(2);			
		}
		return jsonResult;
	}

	//更新模型
	@RequestMapping("updMod")
	public JsonResult upd(Model mod) {
		JsonResult jsonResult = new JsonResult();
		//result为未更新的算法数据,alg为待更新的算法数据
		Model result = modelMapper.findByID(mod.getId());
		if (result == null) {
			jsonResult.setMessage("找不到目标！");
			jsonResult.setState(1);
		} else {
			//名称未修改，可更新
			if(result.getModelname().equals(mod.getModelname())) {
				modelMapper.updModel(mod);
				jsonResult.setState(2);	
			}else {
				//名称修改后可能重名，找重名
				Model result1 = modelMapper.findByModelName(mod.getModelname());
				if(result1 == null) {
					//无重名，可更新
					modelMapper.updModel(mod);
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

	//发布模型
	@RequestMapping("relMod")
	public JsonResult rel(Integer id) {
		JsonResult jsonResult = new JsonResult();
		Model result = modelMapper.findByID(id);
		if (result == null) {
			jsonResult.setMessage("找不到目标！");
			jsonResult.setState(1);
		} else {
			jsonResult.setMessage("接口地址");
			jsonResult.setState(2);			
		}
		return jsonResult;
	}
}
