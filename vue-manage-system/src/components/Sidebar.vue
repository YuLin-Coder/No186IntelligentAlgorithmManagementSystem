<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
            text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template #title>
                            <i :class="item.icon"></i>
                            <span>{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu v-if="subItem.subs" :index="subItem.index" :key="subItem.index">
                                <template #title>{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem, i) in subItem.subs" :key="i" :index="threeItem.index">
                                    {{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item v-else :index="subItem.index" :key="subItem.index">{{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <template #title>{{ item.title }}</template>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import { computed, watch } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
export default {
    setup() {
        const items = [
            {
                icon: "el-icon-lx-home",
                index: "/dashboard",
                title: "系统首页",
            },
            {
                icon: "el-icon-lx-copy",
                index: "1",
                title: "样本中心",
                subs: [
                    {
                        index: "/managedata",
                        title: "数据管理",
                    },
                    {
                        index: "/managelabel",
                        title: "标签管理",
                    },
                    {
                        index: "/404",
                        title: "在线标注",
                    },
                    {
                        index: "/404",
                        title: "智能标注",
                    },
                ],
            },
            {
                icon: "el-icon-lx-favor",
                index: "2",
                title: "算法中心",
                subs: [
                    {
                        index: "/setupalgorithm",
                        title: "算法配置",
                    },
                    {
                        index: "/checkalgorithm",
                        title: "算法验证",
                    },
                    {
                        index: "/algorithmeval",
                        title: "算法性能评估",
                    },
                ],
            },
            {
                icon: "el-icon-lx-cascades",
                index: "3",
                title: "模型中心",
                subs: [
                    {
                        index: "/create-model",
                        title: "创建模型",
                    },
                    {
                        index: "/train-model",
                        title: "训练模型",
                    },
                    {
                        index: "/check-model",
                        title: "校验模型",
                    },
                    {
                        index: "/release-model",
                        title: "发布模型",
                    },
                ],
            },

            {
                icon: "el-icon-lx-emoji",
                index: "/table",
                title: "资源监测",
            },
        ];

        const route = useRoute();

        const onRoutes = computed(() => {
            return route.path;
        });

        const store = useStore();
        const collapse = computed(() => store.state.collapse);

        return {
            items,
            onRoutes,
            collapse,
        };
    },
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
