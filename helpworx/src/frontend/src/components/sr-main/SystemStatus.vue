<template>
    <v-row>
        <v-row class="ml-8 mt-6" dense style="width: 100%">
            <v-col cols="2">
                <v-select item-color="purple" dense solo :items="systems" item-text="sysNm" item-value="sysNm" v-model="selectedSystem" label="시스템 목록"></v-select>
            </v-col>
            <v-spacer />
        </v-row>
        <v-row class="ml-4" dense>
            <v-col cols="5">
                <v-card style="border: solid black 1px; margin: 10px">
                    <v-card-text style="font-weight: bold; text-align: left" class="blue lighten-5">
                        전체건수
                    </v-card-text>

                    <v-row v-if="!isLoading" justify="center" style="padding:5px">
                        <Spinner></Spinner>
                    </v-row>
                    <v-row v-else justify="center" style="padding:5px">
                        <v-col>
                            <v-card>
                                <v-card-text class="myBlock blue lighten-4">총(건)</v-card-text>
                                <v-card-text class="myBlock">{{ totalCounts }}</v-card-text>
                            </v-card>
                        </v-col>
                        <v-col>
                            <v-card style="width:130px">
                                <v-card-text class="myBlock blue lighten-4">데이터수정</v-card-text>
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
            <v-col cols="5">
                <v-card style="border: solid black 1px; margin: 10px">
                    <v-card-text class="myBlock blue lighten-5">
                        월별 건수
                    </v-card-text>
                    <v-row v-if="!isLoading" justify="center" style="padding:5px">
                        <Spinner></Spinner>
                    </v-row>
                    <v-row v-else justify="center" style="padding:5px">
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
        </v-row>
    </v-row>
</template>

<script>
import { getCtmmnySys, getSrDatas } from "../../api/index";
import Spinner from "../util/Spinner";
export default {
    components: {
        Spinner: Spinner,
    },
    data: () => ({
        isLoading: false,
        totalCounts: 0,
        dataModificationCounts: 0,
        improvingCounts: 0,
        errorCounts: 0,
        questionCounts: 0,
        currentMonthCounts: 0,
        prevMonthCounts: 0,

        selectedSystem: "",
        systems: [],
    }),
    created: function() {
        this.getSysAndJobGbData();
        this.getSrMainDatas(this.selectedSystem);
    },

    methods: {
        async getSysAndJobGbData() {
            const res = await getCtmmnySys.getRes();
            this.systems = res.data;
            this.systems.splice(0, 0, { sysNm: "전체 보기" });
        },
        async getSrMainDatas(sysOption) {
            this.isLoading = false;
            if (sysOption === "전체 보기") sysOption = "";
            const res = await getSrDatas.getRes(sysOption);
            this.getSrCounts(res.data);
        },
        getSrCounts(res) {
            this.totalCounts = res.totalCounts;
            this.dataModificationCounts = res.dataModificationCounts;
            this.improvingCounts = res.improvingCounts;
            this.errorCounts = res.errorCounts;
            this.questionCounts = res.questionCounts;
            this.currentMonthCounts = res.currentMonthCounts;
            this.prevMonthCounts = res.prevMonthCounts;
            this.isLoading = true;
        },
    },
    watch: {
        selectedSystem: function() {
            this.getSrMainDatas(this.selectedSystem);
        },
    },
};
</script>

<style scoped>
.myBlock {
    font-weight: bold;
    text-align: center;
}

select {
    border: 0px;
    border-bottom: solid 1px rgba(129, 129, 129, 0.753);
    width: 100%;
}
optgroup {
    font-size: 20px;
}
</style>
