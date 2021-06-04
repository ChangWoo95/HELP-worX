<template>
    <v-row class="mt-2 ml-4" dense style="width: 100%">
        <v-col cols="8">
            <v-card class="firstBox">
                <v-card-text>
                    <form>
                        <v-row class="boxRow">
                            <v-col class="topic" cols="1">고객사</v-col>
                            <v-col>
                                <select style="height:44px;" outlined v-model="selectedCtmmnysOptions">
                                    <option :value="null">전체</option>
                                    <option v-for="ctmmny in ctmmnys" :key="ctmmny.id" :value="ctmmny.id">{{ ctmmny.ctmmnyNm }}</option>
                                </select>
                            </v-col>
                            <v-col class="topic" cols="1">*시스템</v-col>
                            <v-col>
                                <select style="height:44px;" outlined v-if="selectedCtmmnysOptions >= 0" v-model="selectedSystemOption">
                                    <option :value="null">전체</option>
                                    <option v-for="system in systems" :key="system.sys.id" :value="system.sys.id">{{ system.sys.sysNm }}</option>
                                </select>
                                <select style="height:43px;" v-else>
                                    <option>{{ needAnotherSelect }}</option>
                                </select>
                            </v-col>

                            <v-col class="topic" cols="1">업무구분</v-col>
                            <v-col>
                                <select style="height:44px;" outlined v-if="selectedSystemOption >= 0" v-model="data.jobGb">
                                    <option :value="''">전체</option>
                                    <option v-for="job in jobs" :key="job.jobGb" :value="job.jobGb">{{ job.jobGb }}</option>
                                </select>
                                <select style="height:44px;" v-else>
                                    <option>{{ needAnotherSelect }}</option>
                                </select>
                            </v-col>
                        </v-row>
                        <v-row class="boxRow">
                            <v-col class="topic" cols="1">요청구분</v-col>
                            <v-col class="topic" cols="3">
                                <v-select item-color="purple" dense :items="requests" v-model="data.reqGb" outlined></v-select>
                            </v-col>

                            <v-col class="topic" cols="1">SR상태</v-col>
                            <v-col class="topic" cols="3">
                                <v-select item-color="purple" dense :items="srStatus" v-model="data.status" outlined></v-select>
                            </v-col>
                        </v-row>
                        <v-row class="boxRow" style="padding-bottom: 70px">
                            <v-col class="topic" cols="1">제목</v-col>
                            <v-col cols="3">
                                <v-text-field dense outlined v-model="data.sbjct"> </v-text-field>
                            </v-col>

                            <v-col class="topic" cols="1">내용</v-col>
                            <v-col cols="3">
                                <v-text-field dense outlined v-model="data.dscr"> </v-text-field>
                            </v-col>
                            <v-col class="topic" cols></v-col>
                            <v-col cols="1">
                                <v-btn @click="submit" class="submitbtn deep-purple darken-1">조회</v-btn>
                            </v-col>
                        </v-row>
                    </form>
                </v-card-text>
            </v-card>
        </v-col>
        <v-col></v-col>
    </v-row>
</template>

<script>
import { getCtmmnyComboBox, findSrListWithCondition } from "@/api/index";
import { getCtmmnySys } from "../../api";

export default {
    props: ["propsData"],
    data: () => ({
        systems: null,
        ctmmnys: null,
        jobs: null,
        ranges: ["전체", "나의 SR"],
        requests: ["개선", "오류", "문의", "데이터 수정"],
        srStatus: ["접수대기", "진행중", "해결"],
        needAnotherSelect: "이전 선택지 택 必",
        title: "",
        content: "",

        data: {
            sysNm: "",
            jobGb: "",
            id: "",
            reqGb: "",
            status: "",
            sbjct: "",
            dscr: "",
            ctmmny: "",
            page: 0,
        },
        page: 0,
        selectedSystemOption: -1,
        selectedCtmmnysOptions: -1,
        selectedJobOption: -1,
    }),
    methods: {
        submit() {
            this.data.page = 0;
            this.page = 0;
            this.findSrListWithCondition();
        },
        async getCtmmnyComboBox() {
            const res = await getCtmmnyComboBox.getRes();
            this.ctmmnys = res.data;
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
        this.getCtmmnyComboBox();
        this.findSrListWithCondition();
        this.getCtmmnySys();
    },
    watch: {
        selectedSystemOption: function(newVal) {
            if (newVal != null) {
                this.data.sysNm = this.systems[newVal].sys.sysNm;
                this.jobs = this.systems[newVal].sys.jobs;
            } else {
                this.data.sysNm = "";
                this.jobs = null;
            }
        },

        selectedCtmmnysOptions: function(newVal) {
            if (newVal != null) {
                this.data.ctmmny = this.ctmmnys[newVal].id;
                this.systems = this.ctmmnys[newVal].ctmmnySysList;
            } else {
                this.data.ctmmny = "";
                this.systems = null;
            }
        },

        propsData: function(newVal) {
            this.data.page = newVal - 1;
            this.findSrListWithCondition();
        },
    },
};
</script>

<style>
select {
    width: 100%;
    height: 50%;
    border: solid rgba(53, 53, 53, 0.857) 3px;
    background-color: white;
    border-radius: 5%;
}
.submitbtn > .v-btn__content {
    color: white;
}
.boxRow {
    height: 30%;
}
.topic {
    font-weight: bold;
    text-align: end;
    color: black;
}
.firstBox {
    width: 100%;
    height: 240px;
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
