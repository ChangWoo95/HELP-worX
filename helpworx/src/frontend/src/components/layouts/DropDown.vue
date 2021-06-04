<template>
    <div class="text-center">
        <v-menu top :close-on-click="closeOnClick">
            <template v-slot:activator="{ on, attrs }">
                <v-btn fab elevation="0" v-bind="attrs" v-on="on" small icon clearable>
                    <v-icon dense small>mdi-dots-vertical</v-icon>
                </v-btn>
            </template>
            <v-list>
                <h4>[ 권한 변경 ]</h4>
                <v-radio-group hide-details v-model="selectedAuth" row>
                    <v-radio v-for="auth in authList" :key="auth.role" :label="auth.name" :value="auth.role"></v-radio>
                </v-radio-group>
                <v-divider></v-divider>
                <v-list-item @click="click(item.title)" v-for="(item, index) in items" :key="index">
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item>
            </v-list>
        </v-menu>
        <my-page @closeDialog="closeDialog" :dialog="this.dialog" :userId="this.userData"></my-page>

    </div>
</template>
<script>
import store from "../../store/store";
import MyPage from "./MyPage"
export default {
    props:['userData'],
    components:{
        MyPage},
    data: () => ({
        selectedAuth: store.state.login.selectedRole,
        selectedRoleName: "",
        authList: [],
        dialog: false,
        items: [{ title: "My Page" }, { title: "로그아웃" }],
        closeOnClick: true,
    }),
    methods: {
        click: function(itemTitle) {
            if (itemTitle == "로그아웃") {
                store.dispatch("LOGOUT").then(() => this.$router.push("/"));
            }
            else if ( itemTitle == "My Page"){
                this.dialog = true;
            }
        },
        move() {
            const role = this.selectedAuth;
            if (role === "ROLE_ADMIN") {
                this.$router.push("/admin");
            } else if (role === "ROLE_OPERATION") {
                this.$router.push("/oper");
            } else if (role === "ROLE_USER") {
                this.$router.push("/user");
            }
        },
        makeRole() {
            const role = store.state.login.selectedRole;
            if (role === "ROLE_USER") this.selectedRoleName = "유저";
            else if (role === "ROLE_OPERATION") this.selectedRoleName = "운영자";
            else if (role === "ROLE_ADMIN") this.selectedRoleName = "관리자";
        },
        makeList() {
            const list = store.state.login.roles;
            list.forEach((el) => {
                if (el === "ROLE_USER") this.authList.push({ name: "유저", role: el });
                else if (el === "ROLE_OPERATION") this.authList.push({ name: "운영자", role: el });
                else if (el === "ROLE_ADMIN") this.authList.push({ name: "관리자", role: el });
            });
        },
        closeDialog(){
            this.dialog=false
        }
    },
    watch: {
        selectedAuth: function() {
            store.commit("SELECTEDROLE", this.selectedAuth);
            this.makeRole();
            this.move();
        },
    },
    created() {
        this.makeList();
        this.makeRole();
    },
};
</script>
