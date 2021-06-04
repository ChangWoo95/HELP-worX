import { getUserInfo, login } from "@/api/index";

export default {
    state: {
        accessToken: null,
        roles: [],
        selectedRole: "",
    },
    mutations: {
        SELECTEDROLE(state, data) {
            state.selectedRole = data;
            localStorage.setItem("selectedRole", data);
        },
        LOGIN(state, res) {
            state.accessToken = res.data.token;
            localStorage.setItem("token", res.data.token);
        },
        GETMYROLE(state, res) {
            state.roles.length = 0;
            state.selectedRole = "";
            state.roles = res.data.roles;
            localStorage.setItem("roles", res.data.roles);
        },
        LOGOUT(state) {
            state.accessToken = null;
            state.roles = [];
            state.selectedRole = "";
            localStorage.clear();
        },
    },
    actions: {
        async LOGIN({ commit }, json) {
            commit("LOGIN", await login.getRes(json));
        },
        async GETMYROLE({ commit }) {
            commit("GETMYROLE", await getUserInfo.getRes());
        },
        LOGOUT({ commit }) {
            commit("LOGOUT");
        },
    },
    getters: {
        getRoles: function(state) {
            return state.roles;
        },
        getRole: function(state) {
            return state.selectedRole;
        },
    },
};
