<template>
    <v-row dense style="width: 100%">
        <v-card class="firstBox">
            <v-card-text>
                <form>
                    <v-row class="boxRow mb-4">
                        <v-col class="topic" cols="1">*시스템</v-col>
                        <v-col>
                            <v-select item-color="purple" dense outlined :items="systems" item-text="sysNm" item-value="sysNm" v-model="data.sysNm"></v-select>
                        </v-col>

                        <v-col class="topic" cols="1">상태</v-col>
                        <v-col>
                            <v-select dense :items="status" v-model="data.status" outlined></v-select>
                        </v-col>
                    </v-row>
                    <v-row class="boxRow">
                        <v-col class="topic" cols="1">내용</v-col>
                        <v-col cols="3">
                            <v-text-field dense outlined v-model="content"> </v-text-field>
                        </v-col>

                        <v-col cols></v-col>
                        <v-col cols="1">
                            <v-btn @click="submit" class="submitbtn deep-purple darken-1">조회</v-btn>
                        </v-col>
                    </v-row>
                </form>
            </v-card-text>
        </v-card>
    </v-row>
</template>

<script>
import axios from "axios";
import { findChatListWithCondition } from "../../api/index";

export default {
    props: ["propsData"],
    data: () => ({
        systems: [],
        status: ["전체", "진행중", "완료"],
        content: "",
        data: {
            page: 0,
            sysNm: "",
            status: "",
        },
        page: 0,
    }),
    watch: {
        propsData: function(newVal) {
            this.data.page = newVal - 1;
            this.findChatListWithCondition();
        },
    },
    methods: {
        submit() {
            this.page = 0;
            this.data.page = 0;
            this.findChatListWithCondition();
        },

        async findChatListWithCondition() {
            if (this.data.status == "전체") {
                this.data.status = "";
            }
            const res = await findChatListWithCondition.getRes(this.data);
            this.$store.commit("GETCHTTLIST", res.data.content);
        },
    },
    created: function() {
        this.findChatListWithCondition();

        const resourceHost = "http://13.125.164.214:9090";
        axios
            .get(`${resourceHost}/api/cust/sys`)
            .then((response) => {
                this.systems = response.data;
            })
            .catch((error) => console.log(error));
    },
};
</script>

<style>
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
    width: 60%;
    max-height: 60%;

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
