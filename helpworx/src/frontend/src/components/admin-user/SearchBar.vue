<template>
    <v-row dense style="width: 100%">
        <v-card class="firstBox">
            <v-card-text>
                <form @submit.prevent="submit">
                    <v-row class="boxRow" dense>
                        <v-col class="topic" cols="1">*고객사</v-col>
                        <v-col cols="3">
                            <v-select item-color="purple" dense outlined :items="options" item-text="ctmmnyNm" item-value="ctmmnyCd" v-model="req.ctmmnyCd"></v-select>
                        </v-col>
                        <v-col class="topic" cols="1">사용여부</v-col>
                        <v-col cols="3">
                            <v-select item-color="purple" :items="usedList" item-text="usedName" item-value="isUsed" dense outlined v-model="req.isUsed"></v-select>
                        </v-col>
                    </v-row>

                    <v-row dense>
                        <v-col class="topic" cols="1">고객ID</v-col>
                        <v-col cols="3">
                            <v-text-field dense outlined v-model="req.userId"> </v-text-field>
                        </v-col>
                        <v-col class="topic" cols="1">고객이름</v-col>
                        <v-col cols="3">
                            <v-text-field dense outlined v-model="req.userNm"> </v-text-field>
                        </v-col>
                        <v-col cols="2"></v-col>
                        <v-col cols="2">
                            <v-btn type="submit" class="submitbtn blue lighten-2">조회</v-btn>
                        </v-col>
                    </v-row>
                </form>
            </v-card-text>
        </v-card>
    </v-row>
</template>

<script>
import { getCtmmnyCdAndNm, getUserInfoList } from "../../api/index";

export default {
    props: ["propsData"],

    data: () => ({
        usedList: [
            {
                usedName: "사용가능",
                isUsed: true,
            },
            {
                usedName: "사용불가",
                isUsed: false,
            },
            {
                usedName: "전체보기",
                isUsed: "",
            },
        ],
        req: {
            userId: "",
            userNm: "",
            ctmmnyCd: "",
            isUsed: "",
            page: 0,
        },
        options: [],
    }),

    methods: {
        getCtmmnyInfos(res) {
            this.options = res;
        },
        submit() {
            this.req.page = 0;
            this.page = 0;
            this.getUserInfoList();
        },
        async getCtmmnyCdAndNm() {
            const res = await getCtmmnyCdAndNm.getRes();
            this.getCtmmnyInfos(res.data);
        },
        async getUserInfoList() {
            const res = await getUserInfoList.getRes(this.req);
            this.$store.commit("GETUSERLIST", res.data.content);
        },
    },
    created: function() {
        this.getUserInfoList();
        this.getCtmmnyCdAndNm();
    },
    watch: {
        propsData: function(newVal) {
            this.req.page = newVal - 1;
            this.getUserInfoList();
        },
    },
};
</script>

<style>
.submitbtn > .v-btn__content {
    color: white;
}
.boxRow {
    height: 60px;
}
.topic {
    font-weight: bold;
    text-align: end;
    color: black;
}
.firstBox {
    width: 80%;
    border: solid darkgrey 1px;
}
.v-text-field > .v-input__control > .v-input__slot {
    background-color: white;
    border: solid black 1px;
}
.v-select > .v-input__control > .v-input__slot {
    background-color: white;
    border: solid black 1px;
}
</style>
