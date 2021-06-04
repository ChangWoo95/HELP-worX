<template>
    <v-row dense class="mt-2 ml-4">
        <search-bar :propsData="page"></search-bar>
        <sr-table @pageChange="pageChange"></sr-table>
    </v-row>
</template>

<script>
import SearchBar from "../components/admin-user/SearchBar.vue";
import SrTable from "../components/admin-user/SrTable.vue";

export default {
    name: "AdminUsersList",
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
