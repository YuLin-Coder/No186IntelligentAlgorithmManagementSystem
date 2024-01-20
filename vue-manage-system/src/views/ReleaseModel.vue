<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 发布模型
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
                            scope.row.state === '已发布'
                                ? 'success'
                                : scope.row.state === '发布中'
                                ? 'danger'
                                : ''
                        ">{{ scope.row.state }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createtime" label="创建时间"></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button type="text" @click="handleRelease(scope.$index, scope.row)">发布</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                       :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { fetchAllMod, ReleaseMod,} from "../api/index";


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

    // 发布操作
    const handleRelease = (index,row) => {
      query.id = row.id;
      ReleaseMod(query).then((res) => {
          alert(res.message);
      });
    };

    return {
      query,
      tableData,
      pageTotal,
      handlePageChange,
      handleRelease,
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