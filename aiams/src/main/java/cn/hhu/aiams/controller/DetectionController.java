package cn.hhu.aiams.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import java.awt.image.BufferedImage;

import cn.hhu.aiams.util.JsonResult;

@EnableAutoConfiguration
@ComponentScan
@Controller
@CrossOrigin
public class DetectionController {
	
	@RequestMapping("detectionByYolo")
	public String detection(String model_path,String image,Model model) {
		RestTemplate client = new RestTemplate();
		String apiURL = "http://192.168.3.7:5000/detection_html";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String,String> requestParam=new LinkedMultiValueMap<>();
	    requestParam.add("model_path", model_path);
	    requestParam.add("image",image );
	    HttpEntity<MultiValueMap<String,String>> request = new HttpEntity< MultiValueMap<String,String>>(requestParam, headers);
	    String s=request.toString();
	    ResponseEntity<String> entity2 = client.exchange(apiURL, HttpMethod.POST, request, String.class);
	    String body = entity2.getBody();
	    body=body.substring(1, body.length()-1);
	    model.addAttribute("data", body);
		return "detection";
	}
	
	
	@RequestMapping("load_modelByYolo")
	public String load_model(String model_name,String model_path,String classes_path,Model model) {
		System.out.print(model_name+"\n"+model_path+"\n"+classes_path);
		RestTemplate client = new RestTemplate();
		String apiURL = "http://192.168.3.7:5000/load_model";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String,String> requestParam=new LinkedMultiValueMap<>();
	    requestParam.add("model_name", model_name);
	    requestParam.add("model_path", model_path);
	    requestParam.add("classes_path",classes_path );
	    HttpEntity<MultiValueMap<String,String>> request = new HttpEntity< MultiValueMap<String,String>>(requestParam, headers);
	    String s=request.toString();
	    ResponseEntity<String> entity2 = client.exchange(apiURL, HttpMethod.POST, request, String.class);
	    String body = entity2.getBody();
	    body=body.substring(1, body.length()-1);
	    model.addAttribute("data", body);
		return "train";
	}
	
	@RequestMapping("predictByYolo")
	public String predict(String image,Model model) {
		System.out.print(image+"\n");
		RestTemplate client = new RestTemplate();
		String apiURL = "http://192.168.3.7:5000/predict";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String,String> requestParam=new LinkedMultiValueMap<>();
	    requestParam.add("image", image);
	    HttpEntity<MultiValueMap<String,String>> request = new HttpEntity< MultiValueMap<String,String>>(requestParam, headers);
	    String s=request.toString();
	    ResponseEntity<String> entity2 = client.exchange(apiURL, HttpMethod.POST, request, String.class);
	    String body = entity2.getBody();
	    body=body.substring(1, body.length()-1);
	    model.addAttribute("img", body);
		return "detection";
	}
	
	@RequestMapping("trainByYolo")
	public String train(String data_path,String jpeg_jpg,String get_label,String model_name,String model_path,Model model) {
		System.out.print(data_path+"\n");
		RestTemplate client = new RestTemplate();
		String apiURL = "http://192.168.3.7:5000/train_model";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String,String> requestParam=new LinkedMultiValueMap<>();
	    requestParam.add("data_path", data_path);
	    requestParam.add("jpeg_jpg", jpeg_jpg);
	    requestParam.add("get_label", get_label);
	    requestParam.add("model_name", model_name);
	    requestParam.add("model_path", model_path);
	    HttpEntity<MultiValueMap<String,String>> request = new HttpEntity< MultiValueMap<String,String>>(requestParam, headers);
	    String s=request.toString();
	    ResponseEntity<String> entity2 = client.exchange(apiURL, HttpMethod.POST, request, String.class);
	    String body = entity2.getBody();
	    body=body.substring(1, body.length()-1);
	    model.addAttribute("data", body);
		return "train";
	}
	
