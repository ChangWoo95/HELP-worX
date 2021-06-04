<template>
    <v-container fill-height fluid>
        <v-layout column wrap class="grey lighten-3">
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>

            <v-flex class="mybox">
                <Modal v-on:closeModal="closeModal" v-bind:propsdata="msg"></Modal>
                <v-row no-gutters justify="center" class="pb-16">
                    <div class="display-4 font-weight-regular">
                        <span class="font-italic light-blue--text text-darken-2">H</span>
                        <span>elpWorX</span>
                    </div>
                </v-row>
            </v-flex>
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>
            <v-flex class="mybox"></v-flex>

            <v-flex class="mybox">
                <v-row no-gutters justify="center">
                    <v-col cols="6">
                        <v-form @submit.prevent="onSubmit(userEmail, userPwd)">
                            <v-text-field v-model="userEmail" :rules="idRules" type="text" label="아이디/사번" solo required></v-text-field>

                            <v-text-field v-model="userPwd" :rules="passwordRules" type="password" label="비밀번호" solo required></v-text-field>

                            <v-btn type="submit" dark block>
                                로그인
                            </v-btn>
                        </v-form>
                    </v-col>
                </v-row>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
import Modal from "../components/login/Modal";
export default {
    data() {
        return {
            userEmail: "",
            idRules: [(v) => !!v || "아이디 혹은 사번을 입력해주세요", (v) => (v && v.length <= 20) || "Name must be less than 20 characters"],
            userPwd: "",
            passwordRules: [(v) => !!v || "비밀번호를 입력해주세요", (v) => (v && v.length <= 20) || "password must be less than 20 characters"],
            msg: "",
        };
    },
    watch: {
        msg: function(newMsg) {
            if (newMsg == "Request failed with status code 401") {
                this.msg = "아이디 또는 비밀번호를 확인하세요";
            } else if (newMsg == "Request failed with status code 400") {
                this.msg = "입력값을 알맞게 입력하세요";
            }
        },
    },
    methods: {
        closeModal: function() {
            this.msg = "";
        },
        onSubmit(userEmail, userPwd) {
            // LOGIN 액션 실행
            const json = JSON.stringify({ userEmail, userPwd });
            this.$store
                .dispatch("LOGIN", json)
                .then(() => this.$router.replace('/roles'))
                .catch(({ message }) => (this.msg = message));
        },
        redirect() {
            const { search } = window.location;
            if (search === "") {
                this.$router.push("/");
            } else {
                const tokens = search.replace(/^\?/, "").split("&");
                const { returnPath } = tokens.reduce((qs, tkn) => {
                    const pair = tkn.split("=");
                    qs[pair[0]] = decodeURIComponent(pair[1]);
                    return qs;
                }, {});
                // 리다이렉트 처리
                this.$router.push(returnPath);
            }
        },
    },
    components: {
        Modal: Modal,
    },
};
</script>

<style>
.mybox {
    display: block;
    padding: 10px;
}
</style>
