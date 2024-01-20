<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 训练模型
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
        <el-table-column prop="modelname" label="模型名称"></el-table-column>
        <el-table-column prop="algname" label="调用算法"></el-table-column>
        <el-table-column prop="sampleset" label="样本集"></el-table-column>
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <el-tag :type="
                            scope.row.state === '训练完成'
                                ? 'success'
                                : scope.row.state === '训练中'
                                ? 'danger'
                                : ''
                        ">{{ scope.row.state }}</el-tag>
          </template>
        </el-table-column>
<!--        <el-table-column prop="createtime" label="创建时间"></el-table-column>-->
        <el-table-column prop="trainstarttime" label="训练开始时间"></el-table-column>
        <el-table-column prop="trainendtime" label="训练结束时间"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button type="text" @click="handleTrain(scope.$index, scope.row)">开始训练</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                       :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
      </div>
    </div>
    <!-- 开始训练弹出框 -->
    <el-dialog title="训练" v-model="editVisible" width="40%">
      <el-form label-width="100px">
        <el-form-item label="数据集">
          <el-input v-model="form.data_path"></el-input>
        </el-form-item>
        <el-form-item label="图片转换">
          <el-input v-model="form.jpeg_jpg"></el-input>
        </el-form-item>
        <el-form-item label="数据集划分">
          <el-input v-model="form.get_label"></el-input>
        </el-form-item>
        <el-form-item label="模型名字">
          <el-input v-model="form.model_name"></el-input>
        </el-form-item>
        <el-form-item label="模型权值">
          <el-input v-model="form.model_path"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { fetchAllMod,fetchTrianSSD,fetchTrianYolo} from "../api/index";


export default {
  name: "basetable",
  setup() {
    const query = reactive({
      id:"",
      pageIndex: 1,
      pageSize: 5,
    });
    const tableData = ref([]);
    const pageTotal = ref(0);
    // 获取表格数据
    const getData = () => {
      fetchAllMod(query).then((res) => {
        tableData.value = res;
        //pageTotal.value = res.pageTotal || 50;
      });
    };
    getData();

    // 分页导航
    const handlePageChange = (val) => {
      query.pageIndex = val;
      getData();
    };

    // 开始训练
    const editVisible = ref(false);
    let form = reactive({
      id:"",
      algname:"",
      data_path: "",
      jpeg_jpg: "",
      get_label:"",
      model_name:"",
      model_path:"",
    });
    let idx = -1;
    const handleTrain = (index, row) => {
      idx = index;
      form.id = row.id;
      form.algname = row.algname;
      form.model_name = row.modelname;
      editVisible.value = true;
    };
    const saveEdit = () => {
      editVisible.value = false;
      if(form.algname=="YOLOv4"){
        fetchTrianYolo(form);
      }else{
        fetchTrianSSD(form);
      }
    };

    return {
      query,
      tableData,
      pageTotal,
      editVisible,
      form,
      handlePageChange,
      handleTrain,
      saveEdit
    };
  },
};
</script>

<style scoped>
.table {
  width: 100%;
  font-size: 14px;
}
</style>