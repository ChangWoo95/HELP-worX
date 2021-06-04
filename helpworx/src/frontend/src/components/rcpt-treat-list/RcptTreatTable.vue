<template>
    <v-row class="mt-4 ml-4" dense style="width: 100%">
        <v-col cols="8">
            <v-card>
                <v-col cols="12" v-if="this.sr_lists" dense>등록 ({{ sr_lists.length }}) 건</v-col>

                <v-simple-table style="margin-top:3px" dense>
                    <template v-slot:default>
                        <thead class="grey lighten-3">
                            <tr>
                                <th class="text-center no">
                                    No
                                </th>

                                <th class="text-center">
                                    고객사
                                </th>
                                <th class="text-center sys">
                                    시스템
                                </th>
                                <th class="text-center jobgb">
                                    업무구분
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
                                <th class="text-center rqmtTime">
                                    접수자
                                </th>

                                <th class="text-center rqmtTime">
                                    처리자
                                </th>

                                <th class="text-center rqmtTime">
                                    처리일시
                                </th>
                                <th class="text-center rqmtTime">
                                    걸린시간
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="item in sr_lists" :key="item.no" @click="click_row(item)">
                                <td class="text-center no">{{ item.id }}</td>
                                <td class="text-center ">{{ item.ctmmnyNm }}</td>
                                <td class="text-center">{{ item.sysNm }}</td>
                                <td class="text-center">{{ item.jobGb }}</td>
                                <td>{{ item.sbjct }}</td>
                                <td class="text-center reqPerson">{{ item.reqr }}</td>
                                <td class="text-center reqDate">{{ item.reqTim }}</td>
                                <td class="text-center srStatus">{{ item.status }}</td>
                                <td v-if="item.rcptTreat != null" class="text-center rqmtTime">{{ item.rcptTreat.recivrNm }}</td>
                                <td v-else class="text-center rqmtTime"></td>

                                <td v-if="item.rcptTreat != null" class="text-center rqmtTime">{{ item.rcptTreat.opetrNm }}</td>
                                <td v-else class="text-center rqmtTime"></td>
                                <td v-if="item.rcptTreat != null" class="text-center rqmtTime">{{ item.rcptTreat.treatEndTim }}</td>
                                <td v-else class="text-center rqmtTime"></td>
                                <td v-if="item.rcptTreat != null" class="text-center rqmtTime">{{ item.rcptTreat.treatTim }}</td>
                                <td v-else class="text-center rqmtTime"></td>
                            </tr>
                        </tbody>
                    </template>
                </v-simple-table>
                <v-card elevation="0">
                    <v-pagination v-model="page" :length="5" prev-icon="mdi-menu-left" next-icon="mdi-menu-right"></v-pagination>
                </v-card>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
export default {
    data() {
        return {
            sr_lists: [],
            page: 0,
        };
    },

    methods: {
        click_row: function(item) {
            this.$router.push(`/oper/rcptTreat/${item.id}`);
        },
    },
    computed: {
        checkSrList() {
            return this.$store.getters.getSrList;
        },
    },
    watch: {
        page: function(newVal) {
            this.$emit("pageChange", newVal);
            setTimeout(() => {
                this.sr_lists = this.$store.getters.getSrList;
            }, 400);
            console.log(this.sr_lists);
        },
        checkSrList(data) {
            console.log(data);
            this.sr_lists = data;
        },
    },
};
</script>

<style></style>
