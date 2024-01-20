<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 创建模型
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-input v-model="query.modelname" placeholder="模型名称" class="handle-input mr10"></el-input>
        <el-select v-model="query.algname" placeholder="调用算法" class="handle-select mr10">
          <el-option key="1" label="YOLOv4" value="YOLOv4"></el-option>
          <el-option key="2" label="SSD" value="SSD"></el-option>
        </el-select>
        <el-select v-model="query.sampleset" placeholder="样本集" class="handle-select mr10">
          <el-option key="1" label="样本集1" value="样本集1"></el-option>
          <el-option key="2" label="样本集2" value="样本集2"></el-option>
        </el-select>
        <el-button type="primary"  @click="handleAdd">添加</el-button>
      </div>

      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
        <el-table-column prop="modelname" label="模型名称"></el-table-column>
        <el-table-column prop="algname" label="调用算法"></el-table-column>
        <el-table-column prop="sampleset" label="样本集"></el-table-column>
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <el-tag :type="
                            scope.row.state === '加载完成'
                                ? 'success'
                                : scope.row.state === '加载中'
                                ? 'danger'
                                : ''
                        ">{{ scope.row.state }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createtime" label="创建时间"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
            </el-button>
            <el-button type="text" icon="el-icon-delete" class="red"
                       @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                       :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="模型名称">
          <el-input v-model="form.modelname"></el-input>
        </el-form-item>
        <el-form-item label="调用算法">
          <el-select v-model="form.algname">
            <el-option key="1" label="YOLOv4" value="YOLOv4"></el-option>
            <el-option key="2" label="SSD" value="SSD"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="样本集">
          <el-input v-model="form.sampleset"></el-input>
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
import { fetchAllMod, fetchAddMod, fetchDelMod, fetchUpdMod} from "../api/index";


export default {
  name: "basetable",
  setup() {
    const query = reactive({
      id:"",
      modelname: "",
      algname: "",
      sampleset:"",
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

    // 添加操作
    const handleAdd = () => {
      //query.pageIndex = 1;
      fetchAddMod(query).then((res) => {
        if (res.state == 1) {
          alert("添加成功！");
          location.reload();
        } else {
          alert(res.message);
        }
      });
    };
    // 分页导航
    const handlePageChange = (val) => {
      query.pageIndex = val;
      getData();
    };

    // 删除操作
    const handleDelete = (index,row) => {
      // 二次确认删除
      ElMessageBox.confirm("确定要删除吗？", "提示", {
        type: "warning",
      })
          .then(() => {
            query.id = row.id;
            fetchDelMod(query).then((res) => {
              if (res.state == 2) {
                alert("删除成功！");
                location.reload();
              } else {
                alert(res.message);
              }
            });
          })
          .catch(() => {});
    };

    // 表格编辑时弹窗和保存
    const editVisible = ref(false);
    let form = reactive({
      id:"",
      modelname: "",
      algname: "",
      sampleset:"",
    });
    let idx = -1;
    const handleEdit = (index, row) => {
      idx = index;
      form.id = row.id;
      Object.keys(form).forEach((item) => {
        form[item] = row[item];
      });
      editVisible.value = true;
    };
    const saveEdit = () => {
      editVisible.value = false;
      //ElMessage.success(`修改第 ${idx + 1} 行成功`);
      // Object.keys(form).forEach((item) => {
      //     tableData.value[idx][item] = form[item];
      // });
      fetchUpdMod(form).then((res) => {
        if (res.state == 2) {
          alert("修改成功！");
          location.reload();
        } else {
          alert(res.message);
        }
      });
    };

    return {
      query,
      tableData,
      pageTotal,
      editVisible,
      form,
      handleAdd,
      handlePageChange,
      handleDelete,
      handleEdit,
      saveEdit,
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
.table {
  width: 100%;
  font-size: 14px;
}
.red {
  color: #ff0000;
}
.mr10 {
  margin-right: 10px;
}
.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>