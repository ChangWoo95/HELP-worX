<template>
    <v-row dense class="mt-2 ml-4">
        <search-bar :propsData="page"></search-bar>
        <chat-table @pageChange="pageChange" :userData="this.userData"></chat-table>
    </v-row>
</template>

<script>
import SearchBar from "../components/chat-list/SearchBar.vue";
import ChatTable from "../components/chat-list/ChatTable.vue";

export default {
    props: ["userData"],
    name: "ChatList",
    components: {
        SearchBar,
        ChatTable,
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
