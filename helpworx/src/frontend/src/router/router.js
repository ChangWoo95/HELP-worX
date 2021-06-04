import VueRouter from "vue-router";
import Vue from "vue";

Vue.use(VueRouter);
const requireAuth = () => (to, from, next) => {
    if (localStorage.getItem("token") == undefined) return next("/login");
    else if (localStorage.getItem("selectedRole") == "") return next("/roles");
    else {
        const role = localStorage.getItem("selectedRole");
        if (role === "ROLE_USER") next("/user");
        else if (role === "ROLE_OPERATION") next("/oper");
        else if (role === "ROLE_ADMIN") next("/admin");
    }
};

const requireRole = () => (to, from, next) => {
    const role = localStorage.getItem("selectedRole");
    if (to.meta.requiresAuth !== role) {
        alert("접근할 수 없는 권한입니다.");
        next("/roles");
    } else next();
};

const router = new VueRouter({
    mode: "history",
    routes: [
        {
            path: "/login",
            name: "login",
            component: () => import("@/views/Login.vue"),
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem("token") != undefined) next("/roles");
                else next();
            },
        },
        {
            path: "/roles",
            name: "roles",
            component: () => import("@/views/Role.vue"),
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem("token") == undefined) next("/login");
                else next();
            },
        },
        {
            path: "/user",
            name: "user",
            component: () => import("@/views/Main.vue"),
            beforeEnter: requireRole(),
            children: [
                {
                    path: "srs/main",
                    alias: [""],
                    redirect: "",
                    name: "userSrMain",
                    component: () => import("@/views/UserSrMain.vue"),
                    meta: { requiresAuth: "ROLE_USER" },
                },
                {
                    path: "srs/list",
                    name: "userSrList",
                    component: () => import("@/views/SrList.vue"),
                    meta: { requiresAuth: "ROLE_USER" },
                },
                {
                    path: "srs/detail/:id",
                    name: "userSrDetail",
                    component: () => import("@/views/SrDetail.vue"),
                    meta: { requiresAuth: "ROLE_USER" },
                },
                {
                    path: "srs/reg",
                    name: "userSrReg",
                    component: () => import("@/views/SrReg.vue"),
                    meta: { requiresAuth: "ROLE_USER" },
                },
            ],
        },
        {
            path: "/oper",
            name: "oper",
            component: () => import("@/views/Main.vue"),
            beforeEnter: requireRole(),
            children: [
                {
                    path: "srs/main",
                    name: "operSrMain",
                    alias: [""],
                    component: () => import("@/views/OperatorSrMain.vue"),
                    meta: { requiresAuth: "ROLE_OPERATION" },
                },
                {
                    path: "srs/list",
                    name: "operSrList",
                    component: () => import("@/views/RcptTreatList"),
                    meta: { requiresAuth: "ROLE_OPERATION" },
                },
                {
                    path: "srs/status",
                    name: "operSrStatus",
                    component: () => import("@/views/OperatorSrStatus.vue"),
                    meta: { requiresAuth: "ROLE_OPERATION" },
                },
                {
                    path: "srs/rank",
                    name: "operSrRank",
                    component: () => import("@/views/OperatorSrRank.vue"),
                    meta: { requiresAuth: "ROLE_OPERATION" },
                },
                {
                    path: "chats/list",
                    name: "operChatList",
                    component: () => import("@/views/ChatList.vue"),
                    meta: { requiresAuth: "ROLE_OPERATION" },
                },
                {
                    path: "rcptTreat/:id",
                    name: "operRcptTreat",
                    component: () => import("@/views/RcptTreat.vue"),
                    meta: { requiresAuth: "ROLE_OPERATION" },
                },
            ],
        },
        {
            path: "/admin",
            name: "admin",
            component: () => import("@/views/Main.vue"),
            beforeEnter: requireRole(),
            children: [
                {
                    path: "ctmmnys/reg",
                    name: "adminCtmmnysReg",
                    component: () => import("@/views/AdminCtmmnysReg.vue"),
                    meta: { requiresAuth: "ROLE_ADMIN" },
                },
                {
                    path: "users/reg",
                    name: "adminUsersReg",
                    component: () => import("@/views/AdminUsersReg.vue"),
                    meta: { requiresAuth: "ROLE_ADMIN" },
                },
                {
                    path: "ctmmnys/detail/:id",
                    name: "adminCtmmnysDetail",
                    component: () => import("@/views/AdminCtmmnysReg.vue"),
                    meta: { requiresAuth: "ROLE_ADMIN" },
                },
                {
                    path: "ctmmnys/list",
                    alias: [""],
                    component: () => import("@/views/AdminCtmmnysList.vue"),
                    meta: { requiresAuth: "ROLE_ADMIN" },
                },
                {
                    path: "users/list",
                    component: () => import("@/views/AdminUsersList.vue"),
                    meta: { requiresAuth: "ROLE_ADMIN" },
                },
                {
                    path: "users/detail/:id",
                    component: () => import("@/views/AdminUsersReg.vue"),
                    meta: { requiresAuth: "ROLE_ADMIN" },
                },
            ],
        },

        {
            path: "/",
            name: "main",
            beforeEnter: requireAuth(),
        },
    ],
});

export default router;
