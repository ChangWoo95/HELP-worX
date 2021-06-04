export default {
    state: {
        loadingStatus: false,
    },
    mutations: {
        STARTSPINNER(state) {
            state.loadingStatus = true;
        },
        ENDSPINNER(state) {
            state.loadingStatus = false;
        },
    },
    actions: {},
    getters: {},
};
