<template>
    <!-- <v-flex class="mybox mt-4"> -->
    <v-row>
        <SystemStatus></SystemStatus>

        <v-row class="mt-4 ml-3">
            <v-col cols="5">
                <v-row v-if="this.userData.ctmmny != null" dense class="mb-2" style="font-weight: bold">[{{ this.userData.ctmmny.ctmmnyNm }}社] SR (미완료건)</v-row>
                <InCompleteCtmmnySrs></InCompleteCtmmnySrs>
            </v-col>

            <v-col cols="5">
                <v-row dense class="mb-2" style="font-weight: bold">나의 SR (미완료건)</v-row>
                <InCompleteUserSrs></InCompleteUserSrs>
            </v-col>
        </v-row>
    </v-row>
    <!-- </v-flex> -->
</template>

<script>
import SystemStatus from "../components/sr-main/SystemStatus";
import InCompleteUserSrs from "../components/sr-main/InCompleteUserSrs";
import InCompleteCtmmnySrs from "../components/sr-main/InCompleteCtmmnySrs";

export default {
    props: ["userData"],
    name: "UserSrMain",
    components: {
        SystemStatus: SystemStatus,
        InCompleteUserSrs: InCompleteUserSrs,
        InCompleteCtmmnySrs: InCompleteCtmmnySrs,
    },
    data() {
        return {};
    },
};
</script>

<style>
.mybox {
    border: solid 0px;
    text-align: center;
    padding: 50px;
}
</style>
