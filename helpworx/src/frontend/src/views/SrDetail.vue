<template>
    <v-row class="mt-2 ml-4" v-if="srData" dense>
        <v-row dense class="mb-2" style="width: 100%; font-weight: bold">상태 : {{ srData.status }}</v-row>
        <v-simple-table class="v-table" dense>
            <template v-slot:default>
                <thead class="thead">
                    <tr>
                        <th class="text-left">
                            SR 상세
                        </th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody class="v-body">
                    <tr>
                        <td>| 시스템 |</td>
                        <td>
                            <v-card-text>{{ srData.sysNm }}</v-card-text>
                        </td>
                        <td>| 업무 구분 |</td>
                        <td>
                            <v-card-text>{{ srData.jobGb }}</v-card-text>
                        </td>
                    </tr>
                    <tr>
                        <td>| 요청구분 |</td>
                        <td>
                            <v-card-text>{{ srData.reqGb }}</v-card-text>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            | 제목 |
                            <v-card-text id="sbjct">{{ srData.sbjct }}</v-card-text>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            | 내용 |
                            <Viewer class="mt-2 " v-bind:content="srData.dscr" />
                        </td>
                    </tr>
                </tbody>
            </template>
        </v-simple-table>

        <v-simple-table class=" mt-4 v-table" dense>
            <template v-slot:default>
                <thead class="thead">
                    <tr>
                        <th class="text-left">
                            SR 접수
                        </th>
                        <th></th>
                    </tr>
                </thead>
                <tbody class="v-body">
                    <tr>
                        <td>| 완료예정일 |</td>
                        <td class="dscr">
                            <v-card-text v-if="srData.rcptTreat">
                                {{ srData.rcptTreat.cmplPrdt }}
                            </v-card-text>
                        </td>
                    </tr>
                    <tr>
                        <td>| 접수 내용 |</td>
                        <td class="dscr" v-if="srData.rcptTreat">
                            <Viewer v-bind:content="srData.rcptTreat.rcptDscr" style=" padding:10px" />
                        </td>
                    </tr>
                </tbody>
            </template>
        </v-simple-table>
        <br />
        <br />
        <br />

        <v-simple-table class=" mt-4 v-table" dense>
            <template v-slot:default>
                <thead class="thead">
                    <tr>
                        <th class="text-left">
                            SR 처리
                        </th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody class="v-body">
                    <tr>
                        <td>| 처리 시작 |</td>
                        <td>
                            <v-card-text v-if="srData.rcptTreat != null">
                                {{ srData.rcptTreat.treatStartTim }}
                            </v-card-text>
                        </td>

                        <td>| 완료 시각 |</td>
                        <td>
                            <v-card-text v-if="srData.rcptTreat != null">
                                {{ srData.rcptTreat.treatEndTim }}
                            </v-card-text>
                        </td>
                    </tr>
                </tbody>
            </template>
        </v-simple-table>
        <v-simple-table class="v-table" dense>
            <template v-slot:default>
                <tbody class="v-body">
                    <tr>
                        <td>| 처리 내용 |</td>
                        <td class="dscr" v-if="srData.rcptTreat != null">
                            <Viewer v-if="srData.rcptTreat.treatDscr" v-bind:content="srData.rcptTreat.treatDscr" style=" padding:10px" />
                        </td>
                    </tr>
                </tbody>
            </template>
        </v-simple-table>
    </v-row>
</template>

<script>
import Viewer from "../components/sr-main/ToastViewer";
import { getSrInfo } from "../api/index";

export default {
    components: {
        Viewer: Viewer,
    },
    methods: {
        async fetchData() {
            const srId = this.$route.params.id;
            const res = await getSrInfo.getRes(srId);
            this.srData = res.data;
            console.log(this.srData);
        },
    },
    mounted: function() {
        this.fetchData();
    },
    data() {
        return {
            srData: {},
        };
    },
};
</script>

<style scoped>
#sbjct {
    width: 100%;
}

.v-card__text {
    width: 200px;
}

.v-data-table > .v-data-table__wrapper > table > tbody > tr > td {
    font-weight: bold;
    font-size: 1.1em;
}

td {
    width: 25%;
    border: solid 0px;
}

.theme--light.v-data-table > .v-data-table__wrapper > table > thead > tr:last-child > th {
    border: solid 0px;
    background-color: rgba(177, 206, 245, 0.815);
    font-size: 20px;
    color: black;
    border-bottom: solid 1px;
}

tr:hover {
    background-color: transparent !important;
}

.v-table {
    background-color: rgb(247, 243, 243);
    width: 75%;
    border-bottom: solid 0.1px rgba(128, 128, 128, 0.082);
    border-top: solid 0.1px rgba(128, 128, 128, 0.219);
}

p {
    color: rgb(0, 0, 0);
    display: inline;
    text-align: center;
    padding-left: 40px;
    font-size: 1.2em;
}
.dscr {
    height: 100%;
    width: 85%;
}
</style>
