<template>
    <v-row>
        <v-card class="cardBox">
            <!-- <v-row dense style="font-weight: bold" v-if="this.sr_lists">등록 ({{ sr_lists.length }}) 건</v-row> -->
            <v-row dense class="mb-2 mt-2">
                <v-col class="blue-grey--text" cols="1" dense style="font-weight: bold" v-if="this.sr_lists">등록 ({{ sr_lists.length }}) 건</v-col>
                <v-spacer></v-spacer>
                <v-col cols="1" dense>
                    <v-btn @click="srAdd" class="submitbtn blue lighten-2">추가</v-btn>
                </v-col>
            </v-row>
            <v-simple-table dense height="300px" style="padding:10px; width: 2000px">
                <template v-slot:default>
                    <thead>
                        <tr>
                            <th class="text-center no">
                                No
                            </th>
                            <th class="text-center sys">
                                시스템
                            </th>
                            <th class="text-center jobgb">
                                업무구분
                            </th>
                            <th class="text-center jobgb">
                                요청구분
                            </th>

                            <th class="text-center sbjct">
                                제목
                            </th>
                            <th class="text-center reqPerson">
                                요청자
                            </th>
                            <th class="text-center reqDate">
                                요청일시
                            </th>
                            <th class="text-center srStatus">
                                SR상태
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in sr_lists" :key="item.id" @click="click_row(item)">
                            <td class="text-center no">{{ index + 1 }}</td>
                            <td class="text-center sys">{{ item.sysNm }}</td>
                            <td class="text-center">{{ item.jobGb }}</td>
                            <td class="text-center">{{ item.reqGb }}</td>
                            <td class="text-center">{{ item.sbjct }}</td>
                            <td class="text-center reqPerson">{{ item.reqr }}</td>
                            <td class="text-center reqDate">{{ item.reqTim }}</td>
                            <td class="text-center srStatus">{{ item.status }}</td>
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
export default {
    data() {
        return {
            page: 0,
            sr_lists: [],
            isconfirmed: false,
            req: {
                srId: "",
            },
        };
    },
    methods: {
        srAdd() {
            this.$router.push("/user/srs/reg");
        },
        click_row: function(item) {
            this.$router.push(`/user/srs/detail/${item.id}`);
        },
    },
    computed: {
        checkSrList() {
            return this.$store.getters.getSrList;
        },
    },
    watch: {
        checkSrList(data) {
            this.sr_lists = data;
        },
        page: function(newVal) {
            this.$emit("pageChange", newVal);
            setTimeout(() => {
                this.sr_lists = this.$store.getters.getSrList;
            }, 400);
        },
    },
};
</script>

<style>
.cardBox {
    width: 80%;
    /* height: 300px; */
}
td {
    border: solid 0.3px rgb(89, 158, 248);
}
th {
    background-color: rgba(164, 194, 221, 0.637);
}
</style>
