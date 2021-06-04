<template>
    <v-row style="width: 100% max-height:50%" dense>
        <v-card style="width: 65%">
            <v-simple-table class="v-table">
                <template v-slot:default>
                    <thead class="thead">
                        <tr>
                            <th style="font-size: 20px; color:black;border-bottom:solid 1px;" class="text-left">
                                고객
                            </th>
                            <th style="border-bottom:solid 1px;" class="text-left"></th>
                            <th style="border-bottom:solid 1px;" class="text-left"></th>
                            <th style="border-bottom:solid 1px;" class="text-left"></th>
                        </tr>
                    </thead>
                    <tbody class="v-body">
                        <tr>
                            <td>* 고객사</td>
                            <td colspan="3">
                                <v-select dense outlined :items="options" item-text="ctmmnyNm" item-value="ctmmnyCd" v-model="user.ctmmnyCd" hide-details></v-select>
                            </td>
                        </tr>

                        <!-- <tr>
                            <td>* 고객 ID</td>
                            <td colspan="3">
                                <v-text-field
                                    v-model="user.userId"
                                    maxlength="25"
                                    hide-details
                                    solo
                                    :readonly="isReceived === true"
                                    :disabled="isReceived === true"
                                    :filled="isReceived === true"
                                ></v-text-field>
                            </td>
                        </tr> -->
                        <tr>
                            <td>* 고객 Email</td>
                            <td colspan="3">
                                <v-text-field hide-details v-model="user.userEmail" maxlength="25" solo></v-text-field>
                            </td>
                        </tr>
                        <tr>
                            <td>* 고객 이름</td>
                            <td colspan="3">
                                <v-text-field hide-details v-model="user.userNm" maxlength="25" solo></v-text-field>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                * Password <br />
                                <p style="font-size:3px; color:green;">기본 비밀번호는 admin 입니다.</p>
                            </td>
                            <td colspan="3">
                                <v-text-field hide-details type="password" v-model="user.userPwd" maxlength="25" solo></v-text-field>
                            </td>
                        </tr>
                        <tr>
                            <td>전화번호</td>
                            <td colspan="3">
                                <v-text-field hide-details v-model="user.userTeleno" maxlength="25" solo></v-text-field>
                            </td>
                        </tr>
                        <tr>
                            <td>*사용자권한</td>
                            <v-row v-for="item in roles" :key="item.id">
                                <v-checkbox v-model="user.roles" :label="item.roleNm" :value="item.id" color="grey lighten-1" style="font-weight: bold"></v-checkbox>
                            </v-row>
                        </tr>
                        <tr>
                            <td>사용 여부</td>
                            <v-checkbox hide-details disabled input-value="true" style="font-weight: bold"></v-checkbox>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="2"></td>
                            <td colspan="2">
                                <v-btn @click="saveUser()" class="mr-2">저장</v-btn>
                                <v-btn @click="cancel()">닫기</v-btn>
                            </td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </v-card>
    </v-row>
</template>

<script>
import { getCtmmnyCdAndNm, getUsersInfo, getUserRoles, saveUser } from "../api/index";
import bcrypt from "bcryptjs";

export default {
    components: {},
    data: function() {
        return {
            user: {
                userId: "",
                userEmail: "",
                userNm: "",
                userPwd: "",
                userTeleno: "",
                ctmmnyCd: "",
                isUsed: true,
                roles: [],
            },
            isReceived: false,
            options: [],
            roles: [],
        };
    },

    watch: {},

    methods: {
        cancel() {
            this.$router.push("/admin/users/list");
        },
        async getRoleInfos() {
            const data = await getUserRoles.getRes();

            this.roles = data.data;
        },

        getCtmmnyInfos(res) {
            this.options = res;
        },
        async fetchData() {
            const userId = this.$route.params.id;
            if (userId === undefined) return;
            const res = await getUsersInfo.getRes(userId);
            this.user = res.data;
            this.user.userPwd = "admin";
            this.isReceived = true;
        },
        async getCtmmnyCdAndNm() {
            const res = await getCtmmnyCdAndNm.getRes();
            this.getCtmmnyInfos(res.data);
        },
        async saveUser() {
            if (this.checkFillAllData(this.user)) {
                var hashedPwd = bcrypt.hashSync(this.user.userPwd);
                this.user.userPwd = hashedPwd;
                const requestBody = this.user;

                await saveUser.getRes(requestBody);
                this.$router.replace("/admin/users/list");
            }
        },
        checkFillAllData: function(user) {
            if (user.ctmmnyCd === null) {
                alert("고객사를 선택하세요");
                return false;
            } else if (user.userId === null) {
                alert("고객ID를 적으세요");
                return false;
            } else if (user.userNm === null) {
                alert("고객이름을 적어주세요");
            } else if (user.userPwd === null) {
                alert("비밀번호를 적어주세요");
            } else {
                return true;
            }
        },
    },
    created() {
        this.getCtmmnyCdAndNm();
        this.getRoleInfos();
        this.fetchData();
    },
};
</script>

<style scoped>
.v-data-table > .v-data-table__wrapper > table > tbody > tr {
    border: solid 0px;
    font-weight: bold;
}
.v-data-table > .v-data-table__wrapper > table > tbody > tr > td {
    padding-top: 10px;
    padding-bottom: 10px;
    border: solid 0px;
}
th {
    border: solid 0px;
    background-color: rgba(177, 206, 245, 0.815);
}
tr:hover {
    background-color: transparent !important;
}
.v-table {
    border-bottom: solid 0.1px rgba(128, 128, 128, 0.082);
    border-top: solid 0.1px rgba(128, 128, 128, 0.219);
}
select {
    width: 100%;
    height: 44px;
    border: solid rgba(53, 53, 53, 0.857) 1px;
    background-color: white;
    border-radius: 5%;
}

.datetime {
    border-bottom: dashed 1px black;
    width: 170px;
    font-weight: bold;
    font-size: 17px;
}
</style>
