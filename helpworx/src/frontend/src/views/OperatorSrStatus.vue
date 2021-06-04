<template>
    <v-row class="mt-2 ml-4" dense>
        <v-row dense style="width: 100%">
            <v-col cols="1">
                <h4>
                    * 고객사
                </h4>
            </v-col>

            <v-col cols="4" sm="4" md="4" lg="4">
                <select outlined v-model="selectedCtmmnysOptions">
                    <option v-for="ctmmny in ctmmnys" :key="ctmmny.id" :value="ctmmny.id">{{ ctmmny.ctmmnyNm }}</option>
                </select>
            </v-col>

            <v-col style="margin-top:10px;" cols="1">
                <h4>
                    * 시스템
                </h4>
            </v-col>

            <v-col cols="12" sm="4" md="4" lg="4">
                <select outlined v-if="selectedCtmmnysOptions >= 0" v-model="selectedSystem">
                    <option v-for="system in systems" :key="system.sys.id" :value="system.sys.sysNm">{{ system.sys.sysNm }}</option>
                </select>
            </v-col>
        </v-row>
        <v-row style="width: 100%" dense>
            <v-col cols="4">
                <v-card style=" margin: 20px" elevation="10">
                    <v-card-text style="font-weight: bold; text-align: left" class="blue lighten-5">
                        전체건수
                    </v-card-text>
                    <v-row justify="center" style="padding:5px">
                        <v-col>
                            <v-card>
                                <v-card-text class="myBlock blue lighten-4">총(건)</v-card-text>
                                <v-card-text class="myBlock">{{ totalCounts }}</v-card-text>
                            </v-card>
                        </v-col>
                        <v-col>
                            <v-card style="width:130px">
                                <v-card-text class="blue lighten-4 myBlock ">데이터수정</v-card-text>
                                <v-card-text class="myBlock">{{ dataModificationCounts }}</v-card-text>
                            </v-card>
                        </v-col>
                        <v-col>
                            <v-card>
                                <v-card-text class="myBlock blue lighten-4">개선</v-card-text>
                                <v-card-text class="myBlock">{{ improvingCounts }}</v-card-text>
                            </v-card>
                        </v-col>
                        <v-col>
                            <v-card>
                                <v-card-text class="myBlock blue lighten-4">오류</v-card-text>
                                <v-card-text class="myBlock">{{ errorCounts }}</v-card-text>
                            </v-card>
                        </v-col>
                        <v-col>
                            <v-card>
                                <v-card-text class="myBlock blue lighten-4">문의</v-card-text>
                                <v-card-text class="myBlock">{{ questionCounts }}</v-card-text>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-card>
            </v-col>
            <v-col cols="3">
                <v-card style="margin: 20px" elevation="10">
                    <v-card-text style="font-weight: bold; text-align: left" class="blue lighten-5">
                        월별 건수
                    </v-card-text>
                    <v-row justify="center" style="padding:5px">
                        <v-col>
                            <v-card>
                                <v-card-text class="myBlock blue lighten-4">이번 달</v-card-text>
                                <v-card-text class="myBlock">{{ currentMonthCounts }}</v-card-text>
                            </v-card>
                        </v-col>
                        <v-col>
                            <v-card>
                                <v-card-text class="myBlock blue lighten-4">지난 달</v-card-text>
                                <v-card-text class="myBlock">{{ prevMonthCounts }}</v-card-text>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-card>
            </v-col>
            <v-col cols="3">
                <v-card style=" margin: 20px" elevation="10">
                    <v-card-text style="font-weight: bold; text-align: left" class="blue lighten-5">
                        처리시간(hr)
                    </v-card-text>
                    <v-row justify="center" style="padding:5px">
                        <v-col>
                            <v-card>
                                <v-card-text class="myBlock blue lighten-4">평균 접수시간</v-card-text>
                                <v-card-text class="myBlock">{{ averageRcptTime }}</v-card-text>
                            </v-card>
                        </v-col>
                        <v-col>
                            <v-card>
                                <v-card-text class="myBlock blue lighten-4">평균 처리시간</v-card-text>
                                <v-card-text class="myBlock">{{ averageTreatTime }}</v-card-text>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-card>
            </v-col>
        </v-row>
        <v-row dense style="width: 100%">
            <v-col cols="5">
                <LineChart :chartData="monthsData" :name="srName"></LineChart>
            </v-col>
            <v-col cols="5">
                <LineChart :chartData="rcptTreatData" :name="treatName"></LineChart>
            </v-col>
        </v-row>
    </v-row>
</template>

<script>
import { getOperSrDatas, getCtmmnyComboBox, getSrStatusList, getTreatStatusList, getTreatCounts } from "../api/index";
import LineChart from "../components/sr-rank/LineChart";

