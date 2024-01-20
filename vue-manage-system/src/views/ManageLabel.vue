<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 标签管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.name" placeholder="标签组名称" class="handle-input mr10"></el-input>
                <el-input v-model="query.des" placeholder="标签组描述" class="handle-input mr10"></el-input>
                <el-button type="primary"  @click="handleAdd">添加</el-button>
            </div>

            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="标签组名称"></el-table-column>
                <el-table-column prop="des" label="标签组描述"></el-table-column>
                <el-table-column prop="createtime" label="创建时间"></el-table-column>
                <el-table-column prop="updatetime" label="更新时间"></el-table-column>

                <el-table-column label="操作" width="250" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-delete" class="red"
                                 @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        <el-button type="text" icon="el-icon-edit"
                                   @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-search"
                                 @click="handleLook(scope.$index, scope.row)">查看</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
                    :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" v-model="editVisible" width="40%">
            <el-form label-width="100px">
                <el-form-item label="标签组名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="标签组描述">
                    <el-input v-model="form.des"></el-input>
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
import { fetchAllLabelset,fetchAddLabelset,fetchDelLabelset,fetchUpdLabelset } from "../api/Labelset";


export default {
    name: "basetable",
    setup() {
        const query = reactive({
            id:"",
            name: "",
            des: "",
            pageIndex: 1,
            pageSize: 5,
        });
        const tableData = ref([]);
        const pageTotal = ref(0);
        // 获取表格数据
        const getData = () => {
            fetchAllLabelset(query).then((res) => {
                tableData.value = res;
                //pageTotal.value = res.pageTotal || 50;
            });
        };
        getData();

        // 添加操作
        const handleAdd = () => {
            //query.pageIndex = 1;
            fetchAddLabelset(query).then((res) => {
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
                    fetchDelLabelset(query).then((res) => {
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

        //查看操作
        const handleLook = (index, row) => {
          alert("标签组里标签的增删改");
        };

        // 表格编辑时弹窗和保存
        const editVisible = ref(false);
        let form = reactive({
            id:"",
            name: "",
            des: "",
        });
        let idx = -1;
        const handleEdit = (index, row) => {
            idx = index;
            form.id = row.id;
            form.name = row.name;
            form.des = row.des;
            editVisible.value = true;
        };
        const saveEdit = () => {
            editVisible.value = false;
            //ElMessage.success(`修改第 ${idx + 1} 行成功`);
            // Object.keys(form).forEach((item) => {
            //     tableData.value[idx][item] = form[item];
            // });
            fetchUpdLabelset(form).then((res) => {
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
            handleLook,
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
