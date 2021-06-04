<template>
    <v-row>
        <search-bar :propsData="page"></search-bar>
        <sr-table @pageChange="pageChange"></sr-table>
    </v-row>
</template>

<script>
import SearchBar from "../components/rcpt-treat-list/SearchBar";
import SrTable from "../components/rcpt-treat-list/RcptTreatTable.vue";

export default {
    name: "SrList",
    components: {
        SearchBar,
        SrTable,
    },

    data: () => ({
        page: 0,
    }),
    methods: {
        pageChange: function(page) {
            this.page = page;
        },
    },
};
</script>

<style></style>
