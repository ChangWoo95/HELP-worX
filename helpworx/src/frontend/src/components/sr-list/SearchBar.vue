<template>
    <v-row class="mb-2">
        <v-card class="firstBox">
            <v-card-text>
                <form @submit.prevent="submit">
                    <v-row dense style="height:30%">
                        <v-col cols="3">
                            <v-select item-color="purple" dense solo :items="systems" item-text="sysNm" item-value="id" v-model="selectedSystemOption" label="*시스템"></v-select>
                        </v-col>
                        <v-col cols="1"></v-col>
                        <v-col cols="3">
                            <v-select
                                v-if="selectedSystemOption >= 0"
                                :items="systems[selectedSystemOption].jobs"
                                item-text="jobGb"
                                item-value="jobGb"
                                item-color="purple"
                                dense
                                solo
                                v-model="data.jobGb"
                                label="업무구분"
                            ></v-select>
                            <v-select v-else item-color="purple" dense solo label="업무구분"> </v-select>
                        </v-col>
                        <v-col cols="1"></v-col>
                        <v-col cols="3">
                            <v-select dense :items="ranges" v-model="data.id" outlined label="범위"></v-select>
                        </v-col>
                    </v-row>
                    <v-row dense style="height:30%">
                        <v-col cols="3">
                            <v-select item-color="purple" dense :items="requests" v-model="data.reqGb" solo label="요청구분"></v-select>
                        </v-col>
                        <v-col cols="1"></v-col>
                        <v-col cols="3">
                            <v-select item-color="purple" dense :items="srStatus" v-model="data.status" solo label="SR상태"></v-select>
                        </v-col>
                    </v-row>
                    <v-row dense style="height:20%">
                        <v-col cols="3">
                            <v-text-field dense solo v-model="data.sbjct" label="제목"> </v-text-field>
                        </v-col>
                        <v-col cols="1"></v-col>

                        <v-col cols="3">
                            <v-text-field dense solo v-model="data.dscr" label="내용"> </v-text-field>
                        </v-col>
                        <v-col cols="4"></v-col>
                        <v-col cols="1">
                            <v-btn type="submit" class="submitbtn blue lighten-2">조회</v-btn>
                        </v-col>
                    </v-row>
                </form>
            </v-card-text>
        </v-card>
    </v-row>
</template>

<script>
import { getCtmmnySys, findSrListWithCondition } from "../../api/index";

export default {
    props: ["propsData"],
    data: () => ({
        systems: [],
        jobs: [],
        props: ["propsData"],
        ranges: ["전체", "나의 SR"],
        requests: ["개선", "오류", "문의", "데이터 수정"],
        srStatus: ["접수 대기", "진행중", "해결"],
        data: {
            page: 0,
            sysNm: "",
            jobGb: "",
            id: "",
            reqGb: "",
            status: "",
            sbjct: "",
            dscr: "",
            ctmmny: "",
        },
        page: 0,
        selectedSystemOption: -1,
    }),

    methods: {
        submit() {
            this.data.page = 0;
            this.page = 0;
            this.findSrListWithCondition();
        },
        async getCtmmnySys() {
            const res = await getCtmmnySys.getRes();
            this.systems = res.data;
        },
        async findSrListWithCondition() {
            const res = await findSrListWithCondition.getRes(this.data);
            this.$store.commit("GETLIST", res.data.content);
        },
    },
    created: function() {
        this.getCtmmnySys();
        this.findSrListWithCondition();
    },
    watch: {
        selectedSystemOption: function(newVal) {
            this.data.sysNm = this.systems[newVal].sysNm;
        },
        propsData: function(newVal) {
            this.data.page = newVal - 1;
            this.findSrListWithCondition();
        },
    },
};
</script>

<style>
.submitbtn > .v-btn__content {
    color: white;
}
.topic {
    font-weight: bold;
    text-align: end;
    color: black;
}
.firstBox {
    width: 80%;
    height: 160px;
    /* border: solid darkgrey 1px; */
}
.v-text-field > .v-input__control > .v-input__slot {
    background-color: white;
    border: solid black 1px;
}
.v-select > .v-input__control > .v-input__slot {
    background-color: white;
    border: solid black 1px;
}
.v-select__selection {
    width: 100%;
    justify-content: center;
}
</style>
