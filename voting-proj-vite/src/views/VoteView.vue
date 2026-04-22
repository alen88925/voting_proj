<template>
  <div>
    <!-- 輸入名稱彈窗 -->
    <div v-if="showNameInput">
      <h2>請輸入您的名稱</h2>
      <input v-model="voterName" placeholder="請輸入名稱" />
      <button @click="handleNameSubmit">確認</button>
    </div>

    <!-- 投票頁面 -->
    <div v-else>
      <h1>投票系統</h1>
      <p>歡迎，{{ voterName }}！</p>

      <!-- 已投票提示 -->
      <div v-if="hasVoted">
        <p>您已完成投票，感謝您的參與！</p>
      </div>

      <!-- 投票表單 -->
      <div v-else>
        <h2>請選擇投票項目（可多選）</h2>
        <div v-for="item in items" :key="item.itemId">
          <label>
            <input
              type="checkbox"
              :value="item.itemId"
              v-model="selectedItems"
            />
            {{ item.itemName }} （目前票數：{{ item.voteCount }}）
          </label>
        </div>
        <button @click="handleVote">投票</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { voteApi } from '../api/index.js'

// 資料
const items = ref([])
const selectedItems = ref([])
const voterName = ref('')
const voterId = ref('')
const showNameInput = ref(false)
const hasVoted = ref(false)

// 產生 UUID
const generateUUID = () => {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
    const r = Math.random() * 16 | 0
    const v = c === 'x' ? r : (r & 0x3 | 0x8)
    return v.toString(16)
  })
}

// 載入投票項目
const loadItems = async () => {
  const response = await voteApi.getActiveItems()
  items.value = response.data.data
}

// 處理名稱輸入
const handleNameSubmit = () => {
  if (!voterName.value.trim()) {
    alert('請輸入名稱')
    return
  }
  localStorage.setItem('voter_name', voterName.value)
  showNameInput.value = false
}

// 處理投票
const handleVote = async () => {
  if (selectedItems.value.length === 0) {
    alert('請至少選擇一個項目')
    return
  }

  await voteApi.castVotes({
    voterId: voterId.value,
    voterName: voterName.value,
    itemIds: selectedItems.value
  })

  localStorage.setItem('has_voted', 'true')
  hasVoted.value = true
  loadItems()
}

// 頁面載入時執行
onMounted(() => {
  // 檢查是否已有 voter_id
  let id = localStorage.getItem('voter_id')
  if (!id) {
    id = generateUUID()
    localStorage.setItem('voter_id', id)
  }
  voterId.value = id

  // 檢查是否已有名稱
  const name = localStorage.getItem('voter_name')
  if (!name) {
    showNameInput.value = true
  } else {
    voterName.value = name
  }

  // 檢查是否已投過票
  hasVoted.value = localStorage.getItem('has_voted') === 'true'

  loadItems()
})
</script>