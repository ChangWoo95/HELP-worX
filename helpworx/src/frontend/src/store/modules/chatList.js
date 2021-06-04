export default {
    state: {
        chttList:[],
    },
    mutations: {
        GETCHTTLIST(state, resChttList) {
            state.chttList = resChttList;
        },

    },
    getters: {
        getChttList:function(state){
            return state.chttList;
        }
    },
};
