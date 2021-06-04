<template>
    <v-row class="mt-2 ml-4" dense>
        <v-col cols="10">
            <v-card>
                <v-simple-table class="v-table" dense>
                    <template v-slot:default>
                        <thead class="thead">
                            <tr>
                                <th style="font-size: 20px; color:black;border-bottom:solid 1px;" class="text-left">
                                    SR 등록
                                </th>
                                <th style="border-bottom:solid 1px;" class="text-left"></th>
                                <th style="border-bottom:solid 1px;" class="text-left"></th>
                                <th style="border-bottom:solid 1px;" class="text-left"></th>
                            </tr>
                        </thead>
                        <tbody class="v-body">
                            <tr>
                                <td>*시스템</td>
                                <td>
                                    <select v-model="sysOption">
                                        <option v-for="sysData in SysAndJobGbData" :key="sysData.id" :value="sysData.id">{{ sysData.sysNm }}</option>
                                    </select>
                                </td>
                                <td>*업무 구분</td>
                                <td>
                                    <select v-if="sysOption >= 0" v-model="jobGbOption">
                                        <option v-for="(job, index) in SysAndJobGbData[sysOption].jobs" :key="job.id" :value="index">{{ job.jobGb }}</option>
                                    </select>
                                    <select v-else>
                                        <option>시스템 택 必</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>*요청구분</td>
                                <td>
                                    <select v-if="jobGbOption >= 0" v-model="sr.reqGb">
                                        <option v-for="(reqGb, index) in reqGbList" :key="index" :value="reqGb">{{ reqGb }}</option>
                                    </select>
                                    <select v-else>
                                        <option>시스템,업무구분 택 必</option>
                                    </select>
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </template>
                </v-simple-table>
                <v-simple-table class="v-table" dense>
                    <template v-slot:default>
                        <thead class="thead">
                            <tr></tr>
                        </thead>
                        <tbody class="v-body">
                            <tr>
                                <td>
                                    <br />*제목
                                    <v-text-field elevation="0" v-model="sr.sbjct" maxlength="25" label="제목" dense solo></v-text-field>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <br />*내용<br /><br />
                                    <Editor v-on:submitEditor="submitEditor" v-on:cancel="cancel" />
                                    <br />
                                </td>
                            </tr>
                        </tbody>
                    </template>
                </v-simple-table>
            </v-card>
        </v-col>
        <v-col></v-col>
    </v-row>
</template>

<script>
import Editor from "../components/sr-main/ToastEditor";
import { saveSr, getCtmmnySys } from "@/api/index";

const checkFillAllData = function(sr, sysOption, jobGbOption) {
    if (sysOption < 0) {
        alert("시스템을 선택하시오");
        return false;
    } else if (jobGbOption < 0) {
        alert("업무구분을 선택하시오");
        return false;
    } else if (sr.reqGb < 0) {
        alert("요청구분을 선택하시오");
        return false;
    }
    if (sr.sbjct.length < 2) {
        alert("제목을 2자 이상 입력하시오");
        return false;
    } else if (sr.dscr.length < 1) {
        alert("내용을 5자 이상 입력하시오");
        return false;
    } else {
        return true;
    }
};

const toJson = function(sr) {
    sr.reqTim = new Date().getTimezoneOffset();
    return JSON.stringify(sr);
};

export default {
    components: {
        Editor: Editor,
    },

    methods: {
        cancel() {
            if (confirm("SR 등록을 취소하시겠습니까??") == true) {
                //확인
                this.$router.push("/user/srs/main");
            } else {
                //취소
                return;
            }
        },
        async submitEditor(data) {
            this.sr.dscr = data;
            if (checkFillAllData(this.sr)) {
                this.sr.sysNm = this.SysAndJobGbData[this.sysOption].sysNm;
                this.sr.jobGb = this.SysAndJobGbData[this.sysOption].jobs[this.jobGbOption].jobGb;
                var requestBody = toJson(this.sr);
                await saveSr.getRes(requestBody);
                this.$router.replace("/user/srs/main");
            }
        },
    },

    created: async function() {
        const res = await getCtmmnySys.getRes();
        this.SysAndJobGbData = res.data;
    },

    data: function() {
        return {
            sr: {
                sysNm: "",
                jobGb: "",
                reqGb: "",
                sbjct: "",
                dscr: "",
                status: "진행중",
                reqTim: "",
            },
            SysAndJobGbData: null,
            reqGbList: ["데이터수정", "개선", "오류", "문의"],

            sysOption: -1,
            jobGbOption: -1,
            reqGbOption: -1,
        };
    },
};
</script>

<style scoped>
.v-data-table > .v-data-table__wrapper > table > tbody > tr > td {
    font-weight: bold;
    padding-top: 3px;
    width: 25%;
    border: solid 0px;
}
.v-text-field {
    padding-top: 0;
    float: right;
    width: 74.5%;
    margin-top: -10px;
    margin-bottom: -20px;
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
    background-color: rgba(216, 216, 216, 0.233);
}

select {
    border: solid 1.5px grey;
    width: 100%;
    padding-top: 5px;
    padding-bottom: 5px;
    padding-left: 20px;
}
</style>
