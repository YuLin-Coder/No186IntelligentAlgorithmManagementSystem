package cn.hhu.aiams.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hhu.aiams.entity.Dataset;
import cn.hhu.aiams.mapper.DatasetMapper;
import cn.hhu.aiams.util.JsonResult;

@RestController
@CrossOrigin
public class DatasetController {
	@Autowired
	private DatasetMapper datasetMapper;
	
	
	@RequestMapping("findAllDataset")
	public List<Dataset> findall() {
		List<Dataset> list = datasetMapper.findAll();
		return list;
	}
	
	@RequestMapping("addDataset")
	public JsonResult add(Dataset dataset) {
		JsonResult jsonResult = new JsonResult();
		//找重名
		Dataset result = datasetMapper.findByDatasetName(dataset.getName());
		if (result == null) {
			//无重名，可添加
			datasetMapper.addDataset(dataset);
			jsonResult.setState(1);
		} else {
			//重名，添加失败
			jsonResult.setState(2);
			jsonResult.setMessage("添加失败！数据集已经存在！");
		}
		return jsonResult;
	}
	
	@RequestMapping("delDataset")
	public JsonResult del(Integer id) {
		JsonResult jsonResult = new JsonResult();
		Dataset result = datasetMapper.findByID(id);
		if (result == null) {
			jsonResult.setMessage("找不到目标！");
			jsonResult.setState(1);
		} else {
			//删除
			datasetMapper.delDataset(id);
			jsonResult.setState(2);			
		}
		return jsonResult;
	}

	@RequestMapping("updDataset")
	public JsonResult upd(Dataset dataset) {
		JsonResult jsonResult = new JsonResult();
		//result为未更新的算法数据,dataset为待更新的算法数据
		Dataset result = datasetMapper.findByID(dataset.getId());
		if (result == null) {
			jsonResult.setMessage("找不到目标！");
			jsonResult.setState(1);
		} else {
			//名称未修改，可更新
			if(result.getName().equals(dataset.getName())) {
				datasetMapper.updDataset(dataset);
				jsonResult.setState(2);	
			}else {
				//名称修改后可能重名，找重名
				Dataset result1 = datasetMapper.findByDatasetName(dataset.getName());
				if(result1 == null) {
					//无重名，可更新
					datasetMapper.updDataset(dataset);
					jsonResult.setState(2);	
				}
				else{
					//重名
					jsonResult.setMessage("修改后的数据集名称已存在！");
					jsonResult.setState(1);
				}
			}
		}
		return jsonResult;
	}
	
	
	@PostMapping(value = "/upload")
	public void uploadFile(@RequestParam("files") MultipartFile[] files) throws IOException {
	    // 1. 用数组MultipartFile[]来表示多文件,所以遍历数组,对其中的文件进行逐一操作
	    for (MultipartFile file : files) {
	        // 2. 通过一顿file.getXXX()的操作,获取文件信息。
	        // 2.1 这里用文件名举个栗子
	        String filename = file.getOriginalFilename();
	        // 3. 接下来调用方法来保存文件到本地磁盘,返回的是保存后的文件路径
	        String filePath = savaFileByNio((FileInputStream) file.getInputStream(), filename);
	        // 4. 保存文件信息到数据库
	        // 4.1 搞个实体类，把你需要的文件信息保存到实体类中
	        // 4.2 调用Service层或者Dao层，保存数据库即可。
	    }
	}

	public static String savaFileByNio(FileInputStream fis, String fileName) {
	    // 这个路径最后是在: 你的项目路径/FileSpace  也就是和src同级
	    String fileSpace = System.getProperty("user.dir") + File.separator + "FileSpace";
	    String path = fileSpace + File.separator + fileName;
	    // 判断父文件夹是否存在
	    File file = new File(path);
	    if (file.getParentFile() != null || !file.getParentFile().isDirectory()) {
	        file.getParentFile().mkdirs();
	    }
	    // 通过NIO保存文件到本地磁盘
	    try {
	        FileOutputStream fos = new FileOutputStream(path);
	        FileChannel inChannel = fis.getChannel();
	        FileChannel outChannel = fos.getChannel();
	        inChannel.transferTo(0, inChannel.size(), outChannel);
	        inChannel.close();
	        outChannel.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return path;
	}

}
