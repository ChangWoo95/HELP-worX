<template>
    <v-container v-if="srData != null">
        <!-- SR 접수 -->

        <v-card elevation="0">
            <v-simple-table class="v-table">
                <template v-slot:default>
                    <thead class="thead">
                        <tr>
                            <th style="font-size: 20px; color:black;">
                                SR 접수
                            </th>
                            <th style="color:green; text-align:right; padding-right:1px">
                                완료 예정일 / 접수 내용 등록시
                            </th>
                            <th style="color:green; text-align:left; padding-left:3px">
                                SR이 접수 처리되며, 상태가 '진행중' 으로 변경됩니다
                            </th>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody class="v-body">
                        <tr>
                            <td>완료 예정일</td>
                            <td>
                                <v-card-text>
                                    <datetime class="datetime" v-model="rcptTreatData.cmplPrdt" use12-hour></datetime>
                                    <h5 style="color:grey; ">(날짜 선택)</h5>
                                </v-card-text>
                            </td>
                            <td>
                                <h3 style="color:blue;float :left">(상태 선택)</h3>
                                <select v-model="rcptTreatData.status">
                                    <option v-for="status in statusList" :key="status" :value="status">{{ status }}</option>
                                </select>
                            </td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>

            <v-simple-table class="v-table">
                <template v-slot:default>
                    <tbody class="v-body">
                        <tr>
                            <td>접수 내용</td>
                            <td class="dscr">
                                <Editor height="300px" :initialValue="rcptTreatData.rcptDscr" initialEditType="wysiwyg" ref="rcptEditor" />
                            </td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
            <v-card elevation="0">
                <v-btn @click="rcptAction">접수</v-btn>
                <v-btn @click="cancel">취소</v-btn>
            </v-card>
        </v-card>
        <br />

        <!-- SR 처리 -->
        <v-card elevation="0">
            <v-simple-table class="v-table">
                <template v-slot:default>
                    <thead class="thead">
                        <tr>
                            <th style="font-size: 20px; color:black;">
                                SR 처리
                            </th>
                            <th style="color:green; text-align:right; padding-right:1px">
                                처리 내용 기입후
                            </th>
                            <th style="color:green; text-align:left; padding-left:3px">
                                상태를 '해결' 으로 변경바랍니다
                            </th>
                            <th>총소요시간</th>
                            <th>{{ treatTim }}</th>
                        </tr>
                    </thead>
                    <tbody class="v-body">
                        <tr>
                            <td>처리시간</td>
                            <td>
                                <v-card-text>
                                    <datetime class="datetime" type="datetime" auto v-model="treatStartTim" use12-hour></datetime>
                                    <h2 style="float:right; color:grey; ">~</h2>

                                    <h6 style="color:grey; ">(시작 날짜 선택)</h6>
                                </v-card-text>
                            </td>
                            <td>
                                <v-card-text>
                                    <datetime class="datetime" type="datetime" auto v-model="treatEndTim" use12-hour></datetime>
                                    <h6 style="color:grey; ">(완료 날짜 선택)</h6>
                                </v-card-text>
                            </td>
                        </tr>
                        <!-- <tr>
                            <td class="text">상태</td>
                            <td>
                                <select v-model="rcptTreatData.status">
                                    <option v-for="status in statusList" :key="status" :value="status">{{status}}</option>
                                </select>
                                <h6 style="color:grey; ">(상태 선택)</h6>
                            </td>
                        </tr> -->
                    </tbody>
                </template>
            </v-simple-table>

            <v-simple-table class="v-table">
                <template v-slot:default>
                    <tbody class="v-body">
                        <tr>
                            <td>처리 내용</td>
                            <td class="dscr">
                                <Editor height="300px" :initialValue="rcptTreatData.treatDscr" initialEditType="wysiwyg" ref="treatEditor" />
                            </td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </v-card>
        <v-card elevation="0">
            <v-btn @click="treatAction">처리</v-btn>
            <v-btn @click="cancel">취소</v-btn>
        </v-card>
    </v-container>
