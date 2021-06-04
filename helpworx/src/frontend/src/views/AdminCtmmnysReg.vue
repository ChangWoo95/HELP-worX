<template>
    <v-row style="width: 100%; height:100%" dense>
        <v-card style="width: 65%">
            <v-simple-table class="v-table" dense>
                <template v-slot:default>
                    <thead class="thead">
                        <tr>
                            <th style="font-size: 20px; color:black;border-bottom:solid 1px;" class="text-left">
                                고객사 입력
                            </th>
                            <th style="border-bottom:solid 1px;" class="text-left"></th>
                            <th style="border-bottom:solid 1px;" class="text-left"></th>
                            <th style="border-bottom:solid 1px;" class="text-left"></th>
                        </tr>
                    </thead>
                    <tbody class="v-body">
                        <tr>
                            <td>* 고객사 코드</td>
                            <td colspan="1">
                                <v-text-field
                                    style="width: 50%"
                                    v-model="ctmmny.ctmmnyCd"
                                    maxlength="25"
                                    dense
                                    solo
                                    :readonly="isReceived === true"
                                    :disabled="isReceived === true"
                                    :filled="isReceived === true"
                                ></v-text-field>
                            </td>
                        </tr>

                        <tr>
                            <td>* 고객사명</td>
                            <td colspan="3">
                                <v-text-field v-model="ctmmny.ctmmnyNm" maxlength="25" dense solo></v-text-field>
                            </td>
                        </tr>
                        <tr>
                            <td>고객설명</td>
                            <td colspan="3">
                                <v-text-field v-model="ctmmny.ctmmnyDscr" maxlength="25" solo></v-text-field>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="1">
                                *계약시작일
                            </td>
                            <td colspan="1">
                                <v-card-text>
                                    <datetime class="datetime" auto v-model="contBeginDate" use12-hour></datetime>
                                    <h6 style="color:grey; ">(시작 날짜 선택)</h6>
                                </v-card-text>
                            </td>
                            <td colspan="1">
                                *계약종료일
                            </td>
                            <td colspan="1">
                                <v-card-text>
                                    <datetime class="datetime" auto v-model="contEndDate" use12-hour></datetime>
                                    <h6 style="color:grey; ">(완료 날짜 선택)</h6>
                                </v-card-text>
                            </td>
                        </tr>
                        <tr>
                            <td>*사용시스템</td>
                            <v-row v-for="item in options" :key="item.id">
                                <v-checkbox v-model="ctmmny.systemIdList" :label="item.name" :value="item.id" color="grey lighten-1" style="font-weight: bold"></v-checkbox>
                            </v-row>
                        </tr>
                        <tr>
                            <td>사용 여부</td>
                            <v-checkbox disabled input-value="true" style="font-weight: bold"></v-checkbox>
                        </tr>
                        <tr>
                            <td colspan="2"></td>
                            <td colspan="2">
                                <v-btn @click="saveCtmmny()" class="mr-2">저장</v-btn>

                                <v-btn v-if="isReceived" @click="ctmmnyDelete()" class="mr-2">삭제</v-btn>
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
import { getCtmmnyUsingSystems, getCtmmnyInfo, saveCtmmny, updateCtmmnyInfo } from "@/api/index";

export default {
    components: {},
    data: function() {
        return {
            ctmmny: {
                ctmmnyCd: "",
                ctmmnyNm: "",
                ctmmnyDscr: "",
                contBeginDate: "",
                contEndDate: "",
                systemIdList: [],
                isUsed: true,
            },
            contBeginDate: "",
            contEndDate: "",
            isReceived: false,
            options: [],
            req: {
                ctmmnyCd: "",
            },
        };
    },

    watch: {
        contBeginDate: function() {
            this.timeValid();
            this.ctmmny.contBeginDate = this.contBeginDate;
        },
        contEndDate: function() {
            this.timeValid();
            if (this.contEndDate == null) {
                this.contEndDate = "";
                this.ctmmny.contEndDate = "";
            }
            this.ctmmny.contEndDate = this.contEndDate;
        },
    },

    methods: {
        makeDateTime(timestamp) {
            var datetime = "";
            datetime += timestamp.split("T")[0] + "T" + timestamp.split("T")[1] + ".000Z";
            console.log(datetime);
            return datetime;
        },
        ctmmnyDelete() {
            if (confirm("삭제하시겠습니까?") == true) {
                //확인
                this.req.ctmmnyCd = this.ctmmny.ctmmnyCd;
                this.deleteCtmmny();
            } else {
                console.log("실패");
                return;
            }
        },
        async deleteCtmmny() {
            const res = await updateCtmmnyInfo.getRes(this.req);
            if (res != -1) this.$router.push("/admin/ctmmnys/list");
        },

        cancel() {
            this.$router.push("/admin/ctmmnys/list");
        },
        getSystemNames(res) {
            this.options = res;
        },
        async fetchData() {
            const ctmmnyId = this.$route.params.id;
            if (ctmmnyId === undefined) return;

            const res = await getCtmmnyInfo.getRes(ctmmnyId);
            this.ctmmny = res.data;
            if (this.ctmmny.contBeginDate != null && this.ctmmny.contEndDate != null) {
                this.contBeginDate = this.makeDateTime(this.ctmmny.contBeginDate);
                this.contEndDate = this.makeDateTime(this.ctmmny.contEndDate);
            }
            this.isReceived = true;
        },
        async getCtmmnyUsingSystems() {
            const res = await getCtmmnyUsingSystems.getRes();
            this.getSystemNames(res.data);
        },
        async saveCtmmny() {
            if (this.checkFillAllData(this.ctmmny)) {
                const requestBody = this.ctmmny;
                await saveCtmmny.getRes(requestBody);
                this.$router.replace("/admin/ctmmnys/list");
            }
        },
        checkFillAllData: function(ctmmny) {
            if (ctmmny.ctmmnyCd === null) {
                alert("코드명을 입력하세요");
                return false;
            } else if (ctmmny.ctmmnyNm === null) {
                alert("고객사명을 적으세요");
                return false;
            } else if (ctmmny.ctmmnyDscr === null) {
                alert("고객설명을 적어주세요");
            } else if (!this.timeValid()) {
                alert("시작,완료 날짜를 선택하시오");
            } else {
                return true;
            }
        },

        timeValid: function() {
            if (this.contBeginDate != "" && this.contEndDate != "" && this.contBeginDate != null && this.contEndDate != null) {
                var end = new Date(parseInt(this.contEndDate.split("-")[0]), parseInt(this.contEndDate.split("-")[1]), parseInt(this.contEndDate.split("-")[2]));
                var begin = new Date(parseInt(this.contBeginDate.split("-")[0]), parseInt(this.contBeginDate.split("-")[1]), parseInt(this.contBeginDate.split("-")[2]));
                var getDiffTime = end.getTime() - begin.getTime();
                if (getDiffTime < 0) {
                    this.contEndDate = null;
                    alert("올바른 완료 날짜를 선택하시오");
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        },
    },
    created() {
        this.getCtmmnyUsingSystems();
        this.fetchData();
    },
};
</script>

<style scoped>
.v-data-table > .v-data-table__wrapper > table > tbody > tr > td {
    font-weight: bold;
}
.v-text-field {
    padding-top: 0;
}
td {
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
    border: solid 1.5px grey;
    width: 100%;
}

.datetime {
    border-bottom: dashed 1px black;
    width: 170px;
    font-weight: bold;
    font-size: 17px;
}
</style>
