<template>
    <v-navigation-drawer v-model="drawer" absolute temporary>
        <v-list-item>
            <v-list-item-content>
                <v-list-item-title>
                    <h3>[{{ this.userData.userNm }}] 님 <br /></h3>
                    어서오세요
                </v-list-item-title>
            </v-list-item-content>
        </v-list-item>
        <v-divider></v-divider>
        <v-list dense>
            <v-list-item v-for="item in items" :key="item.title" link @click="goRoute(item)">
                <v-list-item-icon>
                    <v-icon>{{ item.icon }}</v-icon>
                    <br />
                    <br />
                </v-list-item-icon>

                <v-list-item-content>
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
export default {
    props: ["propsdata", "userData"],
    data() {
        return {
            drawer: false,
            role: this.$store.state.login.selectedRole,
            items: [],
            userItems: [
                {
                    title: "SR 메인 ",
                    icon: "mdi-wrench",
                    url: "/user/srs/main",
                },
                {
                    title: "SR 목록 ",
                    icon: "mdi-wrench",
                    url: "/user/srs/list",
                },
                {
                    title: "SR 등록하기 ",
                    icon: "mdi-wrench",
                    url: "/user/srs/reg",
                },
            ],
            adminItems: [
                {
                    title: "고객사 등록 ",
                    icon: "mdi-wrench",
                    url: "/admin/ctmmnys/reg",
                },
                {
                    title: "고객사 목록",
                    icon: "mdi-wrench",
                    url: "/admin/ctmmnys/list",
                },
                {
                    title: "고객 목록",
                    icon: "mdi-wrench",
                    url: "/admin/users/list",
                },
                {
                    title: "고객 등록",
                    icon: "mdi-wrench",
                    url: "/admin/users/reg",
                },
            ],
            operItems: [
                {
                    title: "SR 메인 ",
                    icon: "mdi-wrench",
                    url: "/oper/srs/main",
                },
                {
                    title: "SR 목록 ",
                    icon: "mdi-wrench",
                    url: "/oper/srs/list",
                },
                {
                    title: "SR 현황",
                    icon: "mdi-wrench",
                    url: "/oper/srs/status",
                },
                {
                    title: "SR 랭크 ",
                    icon: "mdi-wrench",
                    url: "/oper/srs/rank",
                },
                {
                    title: "실시간 문의 목록",
                    icon: "mdi-wrench",
                    url: "/oper/chats/list",
                },
            ],
        };
    },
    methods: {
        goRoute: function(item) {
            this.$emit("goRoute", item.url);
        },
        getLink() {
            if (this.role === "ROLE_ADMIN") {
                this.items = this.adminItems;
            } else if (this.role === "ROLE_OPERATION") {
                this.items = this.operItems;
            } else if (this.role === "ROLE_USER") {
                this.items = this.userItems;
            }
        },
    },
    watch: {
        propsdata: function(newVal) {
            this.drawer = newVal;
        },
        getRole(data) {
            this.role = data;
            this.getLink();
        },
    },
    created() {
        this.getLink();
    },
    computed: {
        getRole() {
            return this.$store.getters.getRole;
        },
    },
};
</script>

<style></style>
