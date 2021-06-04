<template>
    <v-row style="width: 100%" dense>
        <v-card class="firstBox">
            <v-card-text>
                <form @submit.prevent="submit">
                    <v-row dense>
                        <v-col class="topic" cols="1">고객사 코드</v-col>
                        <v-col cols="3">
                            <v-text-field dense outlined v-model="req.ctmmnyCd"> </v-text-field>
                        </v-col>

                        <v-col class="topic" cols="1">고객사명</v-col>
                        <v-col cols="3">
                            <v-text-field dense outlined v-model="req.ctmmnyNm"> </v-text-field>
                        </v-col>
                        <v-col class="topic" cols="1">사용여부</v-col>

                        <v-col cols="3">
                            <v-select item-color="purple" :items="usedList" item-text="usedName" item-value="isUsed" dense outlined v-model="req.isUsed"></v-select>
                        </v-col>
                    </v-row>
                    <v-row dense>
                        <v-col cols="11"></v-col>
                        <v-col>
                            <v-btn type="submit" class="submitbtn blue lighten-2">조회</v-btn>
                        </v-col>
                    </v-row>
                </form>
            </v-card-text>
        </v-card>
    </v-row>
</template>

<script>
import { getCtmmnyInfoList } from "../../api/index";

export default {
    props: ["propsData"],
    data: () => ({
        usedList: [
            {
                usedName: "사용가능",
                isUsed: true,
            },
            {
                usedName: "사용불가",
                isUsed: false,
            },
            {
                usedName: "전체보기",
                isUsed: "",
            },
        ],
        req: {
            ctmmnyCd: "",
            ctmmnyNm: "",
            isUsed: "",
            page: 0,
        },
    }),
    watch: {
        propsData: function(newVal) {
            this.req.page = newVal - 1;
            this.getCtmmnyInfoList();
        },
    },
    methods: {
        submit() {
            this.getCtmmnyInfoList();
        },

        async getCtmmnyInfoList() {
            const res = await getCtmmnyInfoList.getRes(this.req);
            this.$store.commit("GETCTMMNYLIST", res.data.content);
        },
    },
    created: function() {
        this.getCtmmnyInfoList();
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
