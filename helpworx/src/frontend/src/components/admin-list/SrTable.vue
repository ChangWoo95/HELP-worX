<template>
    <v-row class="mt-8" dense>
        <v-card class="firstBox " style="height: 100%">
            <v-row class="mb-2 mt-2">
                <v-col cols="1" dense style="font-weight: bold ">고객사 ({{ ctmmnyLists.length }}) 건</v-col>
                <v-spacer />
                <v-col cols="1">
                    <v-btn @click="ctmmnyAdd" class="submitbtn blue lighten-2">추가</v-btn>
                </v-col>
                <v-col cols="1">
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
                                고객사 코드
                            </th>
                            <th class="text-center">
                                고객사명
                            </th>
                            <th class="text-center">
                                사용시스템수
                            </th>
                            <th class="text-center">
                                고객수
                            </th>
                            <th class="text-center ">
                                사용여부
                            </th>
                            <th class="text-center " v-show="isconfirmed">확인</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in ctmmnyLists" :key="item.ctmmnyCd" @click="click_row(item)">
                            <td class="text-center no">{{ index + 1 }}</td>
                            <td class="text-center sys">{{ item.ctmmnyCd }}</td>
                            <td class="text-center">{{ item.ctmmnyNm }}</td>
                            <td class="text-center">{{ item.usingSystemCounts }}</td>
                            <td class="text-center">{{ item.usingClientCounts }}</td>

                            <td class="text-center">{{ item.isUsed }}</td>

                            <td class="text-center red--text text--lighten-1" v-show="isconfirmed" @click="ctmmnyDelete(item, index)">삭제</td>
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
import { updateCtmmnyInfo } from "../../api/index";
export default {
    data() {
        return {
            ctmmnyLists: [],
            isconfirmed: false,
            req: {
                ctmmnyCd: "",
            },
            page: 0,
        };
    },
    methods: {
        async updateCtmmny() {
            await updateCtmmnyInfo.getRes(this.req);
        },
        confirm() {
            this.isconfirmed = !this.isconfirmed;
        },
        ctmmnyAdd() {
            this.$router.push("/admin/ctmmnys/reg");
        },
        ctmmnyDelete(item, index) {
            event.stopPropagation();
            if (confirm(`${index + 1}번 행을 삭제하시겠습니까?`) == true) {
                this.req.ctmmnyCd = item.ctmmnyCd;
                this.updateCtmmny();
                this.$store.commit("UPDATECTMMNY", index);
                this.isconfirmed = !this.isconfirmed;
            } else {
                return;
            }
        },
        click_row: function(item) {
            this.$router.push(`/admin/ctmmnys/detail/${item.ctmmnyCd}`);
        },
    },
    computed: {
        checkCtmmnyList() {
            return this.$store.getters.getCtmmnyList;
        },
    },
    watch: {
        checkCtmmnyList(data) {
            this.ctmmnyLists = data;
        },
        page: function(newVal) {
            this.$emit("pageChange", newVal);
            setTimeout(() => {
                console.log(this.$store.getters.getCtmmnyList);
                this.ctmmnyLists = this.$store.getters.getCtmmnyList;
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
