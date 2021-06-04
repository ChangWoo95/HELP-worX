<template>
    <v-row dense style="width: 100%">
        <v-row dense style="width: 100%">
            <v-col cols="1">
                <h3>
                    * 시스템
                </h3>
            </v-col>
            <v-col cols="6" sm="4" md="4" lg="4">
                <select style="height:45px" outlined v-model="sysOption">
                    <optgroup>
                        <option value="" selected> 전체 현황 보기 </option>
                        <option v-for="sysData in SysAndJobGbData" :key="sysData.id" :value="sysData.name">{{ sysData.name }}</option>
                    </optgroup>
                </select>
            </v-col>
        </v-row>
        <!-- <v-row dense style="width: 100%"></v-row> -->
        <v-row dense style="width: 100%">
            <BarChart :chartData="datacollection" style="width: 80%"></BarChart>
        </v-row>
    </v-row>
</template>

<script>
import BarChart from "../components/sr-rank/BarChart";
import { getCtmmnyRankDatas, getCtmmnyUsingSystems } from "../api/index";

export default {
    data: () => ({
        SysAndJobGbData: [],
        sysOption: "",
        datacollection: {},
        ctmmnyList: [],
        SrCountList: [],
    }),
    name: "OperatorSrRank.vue",
    components: {
        BarChart,
    },
    created: function() {
        this.getCtmmnyUsingSystems();
        this.getCtmmnyRankDatas(this.sysOption);
    },

    methods: {
        fillChart(rankList) {
            this.ctmmnyList.length = 0;
            this.SrCountList.length = 0;
            this.fillData(rankList);

            this.datacollection = {
                // Data for the y-axis of the chart
                labels: this.ctmmnyList,
                datasets: [
                    {
                        label: "고객사",
                        backgroundColor: "#4974da",
                        barPercentage: 0.5,
                        // Data for the x-axis of the chart
                        data: this.SrCountList,
                    },
                ],
            };
        },
        fillData(rankList) {
            rankList.forEach((el) => {
                this.ctmmnyList.push(el.sysNm);
                this.SrCountList.push(el.srCounts);
            });
        },
        async getCtmmnyUsingSystems() {
            const res = await getCtmmnyUsingSystems.getRes();
            this.SysAndJobGbData = res.data;
        },
        async getCtmmnyRankDatas() {
            const res = await getCtmmnyRankDatas.getRes(this.sysOption);
            this.fillChart(res.data);
        },
    },
    watch: {
        sysOption: function() {
            this.getCtmmnyRankDatas(this.sysOption);
        },
    },
};
</script>

<style>
select {
    border: solid 3px rgba(61, 54, 168, 0.753);
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
