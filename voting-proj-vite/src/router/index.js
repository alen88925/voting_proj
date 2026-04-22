import { createRouter, createWebHistory } from 'vue-router'
import AdminView from '../views/AdminView.vue'
import VoteView from '../views/VoteView.vue'

const routes = [
    {
        path: '/admin',
        component: AdminView
    },
    {
        path: '/vote',
        component: VoteView
    },
    {
        path: '/',
        redirect: '/vote'
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router