export default {
    components: { LineChart },
    methods: {
        fillSrChart(statusList) {
            this.monthsData = {
                // Data for the y-axis of the chart
                labels: this.monthList,
                datasets: [],
            };
            this.fillSrData(statusList, this.monthsData);
        },
        fillTreatChart(statusList) {
            // this.monthList.length = 0;

            this.rcptTreatData = {
                labels: this.monthList,
                datasets: [],
            };
            this.fillTreatData(statusList, this.rcptTreatData);
        },
        dynamicColors() {
            var r = Math.floor(Math.random() * 255);
            var g = Math.floor(Math.random() * 255);
            var b = Math.floor(Math.random() * 255);
            return "rgb(" + r + "," + g + "," + b + ")";
        },
        fillSrData(statusList, lineData) {
            statusList.forEach((data) => {
                let labelName = data.ctmmnyNm;
                let idx = 0;
                let dataList = [];
                this.monthList.forEach((mon) => {
                    if (idx < data.monthList.length && data.monthList[idx].dateExpression != null && data.monthList[idx].dateExpression.includes(mon)) {
                        dataList.push(data.monthList[idx++].srCountsByMonth);
                    } else dataList.push(0);
                });
                lineData.datasets.push({
                    label: labelName,
                    lineTension: 0,
                    fill: false,
                    // borderColor: "#d9572b",
                    borderColor: this.dynamicColors(),
                    pointBackgroundColor: this.dynamicColors(),
                    barPercentage: 0.5,
                    data: dataList,
                });
            });
        },
        fillTreatData(statusList, lineData) {
            statusList.forEach((data) => {
                let labelName = data.ctmmnyNm;
                let idx = 0;
                let dataList = [];
                this.monthList.forEach((mon) => {
                    if (idx < data.monthList.length && data.monthList[idx].dateExpression != null && data.monthList[idx].dateExpression.includes(mon)) {
                        dataList.push(data.monthList[idx++].treatTimeByMonth);
                    } else dataList.push(0);
                });
                let color = this.dynamicColors();
                lineData.datasets.push({
                    label: labelName,
                    lineTension: 0,
                    fill: false,
                    // borderColor: "#d9572b",
                    borderColor: color,
                    pointBackgroundColor: color,
                    barPercentage: 0.5,
                    data: dataList,
                });
            });
        },
        async getOperSrDatas() {
            const res = await getOperSrDatas.getRes(this.req);
            this.getSrCounts(res.data);
        },
        async getCtmmnyComboBox() {
            const res = await getCtmmnyComboBox.getRes();
            this.ctmmnys = res.data;
        },
        async getSrStatusList() {
            const res = await getSrStatusList.getRes(this.req);
            this.fillSrChart(res.data);
        },
        async getTreatStatusList() {
            const res = await getTreatStatusList.getRes(this.req);
            this.fillTreatChart(res.data);
        },
        async getTreatCounts() {
            const res = await getTreatCounts.getRes(this.req);
            this.getTreatTimes(res.data);
        },
        getTreatTimes(res) {
            this.averageRcptTime = res.averageRcptTime;
            this.averageTreatTime = res.averageTreatTime;
        },
        getSrCounts(res) {
            this.totalCounts = res.totalCounts;
            this.dataModificationCounts = res.dataModificationCounts;
            this.improvingCounts = res.improvingCounts;
            this.errorCounts = res.errorCounts;
            this.questionCounts = res.questionCounts;
            this.currentMonthCounts = res.currentMonthCounts;
            this.prevMonthCounts = res.prevMonthCounts;
        },
    },
    data() {
        return {
            monthList: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
            monthsData: {},
            rcptTreatData: {},
            srName: "월별 SR건수",
            treatName: "월별 처리시간",
            // 전체 건수
            totalCounts: 0,
            dataModificationCounts: 0,
            improvingCounts: 0,
            errorCounts: 0,
            questionCounts: 0,

            // 월별 건수
            currentMonthCounts: 0,
            prevMonthCounts: 0,

            averageRcptTime: 0,
            averageTreatTime: 0,

            selectedCtmmnysOptions: -1,
            ctmmnys: "",
            systems: "",
            selectedCtmmny: "",
            selectedSystem: "",
            req: {
                ctmmnyCd: "",
                name: "",
            },
        };
    },
    created() {
        this.getSrStatusList();
        this.getTreatStatusList();
        this.getOperSrDatas();
        this.getCtmmnyComboBox();
        this.getTreatCounts();
    },
    watch: {
        selectedSystem: function() {
            this.req.name = this.selectedSystem;
            this.getOperSrDatas();
            this.getTreatCounts();
            this.getSrStatusList();
            this.getTreatStatusList();
        },
        selectedCtmmnysOptions: function(newVal) {
            this.req.ctmmnyCd = this.ctmmnys[newVal].id;
            this.systems = this.ctmmnys[newVal].ctmmnySysList;
            this.getOperSrDatas();
            this.getTreatCounts();
            this.getSrStatusList();
            this.getTreatStatusList();
        },
    },
};
</script>

<style>
.mybox {
    border: solid 0px;
    background-color: rgba(216, 216, 216, 0.233);
    text-align: center;
    font-weight: bold;
    margin-left: -20px;
    margin-right: -20px;
}
.myBlock {
    font-weight: bold;
    text-align: center;
}
.selectSystem {
    width: 300px;
}
.secondBox {
    border: solid 5px;
    margin: 5px;
    padding: 3px;
    padding-bottom: 11px;
}

select {
    border: solid 3px hsla(240, 4%, 42%, 0.753);
    width: 100%;
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 20px;
    margin-bottom: -30px;
}
optgroup {
    font-size: 20px;
}
</style>
