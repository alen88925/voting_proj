<template>
  <div>
    <h1>後台管理</h1>

    <!-- 新增表單 -->
    <div>
      <h2>新增投票項目</h2>
      <input v-model="newItemName" placeholder="請輸入項目名稱" />
      <button @click="handleCreate">新增</button>
    </div>

    <!-- 投票項目列表 -->
    <div>
      <h2>投票項目列表</h2>
      <table>
        <thead>
          <tr>
            <th>編號</th>
            <th>名稱</th>
            <th>票數</th>
            <th>狀態</th>
            <th>建立時間</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in items" :key="item.itemId">
            <td>{{ item.itemId }}</td>
            <td>
              <span v-if="editingId !== item.itemId">{{ item.itemName }}</span>
              <input v-else v-model="editingName" />
            </td>
            <td>{{ item.voteCount }}</td>
            <td>{{ item.enable ? '啟用' : '停用' }}</td>
            <td>{{ item.itemCreatedTime }}</td>
            <td>
              <!-- 編輯模式 -->
              <span v-if="editingId === item.itemId">
                <button @click="handleUpdate(item.itemId)">確認</button>
                <button @click="cancelEdit">取消</button>
              </span>
              <!-- 一般模式 -->
              <span v-else>
                <button @click="startEdit(item)">編輯</button>
                <button @click="handleToggle(item.itemId)">
                  {{ item.enable ? '停用' : '啟用' }}
                </button>
                <button @click="handleDelete(item.itemId)">刪除</button>
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { adminApi } from '../api/index.js'

// 資料
const items = ref([])
const newItemName = ref('')
const editingId = ref(null)
const editingName = ref('')

// 載入所有項目
const loadItems = async () => {
  const response = await adminApi.getAllItems()
  items.value = response.data.data
}

// 新增項目
const handleCreate = async () => {
  if (!newItemName.value.trim()) {
    alert('請輸入項目名稱')
    return
  }
  await adminApi.createItem({ itemName: newItemName.value })
  newItemName.value = ''
  loadItems()
}

// 開始編輯
const startEdit = (item) => {
  editingId.value = item.itemId
  editingName.value = item.itemName
}

// 取消編輯
const cancelEdit = () => {
  editingId.value = null
  editingName.value = ''
}

// 更新項目
const handleUpdate = async (id) => {
  if (!editingName.value.trim()) {
    alert('請輸入項目名稱')
    return
  }
  await adminApi.updateItem(id, { itemName: editingName.value })
  cancelEdit()
  loadItems()
}

// 切換狀態
const handleToggle = async (id) => {
  await adminApi.toggleItem(id)
  loadItems()
}

// 刪除項目
const handleDelete = async (id) => {
  if (!confirm('確定要刪除此項目嗎？')) return
  await adminApi.deleteItem(id)
  loadItems()
}

// 頁面載入時執行
onMounted(() => {
  loadItems()
})
</script>