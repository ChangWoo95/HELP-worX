<template>
    <v-row dense class="mt-8">
        <v-card class="firstBox " style="height: 100%">
            <v-row class="mb-2 mt-2" dense>
                <v-col cols="1" dense style="font-weight: bold ">고객 ({{ userLists.length }}) 건</v-col>
                <v-spacer />
                <v-col cols="1" dense>
                    <v-btn @click="UserAdd" class="submitbtn blue lighten-2">추가</v-btn>
                </v-col>
                <v-col cols="1" dense>
                    <v-btn @click="confirm" class="submitbtn blue lighten-2">삭제</v-btn>
                </v-col>
            </v-row>

            <v-simple-table dense>
                <template v-slot:default>
                    <thead>
                        <tr>
                            <th class="text-center">
                                No
                            </th>
                            <th class="text-center">
                                고객사
                            </th>
                            <th class="text-center">
                                고객ID
                            </th>
                            <th class="text-center">
                                고객 이름
                            </th>
                            <th class="text-center">
                                사용여부
                            </th>
                            <th class="text-center" v-show="isconfirmed">확인</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in userLists" :key="item.userId" @click="click_row(item)">
                            <td class="text-center no">{{ index + 1 }}</td>
                            <td class="text-center sys">{{ item.ctmmnyNm }}</td>
                            <td class="text-center">{{ item.userId }}</td>
                            <td class="text-center">{{ item.userNm }}</td>
                            <td class="text-center">{{ item.isUsed }}</td>
                            <td class="text-center red--text text--lighten-1" v-show="isconfirmed" @click="userDelete(item, index)">삭제</td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>

            <v-card elevation="0">
                <v-pagination v-model="page" :length="5" prev-icon="mdi-menu-left" next-icon="mdi-menu-right"></v-pagination>
            </v-card>
        </v-card>
    </v-row>
</template>

<script>
import { updateUserInfo } from "@/api/index";
export default {
    data() {
        return {
            userLists: [],
            isconfirmed: false,
            req: {
                userId: "",
            },
            page: 0,
        };
    },
    methods: {
        async updateUser() {
            await updateUserInfo.getRes(this.req);
        },
        confirm() {
            this.isconfirmed = !this.isconfirmed;
        },
        UserAdd() {
            this.$router.push("/admin/users/reg");
        },
        userDelete(item, index) {
            event.stopPropagation();
            if (confirm(`${index + 1}번 행을 삭제하시겠습니끼?`) == true) {
                this.req.userId = item.userId;
                this.updateUser();
                this.$store.commit("UPDATEUSER", index);
                this.isconfirmed = !this.isconfirmed;
            } else {
                return;
            }
        },
        click_row: function(item) {
            this.$router.push(`/admin/users/detail/${item.userId}`);
        },
    },
    computed: {
        checkUserList() {
            return this.$store.getters.getUserList;
        },
    },
    watch: {
        checkUserList(data) {
            this.userLists = data;
        },
        page: function(newVal) {
            this.$emit("pageChange", newVal);
            setTimeout(() => {
                this.userLists = this.$store.getters.getUserList;
            }, 400);
        },
    },
};
</script>

<style>
th,
td {
    border: solid 1px rgb(89, 158, 248);
}
th {
    background-color: rgba(164, 194, 221, 0.637);
}
.firstBox {
    width: 85%;
    border: solid darkgrey 1px;
}
</style>
