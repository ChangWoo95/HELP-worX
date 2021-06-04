<template>
    <v-row class="mt-2 ml-4" dense>
        <search-bar :propsData="page"></search-bar>
        <sr-table @pageChange="pageChange"></sr-table>
    </v-row>
</template>

<script>
import SearchBar from "../components/admin-list/SearchBar.vue";
import SrTable from "../components/admin-list/SrTable.vue";

export default {
    name: "AdminCtmmnysList",
    components: {
        SearchBar,
        SrTable,
    },
    data() {
        return {
            page: 0,
        };
    },
    methods: {
        pageChange: function(page) {
            this.page = page;
        },
    },
};
</script>

<style></style>
