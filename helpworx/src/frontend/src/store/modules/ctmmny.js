export default {
    state: {
        ctmmnyList: [],
    },
    mutations: {
        GETCTMMNYLIST(state, resCtmmnyList) {
            state.ctmmnyList = resCtmmnyList;
        },
        UPDATECTMMNY(state, idx) {
            state.ctmmnyList[idx].isUsed = false;
        },
    },
    getters: {
        getCtmmnyList: function(state) {
            return state.ctmmnyList;
        },
    },
};
