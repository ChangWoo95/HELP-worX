<template>
    <v-row dense style="width: 100%">
        <v-card width="60%">
            <v-col v-if="chttRoomList != null" cols="12" dense>등록 ({{ this.chttRoomList.length }}) 건</v-col>

            <ChatDetail :userData="this.userData" v-on:closeDialog="closeDialog" :clickedChttRoom="chttRoom"></ChatDetail>
            <v-simple-table style="margin-top: 20px">
                <template v-slot:default>
                    <thead class="grey lighten-3">
                        <tr>
                            <th class="text-center no">
                                No
                            </th>
                            <th class="text-center sys">
                                시스템
                            </th>
                            <th class="text-center reqPerson">
                                고객명
                            </th>
                            <th class="text-center sbjct">
                                고객사
                            </th>
                            <th class="text-center reqDate">
                                시작일시
                            </th>
                            <th class="text-center reqPerson">
                                상태
                            </th>
                            <th class="text-center reqDate">
                                종료일시
                            </th>
                        </tr>
                    </thead>
                    <tbody v-if="chttRoomList != null">
                        <tr v-for="chttRoom in chttRoomList" :key="chttRoom.id" @click="click_row(chttRoom)">
                            <td class="text-center no">{{ chttRoom.id }}</td>
                            <td class="text-center sys">{{ chttRoom.sysNm }}</td>
                            <td>{{ chttRoom.userNm }}</td>
                            <td>{{ chttRoom.ctmmnyNm }}</td>
                            <td class="text-center reqDate">{{ getYYYYMMDDHHMMSS(chttRoom.chttStartTim) }}</td>
                            <td v-if="chttRoom.status == '진행중'" class="text-center reqPerson" style="background-color:#B2DFDB;">{{ chttRoom.status }}</td>
                            <td v-else class="text-center reqPerson" style="background-color:lightgrey;">{{ chttRoom.status }}</td>
                            <td class="text-center reqDate">{{ chttRoom.chttEndTim }}</td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
            <v-card elevation="0">
                <v-pagination v-model="page" :length="5" prev-icon="mdi-menu-left" next-icon="mdi-menu-right"></v-pagination>
            </v-card>
        </v-card>
    </v-row>
</template>

<script>
import axios from "axios";
import ChatDetail from "./ChatDetail.vue";
export default {
    props: ["userData"],

    data() {
        return {
            page: 1,
            chttRoomList: null,
            chttRoom: null,
        };
    },

    components: {
        ChatDetail: ChatDetail,
    },

    methods: {
        getTable: function(page) {
            const resourceHost = "http://13.125.164.214:9090";
            axios
                .get(`${resourceHost}/api/oper/chttrooms`, { params: { page: page } })
                .then((response) => {
                    this.chttRoomList = response.data.content;
                })
                .catch((error) => console.log(error));
        },

        click_row: function(chttRoom) {
            this.chttRoom = chttRoom;
        },

        closeDialog: function() {
            this.chttRoom = null;
        },

        getYYYYMMDDHHMMSS: function(time) {
            const format = "YYYY-MM-DD HH:mm:ss";
            return (
                this.$moment(new Date(time))
                    .format(format)
                    .split(" ")[0] +
                " / " +
                this.$moment(new Date(time))
                    .format(format)
                    .split(" ")[1]
            );
        },
    },

    computed: {
        checkChttList() {
            return this.$store.getters.getChttList;
        },
    },

    watch: {
        page: function(newVal) {
            this.$emit("pageChange", newVal);
            setTimeout(() => {
                this.chttRoomList = this.$store.getters.getChttList;
            }, 400);
        },
        checkChttList(data) {
            this.chttRoomList = data;
        },
    },
};
</script>

<style>
#div {
    background-color: rgb(64, 231, 189);
}
</style>
