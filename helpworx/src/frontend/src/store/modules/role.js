export default {
    state: {
        roleList: [],
    },
    mutations: {
        GETROLELIST(state, resRoleList) {
            state.roleList = resRoleList;
        },
    },
    actions: {},
    getters: {
        getRoleList: function(state) {
            return state.roleList;
        },
    },
};
