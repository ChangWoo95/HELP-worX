export default {
    state: {
        srList: [],
    },
    mutations: {
        GETLIST(state, resSrList) {
            state.srList = resSrList;
        },
    },
    actions: {},
    getters: {
        getSrList: function(state) {
            return state.srList;
        },
    },
};