	@RequestMapping("image_equality")
	public String image_equality(String image_path,Model model) {
		RestTemplate client = new RestTemplate();
		String apiURL = "http://192.168.3.7:5000/image_equality";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String,String> requestParam=new LinkedMultiValueMap<>();
	    requestParam.add("image_path", image_path);
	    HttpEntity<MultiValueMap<String,String>> request = new HttpEntity< MultiValueMap<String,String>>(requestParam, headers);
	    String s=request.toString();
	    ResponseEntity<String> entity2 = client.exchange(apiURL, HttpMethod.POST, request, String.class);
	    String body = entity2.getBody();
	    body=body.substring(1, body.length()-1);
	    model.addAttribute("data", body);
		return "image_equality";
	}

	@RequestMapping(value = "/home")
    public ModelAndView home(@RequestParam("name") String name ) throws IOException {
        ModelAndView view = new ModelAndView("train");
        view.addObject("image_name", name);
        return view;
    }

    @RequestMapping(value = "/image/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("image_name") String image_name) throws Exception{

        byte[] imageContent ;
        String path = "D:/java_code/aiams/aiams/src/main/resources/templates/"+image_name+".jpg";
        System.out.print(path);
        imageContent = fileToByte(new File(path));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }
    
    @RequestMapping("load_modelBySSD")
	public String l(String model_path,String model_name, String classes_path,Model model) {
		RestTemplate client = new RestTemplate();
		String apiURL = "http://192.168.3.101:5000/ssd_predict_setpath";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String,String> requestParam=new LinkedMultiValueMap<>();
	    requestParam.add("model_path",model_path );
	    requestParam.add("model_name",model_name );
	    requestParam.add("cls_path",classes_path );
	    HttpEntity<MultiValueMap<String,String>> request = new HttpEntity< MultiValueMap<String,String>>(requestParam, headers);
	    String s=request.toString();
	    
	    //获取响应
	    ResponseEntity<String> entity2 = client.exchange(apiURL, HttpMethod.POST, request, String.class);
	    String body = entity2.getBody();
	    body=body.substring(1, body.length()-1);
//	    System.out.print("!!!!!!!!!");
//	    System.out.println(body);
	    model.addAttribute("data", body);
	    System.out.print(model);
		return "load_model1";
	}
	
	@RequestMapping("detectionBySSD")
	public String detection(String image,Model model) {
		RestTemplate client = new RestTemplate();
		String apiURL = "http://192.168.3.101:5000/ssd_ssd";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String,String> requestParam=new LinkedMultiValueMap<>();
	    requestParam.add("image",image );
	    HttpEntity<MultiValueMap<String,String>> request = new HttpEntity< MultiValueMap<String,String>>(requestParam, headers);
	    String s=request.toString();   
	    //获取响应
	    ResponseEntity<String> entity2 = client.exchange(apiURL, HttpMethod.POST, request, String.class);
	    String body = entity2.getBody();
	    System.out.println(body);
	    body=body.substring(5, body.length()-1);
	    System.out.print("!!!!!");
	    System.out.println(body);
	    model.addAttribute("data", body);
	    System.out.print(model);
		return "detection1";
	}
		

	@RequestMapping("trainBySSD")
	public String trainBySSD(String data_path,String jpeg_jpg,String get_label,String model_name,String model_path,Model model) {
		RestTemplate client = new RestTemplate();
		String apiURL = "http://192.168.3.101:5000/train_model";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    MultiValueMap<String,String> requestParam=new LinkedMultiValueMap<>();
	    requestParam.add("data_path", data_path);
	    requestParam.add("jpeg_jpg", jpeg_jpg);
	    requestParam.add("get_label", get_label);
	    requestParam.add("model_name", model_name);
	    requestParam.add("model_path", model_path);
	    HttpEntity<MultiValueMap<String,String>> request = new HttpEntity< MultiValueMap<String,String>>(requestParam, headers);
	    String s=request.toString();
	    ResponseEntity<String> entity2 = client.exchange(apiURL, HttpMethod.POST, request, String.class);
	    String body = entity2.getBody();
	    body=body.substring(1, body.length()-1);
	    model.addAttribute("data", body);
		return "train1";
	}	
	

    public static byte[] fileToByte(File img) throws Exception {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "png", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return bytes;
    }
	
	
}
