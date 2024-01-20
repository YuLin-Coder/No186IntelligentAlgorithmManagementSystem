<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 校验模型
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-select v-model="query.model_name" placeholder="模型名称" class="handle-select mr10">
          <el-option key="1" label="yolo" value="yolo"></el-option>
          <el-option key="2" label="SSD" value="SSD"></el-option>
        </el-select>
        <el-input v-model="query.model_path" placeholder="模型路径" class="handle-select mr10"></el-input>
        <el-input v-model="query.classes_path" placeholder="类文件路径" class="handle-select mr10"></el-input>
        <el-button type="primary"  @click="handleLoad">加载模型</el-button>
      </div>
      <div class="handle-box">
        <el-input v-model="query.image" placeholder="类文件路径" class="handle-input mr10"></el-input>
          <el-button type="primary"  @click="handlePredict">检测</el-button>
      </div>
    </div>
    <div class="container">
      <img :src="codeUrl"/>
    </div>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { fetchLoadSSD, fetchLoadYolo,fetchPredictSSD,fetchPredictYolo} from "../api/index";


export default {
  name: "basetable",
  setup() {
    const query = reactive({
      model_name:"",
      model_path:"",
      classes_path:"",
      image:"",
    });

    const codeUrl= ref();
    // 加载模型
    const handleLoad = () => {
      if(query.model_name=="yolo"){
        fetchLoadYolo(query).then((res) => {
          alert(res);
        });
      }else{
        fetchLoadSSD(query).then((res) => {
          alert(res);
        });
      }
    };
    //检测
    const handlePredict = () => {
      if(query.model_name=="yolo"){
        fetchPredictYolo(query).then((res) => {
          //alert(res);
          codeUrl.value=res;
        });
      }else{
        fetchPredictSSD(query).then((res) => {
          codeUrl.value=res;
        });
      }
    };

    return {
      query,
      codeUrl,
      handleLoad,
      handlePredict,
    };
  },
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}
.handle-select {
  width: 200px;
}
.handle-input {
  width: 300px;
  display: inline-block;
}
.mr10 {
  margin-right: 10px;
}
</style>