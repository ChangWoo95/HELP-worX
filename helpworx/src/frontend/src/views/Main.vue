<template>
    <v-main>
        <Header @toggleSideMenu="toggleSideMenu" @goRoute="goRoute" :propsdata="nowPath"></Header>
        <!-- <v-card class="mx-auto overflow-hidden" width="100%" height="100%"> -->
        <LeftMenu :propsdata="drawer" @goRoute="goRoute" :userData="userData"></LeftMenu>
        <v-container class="mt-3 mainContainer grey lighten-3">
            <router-view :userData="userData"> </router-view>
            <!-- <SockConnectSendSubScribe style="position: absolute; right: 100px; bottom: 300px;" v-if="userData" :userData="this.userData"></SockConnectSendSubScribe> -->
            <SockConnectSendSubScribe v-if="userData" :userData="this.userData"></SockConnectSendSubScribe>
        </v-container>
        <!-- </v-card> -->
    </v-main>
</template>
<script>
import SockConnectSendSubScribe from "../components/chat/SockConnectSendSubScribe";
import Header from "../components/layouts/Header";
import { getUserInfo } from "../api/index";
import LeftMenu from "../components/layouts/LeftMenu";

const checkNowMenu = function(path) {
    if (path.includes("/srs/detail/")) return "SR 상세 조회";
    else if (path == "/srs/reg") return "SR 등록";
    else if (path == "/user/srs/main" || path == "/") return "SR 메인 ";
    else if (path == "/user/srs/list" || path == "/") return "SR 목록 ";
    else if (path == "/user" || path == "/") return "SR 메인 ";
    else if (path == "/oper") return "SR 메인";
    else if (path == "/oper/chats/list" || path == "/") return "실시간 문의";
    else if (path == "/oper/srs/list") return "SR 목록";
    else if (path == "/oper/srs/main") return "SR 메인";
    else if (path == "/oper/srs/status") return "SR 현황";
    else if (path == "/oper/srs/rank") return "SR 랭크";
    else if (path == "/admin" || path == "/") return "고객사 목록";
    else if (path == "/admin/ctmmnys/list" || path == "/") return "고객사 목록 ";
    else if (path.includes("/admin/ctmmnys/detail/") || path == "/") return "고객사 상세 ";
    else if (path == "/admin/ctmmnys/reg" || path == "/") return "고객사 등록 ";
    else if (path == "/admin/users/list" || path == "/") return "고객 목록 ";
};

export default {
    data() {
        return {
            drawer: false,
            nowPath: checkNowMenu(this.$router.currentRoute.path),
            userData: "",
            myrole: "",
        };
    },

    components: {
        Header: Header,
        LeftMenu: LeftMenu,
        SockConnectSendSubScribe: SockConnectSendSubScribe,
    },

    methods: {
        toggleSideMenu: function() {
            this.drawer = !this.drawer;
        },
        goRoute: function(url) {
            this.drawer = false;
            if (this.$route.path !== url) this.$router.push(url);
            this.nowPath = checkNowMenu(this.$router.currentRoute.path);
        },
        async getUserInfo() {
            const res = await getUserInfo.getRes();
            this.userData = res.data;
        },
    },
    created() {
        this.getUserInfo();
    },
    watch: {
        $route() {
            this.nowPath = checkNowMenu(this.$router.currentRoute.path);
        },
    },
};
</script>

<style scoped>
.mainContainer {
    width: 2000px;
    height: 700px;
    max-width: 2000px;
}
</style>
