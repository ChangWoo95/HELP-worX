import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
import login from "./modules/login";
import srMain from "./modules/srMain";
import ctmmny from "./modules/ctmmny";
import role from "./modules/role";
import user from "./modules/user";
import chatList from "./modules/chatList";
import spinner from "./modules/spinner";
import axios from "axios";

Vue.use(Vuex);

const enhanceAccessToeken = (token) => {
    if (!token) return;
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
};

if (JSON.parse(localStorage.getItem("vuex") != null)) {
    enhanceAccessToeken(JSON.parse(localStorage.getItem("vuex")).login.accessToken);
}

export default new Vuex.Store({
    modules: {
        login,
        srMain,
        ctmmny,
        user,
        spinner,
        chatList,
        role,
    },
    plugins: [createPersistedState({ paths: ["login"] })],
});
