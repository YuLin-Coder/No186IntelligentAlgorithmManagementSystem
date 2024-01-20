package cn.hhu.aiams.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hhu.aiams.entity.Labelset;
import cn.hhu.aiams.mapper.LabelsetMapper;
import cn.hhu.aiams.util.JsonResult;

@RestController
@CrossOrigin
public class LabelsetController {
	@Autowired
	private LabelsetMapper labelsetMapper;
	
	
	@RequestMapping("findAllLabelset")
	public List<Labelset> findall() {
		List<Labelset> list = labelsetMapper.findAll();
		return list;
	}
	
	@RequestMapping("addLabelset")
	public JsonResult add(Labelset labelset) {
		JsonResult jsonResult = new JsonResult();
		//找重名
		Labelset result = labelsetMapper.findByLabelsetName(labelset.getName());
		if (result == null) {
			//无重名，可添加
			labelsetMapper.addLabelset(labelset);
			jsonResult.setState(1);
		} else {
			//重名，添加失败
			jsonResult.setState(2);
			jsonResult.setMessage("添加失败！标签组已经存在！");
		}
		return jsonResult;
	}
	
	@RequestMapping("delLabelset")
	public JsonResult del(Integer id) {
		JsonResult jsonResult = new JsonResult();
		Labelset result = labelsetMapper.findByID(id);
		if (result == null) {
			jsonResult.setMessage("找不到目标！");
			jsonResult.setState(1);
		} else {
			//删除
			labelsetMapper.delLabelset(id);
			jsonResult.setState(2);			
		}
		return jsonResult;
	}

	@RequestMapping("updLabelset")
	public JsonResult upd(Labelset labelset) {
		JsonResult jsonResult = new JsonResult();
		//result为未更新的算法数据,dataset为待更新的算法数据
		Labelset result = labelsetMapper.findByID(labelset.getId());
		if (result == null) {
			jsonResult.setMessage("找不到目标！");
			jsonResult.setState(1);
		} else {
			//名称未修改，可更新
			if(result.getName().equals(labelset.getName())) {
				labelsetMapper.updLabelset(labelset);
				jsonResult.setState(2);	
			}else {
				//名称修改后可能重名，找重名
				Labelset result1 = labelsetMapper.findByLabelsetName(labelset.getName());
				if(result1 == null) {
					//无重名，可更新
					labelsetMapper.updLabelset(labelset);
					jsonResult.setState(2);	
				}
				else{
					//重名
					jsonResult.setMessage("修改后的标签组名称已存在！");
					jsonResult.setState(1);
				}
			}
		}
		return jsonResult;
	}
}
