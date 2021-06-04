<template>
    <v-simple-table>
        <template v-slot:default>
            <thead>
                <tr>
                    <th class="text-center no">
                        No
                    </th>
                    <th class="text-center sys">
                        시스템
                    </th>
                    <th class="text-center sbjct">
                        제목
                    </th>
                    <th class="text-center reqr">
                        요청자
                    </th>
                    <th class="text-center req_tim">
                        요청시간
                    </th>
                    <th class="text-center state">
                        상태
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item, index) in ctmmny_sr_datas.slice(0, 5)" :key="item.id" @click="click_row(item)">
                    <td class="text-center no">{{ index + 1 }}</td>
                    <td class="text-center sys">{{ item.sysNm }}</td>
                    <td>{{ item.sbjct }}</td>
                    <td>{{ item.reqrNm }}</td>
                    <td class="text-center req_tim">{{ item.reqTim }}</td>
                    <td class="text-center state">{{ item.status }}</td>
                </tr>
            </tbody>
        </template>
    </v-simple-table>
</template>
<script>
import { getInCompleteCtmmnyDatas } from "../../api/index";

export default {
    methods: {
        click_row: function(item) {
            this.$router.push(`/user/srs/detail/${item.id}`);
        },
        async getInCompleteCtmmnyDatas() {
            const res = await getInCompleteCtmmnyDatas.getRes();
            this.ctmmny_sr_datas = res.data;
        },
    },
    data() {
        return {
            ctmmny_sr_datas: [],
        };
    },
    mounted() {
        this.getInCompleteCtmmnyDatas();
    },
};
</script>

<style>
th,
td {
    border: solid 1px rgb(89, 158, 248);
}
th {
    background-color: rgba(164, 194, 221, 0.637);
}
.no {
    width: 20px;
}
.sys {
    width: 130px;
}
</style>
