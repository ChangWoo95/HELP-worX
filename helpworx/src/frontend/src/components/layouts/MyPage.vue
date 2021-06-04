<template>
    <v-dialog v-model="dialog"  width="600px">

        <v-card elevation="0">
            <v-simple-table class="v-table">
                <template v-slot:default>
                    <thead class="thead">
                        <tr>
                            <th style="font-size: 20px; color:black;border-bottom:solid 1px;" class="text-left">
                                {{user.userNm}} 님
                            </th>
                            <th style="border-bottom:solid 1px;" class="text-left"></th>
                            <th style="border-bottom:solid 1px;" class="text-left"></th>
                            <th style="border-bottom:solid 1px;" class="text-left"></th>
                        </tr>
                    </thead>
                    <tbody class="v-body">
                        <tr>
                            <td>* 고객사</td>
                            <td colspan="3">
                                <v-card-text
                                > {{user.ctmmny.ctmmnyNm}}</v-card-text>
                            </td>
                        </tr>

                        <tr>
                            <td>* 고객ID</td>
                            <td colspan="3">
                                <v-card-text
                                >{{user.userId}}</v-card-text>
                            </td>
                        </tr>
                        <tr>
                            <td>* 고객 Email</td>
                            <td colspan="3">
                                <v-card-text >{{user.userEmail}}</v-card-text>
                            </td>
                        </tr>
                        <tr>
                            <td>전화번호</td>
                            <td colspan="3">
                                <v-card-text hide-details dense >{{user.userTeleno}}</v-card-text>
                            </td>
                        </tr>
                        <tr>
                            <td>권한</td>
                            <td colspan="3">
                                <v-card-text hide-details dense >{{user.roles}}</v-card-text>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"></td>
                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </v-card>
    </v-dialog>
</template>

<script>
import { getCtmmnyCdAndNm, getUserInfo } from '../../api/index'

export default {
    props:['dialog','userId'],
    components: {},
    data: function() {
        return {
            user: {
                userId: "",
                userEmail: "",
                userNm: "",
                userPwd: "",
                userTeleno: "",
                ctmmnyCd: "",
            },
            ctmmnyNm:'',
            dialogg:false,
            ctmmnyDatas:'',
            isReceived: false,
            options: [],
        };
    },

    methods: {
        getCtmmnyInfos(res) {
            this.ctmmnyDatas = res;
        },
        async fetchData() {
            const res = await getUserInfo.getRes();
            this.user = res.data;
            for(var ctmmny of this.ctmmnyDatas){
                if (ctmmny.ctmmnyCd == this.user.ctmmnyCd){
                    this.ctmmnyNm = ctmmny.ctmmnyNm
                    break;
                }
            }
            this.isReceived = true;
        },
        async getCtmmnyCdAndNm() {
            const res = await getCtmmnyCdAndNm.getRes();
            this.getCtmmnyInfos(res.data);
        },

    },
    created() {
        this.getCtmmnyCdAndNm().then(
            ()=>{
                this.fetchData();
            }
        );
    },
    watch:{
        dialog:function(newVal){
            if(newVal==false){
                this.$emit('closeDialog');
            }
        }
    }
};
</script>

<style scoped>
.v-data-table > .v-data-table__wrapper > table > tbody > tr  {
    border: solid 0px;
    font-weight: bold;
}
.v-data-table > .v-data-table__wrapper > table > tbody > tr > td{
    padding-top:10px;
    padding-bottom:10px;
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
    width: 100%;
    border-bottom: solid 0.1px rgba(128, 128, 128, 0.082);
    border-top: solid 0.1px rgba(128, 128, 128, 0.219);
}
select {
    width: 100%;
    height: 44px;
    border: solid rgba(53, 53, 53, 0.857) 1px;
    background-color: white;
    border-radius: 5%;
}

.datetime {
    border-bottom: dashed 1px black;
    width: 170px;
    font-weight: bold;
    font-size: 17px;
}
</style>
