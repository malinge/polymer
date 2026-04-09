<template>
	<el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" draggable>
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="100px" @keyup.enter="submitHandle()">
	    <#list formList as field>
			<#if field.formType == 'text'>
				<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
					<el-input v-model="dataForm.${field.attrName}" placeholder="${field.fieldComment!}"></el-input>
				</el-form-item>
			<#elseif field.formType == 'textarea'>
				<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
					<el-input type="textarea" v-model="dataForm.${field.attrName}"></el-input>
				</el-form-item>
			<#elseif field.formType == 'editor'>
				<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
					<el-input type="textarea" v-model="dataForm.${field.attrName}"></el-input>
				</el-form-item>
			<#elseif field.formType == 'select'>
				<#if field.formDict??>
					<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
						<fast-select v-model="dataForm.${field.attrName}" dict-type="${field.formDict}" placeholder="${field.fieldComment!}"></fast-select>
					</el-form-item>
				<#else>
					<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
						<el-select v-model="dataForm.${field.attrName}" placeholder="请选择">
							<el-option label="请选择" value="0"></el-option>
						</el-select>
					</el-form-item>
				</#if>
			<#elseif field.formType == 'radio'>
				<#if field.formDict??>
					<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
						<fast-radio-group v-model="dataForm.${field.attrName}" dict-type="${field.formDict}"></fast-radio-group>
					</el-form-item>
				<#else>
					<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
						<el-radio-group v-model="dataForm.${field.attrName}">
							<el-radio :label="0">启用</el-radio>
							<el-radio :label="1">禁用</el-radio>
						</el-radio-group>
					</el-form-item>
				</#if>
			<#elseif field.formType == 'checkbox'>
				<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
					<el-checkbox-group v-model="dataForm.${field.attrName}">
						<el-checkbox label="启用" name="type"></el-checkbox>
						<el-checkbox label="禁用" name="type"></el-checkbox>
					</el-checkbox-group>
				</el-form-item>
			<#elseif field.formType == 'date'>
				<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
					<el-date-picker type="date" placeholder="${field.fieldComment!}" v-model="dataForm.${field.attrName}"></el-date-picker>
				</el-form-item>
			<#elseif field.formType == 'datetime'>
				<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
					<el-date-picker type="datetime" placeholder="${field.fieldComment!}" v-model="dataForm.${field.attrName}"></el-date-picker>
				</el-form-item>
			<#elseif field.formType == 'inputNumber'>
				<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
					<el-input-number v-model="dataForm.${field.attrName}" :min="0" :max="1000" placeholder="${field.fieldComment!}"  />
				</el-form-item>
			<#elseif field.formType == 'selectUser'>
				<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
					<div class="user-selection">
						<div v-if="selectedUser" class="selected-user">
							<span>{{ selectedUser.username }}</span>
							<el-icon @click="clearUser" class="delete-icon">
								<Close />
							</el-icon>
						</div>
						<el-button text bg :icon="Plus" @click="openUserDialog">
							选择${field.fieldComment}
						</el-button>
					</div>
				</el-form-item>
			<#else>
				<el-form-item label="${field.fieldComment!}" prop="${field.attrName}">
					<el-input v-model="dataForm.${field.attrName}" placeholder="${field.fieldComment!}"></el-input>
				</el-form-item>
			</#if>
	    </#list>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
		<#list formList as field>
		<#if field.formType == 'selectUser'>
			<user-transfer
					v-model="dataForm.${field.attrName}"
					ref="userTransferRef"
					:title="'选择${field.fieldComment}'"
					:multiple="false"
					@confirm="handleUserSelected"/>
		</#if>
		</#list>

	</el-dialog>
</template>

<script setup lang="ts">
    import {reactive, ref} from 'vue'
    import {ElMessage} from 'element-plus/es'

    <#list formList as field>
<#if field.formType == 'selectUser'>
    </#if>
</#list>

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()
<#list formList as field>
<#if field.formType == 'selectUser'>
const userTransferRef = ref()
const selectedUser = ref<{ id: number; username: string } | null>(null)
</#if>
</#list>

const dataForm = reactive({
	<#list fieldList as field>
	${field.attrName}: ''<#sep>,
	</#list>
})

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}

	if (id) {
		get${FunctionName}(id)
	}

	<#list formList as field>
	<#if field.formType == 'selectUser'>
	// 重置${field.fieldComment}信息
	selectedUser.value = null
	</#if>
	</#list>
}

const get${FunctionName} = (id: number) => {
	use${FunctionName}Api(id).then(res => {
		Object.assign(dataForm, res.data)
	})
}

const dataRules = ref({
	<#list formList as field>
	<#if field.formRequired>
	${field.attrName}: [{ required: true, message: '必填项不能为空', trigger: 'blur' }]<#sep>,
	</#if>
	</#list>
})

<#list formList as field>
<#if field.formType == 'selectUser'>
// 打开${field.fieldComment}选择对话框
const openUserDialog = () => {
	userTransferRef.value.open(dataForm.${field.attrName})
}

// 处理${field.fieldComment}选择结果
const handleUserSelected = (user: any) => {
	if (user) {
		selectedUser.value = {
			id: user.id,
			username: user.username
		}
		dataForm.${field.attrName} = user.id
	} else {
		selectedUser.value = null
		dataForm.${field.attrName} = null
	}
}

// 清除已选择的${field.fieldComment}
const clearUser = () => {
	selectedUser.value = null
	dataForm.${field.attrName} = null
}
</#if>
</#list>

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		use${FunctionName}SubmitApi(dataForm).then(() => {
			ElMessage.success({
				message: '操作成功',
				duration: 500,
				onClose: () => {
					visible.value = false
					emit('refreshDataList')
				}
			})
		})
	})
}

defineExpose({
	init
})

</script>
<#list formList as field>
<#if field.formType == 'selectUser'>
<style lang="scss" scoped>
	.user-selection {
		display: flex;
		flex-direction: row;
		gap: 10px;

		.selected-user {
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding: 0 10px;
			background-color: #f5f7fa;
			border-radius: 20px;
			border: 0 solid #dcdfe6;

			.delete-icon {
				cursor: pointer;
				color: #f56c6c;
			}

			.delete-icon:hover {
				color: #e4393c;
			}
		}
	}

	.el-button.is-text:not(.is-disabled).is-has-bg {
		background-color: var(--el-fill-color-light);
		border-radius: 20px;
	}
</style>
</#if>
</#list>