<template>
	<!-- 卡片容器，提供阴影和边框效果 -->
	<el-card>

		<!-- 内联表单，用于查询条件输入 -->
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
		<#list queryList as field>
			<el-form-item>
			<#if field.queryFormType == 'text' || field.queryFormType == 'textarea' || field.queryFormType == 'editor'>
			  <el-input v-model="state.queryForm.${field.attrName}" placeholder="${field.fieldComment!}" clearable></el-input>
			<#elseif field.queryFormType == 'select'>
			  <#if field.formDict??>
			  <fast-select v-model="state.queryForm.${field.attrName}" dict-type="${field.formDict}" style="width: 100px" placeholder="${field.fieldComment!}" clearable></fast-select>
			  <#else>
			  <el-select v-model="state.queryForm.${field.attrName}" style="width: 100px" placeholder="${field.fieldComment!}">
				<el-option label="选择" value="0"></el-option>
			  </el-select>
			  </#if>
			<#elseif field.queryFormType == 'radio'>
			  <#if field.formDict??>
			  <fast-radio-group v-model="state.queryForm.${field.attrName}" dict-type="${field.formDict}"></fast-radio-group>
			  <#else>
			  <el-radio-group v-model="state.queryForm.${field.attrName}">
				<el-radio :label="0">单选</el-radio>
			  </el-radio-group>
			  </#if>
			<#elseif field.queryFormType == 'date'>
			  <el-date-picker
				v-model="${field.attrName}Ref"
				type="daterange"
				start-placeholder="开始${field.fieldComment!}"
				end-placeholder="结束${field.fieldComment!}"
				value-format="YYYY-MM-DD" @change="onChange${field.attrName?cap_first}">
			  </el-date-picker>
			<#elseif field.queryFormType == 'datetime'>
			  <el-date-picker
				v-model="${field.attrName}Ref"
				type="datetimerange"
				start-placeholder="开始${field.fieldComment!}"
				end-placeholder="结束${field.fieldComment!}"
				value-format="YYYY-MM-DD HH:mm:ss" @change="onChange${field.attrName?cap_first}">
			  </el-date-picker>
			<#else>
			  <el-input v-model="state.queryForm.${field.attrName}" placeholder="${field.fieldComment!}"></el-input>
			</#if>
			</el-form-item>
		  </#list>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'${moduleName}:${functionName}:save'" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'${moduleName}:${functionName}:delete'" type="danger" @click="deleteBatchHandle()">删除</el-button>
			</el-form-item>
		</el-form>

		<!-- 数据表格 -->
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
	    <#list gridList as field>
		  <#if field.formDict??>
			<fast-table-column prop="${field.attrName}" label="${field.fieldComment!}" dict-type="${field.formDict}"></fast-table-column>
		  <#else>
			<el-table-column prop="${field.attrName}" label="${field.fieldComment!}" header-align="center" align="center"></el-table-column>
		  </#if>
        </#list>
			<el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
				<template #default="scope">
					<el-button v-auth="'${moduleName}:${functionName}:update'" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button v-auth="'${moduleName}:${functionName}:delete'" type="primary" link @click="deleteBatchHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!-- 分页组件 -->
		<el-pagination
			:current-page="state.pageNo"
			:page-sizes="state.pageSizes"
			:page-size="state.pageSize"
			:total="state.total"
			layout="total, sizes, prev, pager, next, jumper"
			@size-change="sizeChangeHandle"
			@current-change="currentChangeHandle">
		</el-pagination>

		<!-- 新增/修改弹窗 -->
		<add-or-update ref="addOrUpdateRef" @refreshDataList="getDataList"></add-or-update>
	</el-card>
</template>

<script setup lang="ts" name="${ModuleName}${FunctionName}Index">
    // 导入必要的库和组件
    import {useCrud} from '@/hooks' // 封装的CRUD钩子
    import {reactive, ref} from 'vue'
    import {IHooksOptions} from '@/hooks/interface' // 类型定义

    /**
 * 状态管理
 * 使用封装的useCrud钩子管理列表页的CRUD操作
 */
const state: IHooksOptions = reactive({
	dataListUrl: '/${moduleName}/${functionName}/page',  // 数据列表接口
	deleteUrl: '/${moduleName}/${functionName}',         // 删除接口
	queryForm: {  // 查询表单数据
		<#list queryList as field>
		<#if field.queryType != 'between'>
		${field.attrName}: ''<#sep>, </#sep>
		<#else>
		begin${field.attrName?cap_first}: '',
		end${field.attrName?cap_first}: ''<#sep>, </#sep>
		</#if>
		</#list>
	}
})

// 引用组件/响应式
const addOrUpdateRef = ref()
<#list queryList as field>
<#if field.queryType == 'between'>
const ${field.attrName}Ref = ref<string[]>([])
</#if>
</#list>

/**
 * 打开新增/修改弹窗
 * @param {number} [id] - 需要修改的条目ID，不传表示新增
 */
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

<#list queryList as field>
<#if field.queryType == 'between'>
/**
 * ${field.fieldComment!}范围选择变更处理
 * @param {any} value - 日期选择器返回的值数组
 */
const onChange${field.attrName?cap_first} = (value: any) => {
	if (value && value.length === 2) {
		state.queryForm.begin${field.attrName?cap_first} = value[0];
		state.queryForm.end${field.attrName?cap_first} = value[1];
	}else {
		state.queryForm.begin${field.attrName?cap_first} = '';
		state.queryForm.end${field.attrName?cap_first} = '';
	}
}

</#if>
</#list>
// 从useCrud钩子中解构出CRUD操作方法
const {
	getDataList,              // 获取数据列表
	selectionChangeHandle,    // 多选变化处理
	sizeChangeHandle,         // 分页大小变化处理
	currentChangeHandle,      // 当前页码变化处理
	deleteBatchHandle         // 批量删除处理
} = useCrud(state)
</script>