</template>

<script>
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";

import "vue-datetime/dist/vue-datetime.css";

const getYYYYMMDD = function(time) {
    return time.split("T")[0];
};

import { saveRcpt } from "@/api/index";
export default {
    props: ["userNm", "srData"],
    components: {
        Editor,
    },
    data: function() {
        return {
            rcptTreatData: {
                cmplPrdt: "",
                treatEndTim: "",
                treatStartTim: "",
                rcptDscr: "",
                treatDscr: "",
                status: "접수대기",
                rcptTim: "",
                opetrNm: "",
                recivrNm: "",
                treatTim: "",
            },
            treatEndTim: "",
            treatStartTim: "",
            treatTim: "",
            statusList: ["접수대기", "진행중", "해결"],
        };
    },
    watch: {
        treatEndTim: function() {
            this.treatTim = this.setTreatTim();
            this.rcptTreatData.treatEndTim = this.treatEndTim;
        },
        treatStartTim: function() {
            this.treatTim = this.setTreatTim();
            this.rcptTreatData.treatStartTim = this.treatStartTim;
        },
        srData: function() {
            this.rcptTreatData.cmplPrdt = this.srData.rcptTreat.cmplPrdt;
            if (this.srData.rcptTreat.treatStartTim != null && this.srData.rcptTreat.treatEndTim != null) {
                this.treatStartTim = this.makeDateTime(this.srData.rcptTreat.treatStartTim);
                this.treatEndTim = this.makeDateTime(this.srData.rcptTreat.treatEndTim);
            }
            this.rcptTreatData.rcptDscr = this.srData.rcptTreat.rcptDscr;
            this.rcptTreatData.treatDscr = this.srData.rcptTreat.treatDscr;
            this.rcptTreatData.opetrNm = this.srData.rcptTreat.opetrNm;
            this.rcptTreatData.recivrNm = this.srData.rcptTreat.recivrNm;
            this.rcptTreatData.status = this.srData.status;
            this.rcptTreatData.rcptTim = this.srData.rcptTreat.rcptTim;
            console.log("여기");
            console.log(this.rcptTreatData.rcptTim);
            console.log(this.srData);
            this.$refs.rcptEditor.invoke("setMarkdown", this.rcptTreatData.rcptDscr);
            this.$refs.treatEditor.invoke("setMarkdown", this.rcptTreatData.treatDscr);
        },
    },
    methods: {
        cancel() {
            history.back();
        },
        makeDateTime(timestamp) {
            var datetime = "";
            datetime += timestamp.split(" ")[0] + "T" + timestamp.split(" ")[1] + ".000Z";
            return datetime;
        },

        toJson: function(data) {
            return JSON.stringify(data);
        },

        validateCmpl: function() {
            let now = getYYYYMMDD(this.$moment(new Date()).toISOString());
            // 접수 내용 작성시 진행중으로 변경
            if (this.rcptTreatData.cmplPrdt == "" || getYYYYMMDD(this.rcptTreatData.cmplPrdt) < now) {
                alert("완료예정일을 정상적으로 입력하시오");
                return false;
            } else if (this.rcptTreatData.rcptDscr.length < 20) {
                alert("접수 내용을 정상적으로 입력하시오 (20자 이상)");
                return false;
            } else if (this.rcptTreatData.status != "진행중") {
                alert("상단의 상태를 진행중으로 입력하시오");
                return false;
            } else {
                this.rcptTreatData.recivrNm = this.userNm;
                return true;
            }
        },

        validateTreat: function() {
            if (this.rcptTreatData.cmplPrdt == "") {
                alert("'접수'의 완료예정일을 정상적으로 입력하시오");
                return false;
            } else if (this.rcptTreatData.rcptDscr.length < 20) {
                alert("'접수'의 내용을 정상적으로 입력하시오 (20자 이상)");
                return false;
            }else if (this.rcptTreatData.status != "해결") {
                alert("상태를 해결로 선택하시오");
                return false;
            } else if (this.rcptTreatData.treatEndTim == "" || this.rcptTreatData.treatStartTim == "" || this.rcptTreatData.treatEndTim == null || this.rcptTreatData.treatStartTim == null) {
                alert("날짜를 모두 선택하시오");
                return false;
            } else if (this.rcptTreatData.treatDscr.length < 20) {
                alert("처리 내용을 정상적으로 입력하시오 (20자 이상)");
                return false;
            } else {
                if (this.rcptTreatData.recivrNm == "") {
                    this.rcptTreatData.recivrNm = this.userNm;
                }
                this.rcptTreatData.opetrNm = this.userNm;
                return true;
            }
        },

        setTreatTim: function() {
            if (this.treatEndTim != "" && this.treatStartTim != "") {
                var day1 = new Date(parseInt(this.treatEndTim.split("-")[0]), parseInt(this.treatEndTim.split("-")[1]), parseInt(this.treatEndTim.split("-")[2]));
                var day2 = new Date(parseInt(this.treatStartTim.split("-")[0]), parseInt(this.treatStartTim.split("-")[1]), parseInt(this.treatStartTim.split("-")[2]));
                var getDiffTime = day1.getTime() - day2.getTime();
                if (getDiffTime < 0) {
                    alert("올바른 완료 날짜를 선택하시오");
                    this.treatEndTim = null;
                } else {
                    var m = Math.floor(getDiffTime / (1000 * 60 * 60 * 24 * 30));
                    var d = Math.floor( (getDiffTime % (1000 * 60 * 60 * 24 * 30) ) / (1000 * 60 * 60 * 24));
                    var h = Math.floor( (getDiffTime % (1000 * 60 * 60 * 24 * 30) ) % (1000 * 60 * 60 * 24)  /  (1000 * 60 * 60));
                }
                
                return m + "개월 " + d + "일 " + h +"시간 " ;
            }
        },

        getEditor: function() {
            this.rcptTreatData.rcptDscr = this.$refs.rcptEditor.invoke("getMarkdown");
            this.rcptTreatData.treatDscr = this.$refs.treatEditor.invoke("getMarkdown");
        },

        rcptAction: async function() {
            this.getEditor();
            this.rcptTreatData.rcptTim = this.$moment(new Date()).format("YYYY-MM-DDTHH:mm:ss");
            if (this.validateCmpl()) {
                var requestBody = this.toJson(this.rcptTreatData);
                var srId = this.$route.params.id;
                const res = saveRcpt.getRes(requestBody, srId);
                console.log(res);
                this.$router.replace("/oper/srs/list");
            }
        },
        treatAction: async function() {
            this.getEditor();
            if (this.validateTreat()) {
                this.rcptTreatData.treatTim = this.treatTim;
                var requestBody = this.toJson(this.rcptTreatData);
                var srId = this.$route.params.id;
                const res = saveRcpt.getRes(requestBody, srId);
                console.log(res);
                this.$router.replace("/oper/srs/list");
            }
        },
    },
};
</script>

<style scoped>
th {
    border: solid 0px;
    background-color: rgba(177, 206, 245, 0.815);
}
tr:hover {
    background-color: transparent !important;
}

.v-data-table > .v-data-table__wrapper > table > tbody > tr > td {
    font-weight: bold;
}
.v-data-table > .v-data-table__wrapper > table > tbody > tr > td {
    border: solid 0px;
}
.v-table {
    width: 75%;
    border-bottom: solid 0.1px rgba(128, 128, 128, 0.082);
    border-top: solid 0.1px rgba(128, 128, 128, 0.219);
}
div.v-card__text {
    margin-bottom: -10px;
    margin-top: -10px;
}
select {
    border: 0px;
    height: 50px;
    width: 50%;
    border-bottom: dashed 1px black;
}

.dscr,
.sbjct {
    height: 100%;
    width: 85%;
}
.datetime {
    border-bottom: dashed 1px black;
    width: 170px;
    font-weight: bold;
    font-size: 17px;
}
</style>
