export default {
    state: {
        userList: [],
    },
    mutations: {
        GETUSERLIST(state, resUserList) {
            state.userList = resUserList;
        },
        UPDATEUSER(state, idx) {
            state.userList[idx].isUsed = false;
        },
    },
    actions: {},
    getters: {
        getUserList: function(state) {
            return state.userList;
        },
    },
};
