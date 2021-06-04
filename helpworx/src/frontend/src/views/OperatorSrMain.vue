<template>
    <v-flex class="box mt-5">
        <v-row>
            <v-col>
                <v-row dense class="mb-2" style="font-weight: bold">미완료 SR</v-row>
                <InCompleteOperSrs></InCompleteOperSrs>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <v-row dense class="mb-2" style="font-weight: bold">진행중 문의</v-row>
                <ProceedingOperSrs></ProceedingOperSrs>
            </v-col>
        </v-row>
    </v-flex>
</template>

<script>
import InCompleteOperSrs from "../components/sr-main/InCompleteOperSrs.vue";
import ProceedingOperSrs from "../components/sr-main/ProceedingOperSrs.vue";

export default {
    name: "OperatorSrMain",
    components: {
        InCompleteOperSrs: InCompleteOperSrs,
        ProceedingOperSrs: ProceedingOperSrs,
    },
};
</script>

<style>
.box {
    border: solid 1px;
    text-align: center;
    width: 80%;
}
</style>
