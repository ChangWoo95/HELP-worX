<template>
    <v-container v-if="srData!=null">
        <v-card  elevation="0">
            <v-simple-table class="v-table">
                <template v-slot:default>
                    <thead class="thead" >
                        <tr >
                            <th style="font-size: 20px; color:black;border-bottom:solid 1px;">
                               SR 정보
                            </th>
                            <th style="border-bottom:solid 1px;" >

                            </th>
                            <th style="border-bottom:solid 1px;" >
                                                            
                            </th>
                            <th style="border-bottom:solid 1px;">
                                
                            </th>
                        </tr>
                    </thead>
                    <tbody  class="v-body">
                        <tr>
                            <td>시스템</td>
                            <td>
                                <v-card-text>
                                    {{srData.sysNm}}
                                </v-card-text>
                            </td>
                            <td>업무 구분</td>
                            <td >
                                <v-card-text>
                                    {{srData.jobGb}}
                                </v-card-text>
                            </td>
                        </tr>
                        <tr>
                            <td>요청구분</td>
                            <td>
                                <v-card-text>
                                    {{srData.reqGb}}
                                </v-card-text>
                            </td>
                            <td>요청일자</td>
                            <td>
                                <v-card-text v-if="srData.reqTim!=null">
                                    {{srData.reqTim.split('T')[0]}}
                                </v-card-text>
                            </td>

                        </tr>
                    </tbody>
                </template>
            </v-simple-table>
            <v-simple-table class="v-table">
                <template v-slot:default>
                    <tbody  class="v-body">
                        <tr>
                            <td >제목</td>
                            <td class= "dscr">
                                <v-card-text>
                                    {{srData.sbjct}}
                                </v-card-text>
                            </td>
                            <td>

                        </td>
                        </tr>
                        <tr>
                            <td>내용</td>
                            <td class= "dscr">
                                <p></p>
                                <Viewer ref="toastViewer" v-if="srData!=null" :initialValue="srData.dscr" style=" padding:10px" />
                            </td>
                        </tr>
                    </tbody>
                </template>


            </v-simple-table>
        </v-card>
    </v-container>
</template>

<script>
  import { Viewer } from "@toast-ui/vue-editor";

  export default {
    props:['srData'],
    components: {
        Viewer,
    },
    watch:{
        srData:function(newVal){
            this.$refs.toastViewer.invoke('setMarkdown', newVal.dscr)
        }
    },
    data: function() {
        return{
            reqGbList:['데이터수정','개선','오류','문의'],
            content : '',
            sysOption:-1,
            jobGbOption:-1,
            reqGbOption:-1,

        }
    },

}
</script>

<style scoped>
    th{
        border:solid 0px;
        background-color: rgba(177, 206, 245, 0.815);
    }
    tr:hover {
        background-color: transparent !important;
    }
    select {
        border: solid 1.5px grey;
        padding-top: 5px;
        padding-bottom: 5px;
        padding-left: 20px;
    }

    .v-data-table > .v-data-table__wrapper > table > tbody > tr > td{
        font-weight: bold;
    }
    .v-text-field {
        padding-top:0;
        float:right; width:74.5%;margin-top:-10px; margin-bottom:-20px;
    }
    .v-data-table > .v-data-table__wrapper > table > tbody > tr > td {
        border: solid 0px;
    }
    .v-table {
        width:75%;
        border-bottom : solid 0.1px rgba(128, 128, 128, 0.082);
        border-top : solid 0.1px rgba(128, 128, 128, 0.219);
    }

    .dscr, .sbjct{
        height:100%;
        width: 76.5%;
    }

</style>