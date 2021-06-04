<template>
    <v-row dense class="mt-2 ml-4">
        <search-bar :propsData="page"></search-bar>
        <sr-table @pageChange="pageChange"></sr-table>
    </v-row>
</template>

<script>
import SearchBar from "../components/sr-list/SearchBar.vue";
import SrTable from "../components/sr-list/SrTable.vue";

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

<style>
.mybox {
    border: solid 0px;
    text-align: center;
    padding: 20px;
}
</style>
