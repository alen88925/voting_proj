import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json'
    }
})

// 後台管理 API
export const adminApi = {
    getAllItems: () => api.get('/admin/items'),
    createItem: (data) => api.post('/admin/items', data),
    updateItem: (id, data) => api.put(`/admin/items/${id}`, data),
    toggleItem: (id) => api.patch(`/admin/items/${id}/toggle`),
    deleteItem: (id) => api.delete(`/admin/items/${id}`)
}

// 使用者投票 API
export const voteApi = {
    getActiveItems: () => api.get('/vote/items'),
    castVotes: (data) => api.post('/vote/cast', data)
}