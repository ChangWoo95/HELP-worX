<template>
    <v-toolbar fixed dense class="grey lighten-3" :dark="false">
        <v-app-bar-nav-icon @click="emitevent"></v-app-bar-nav-icon>
        <v-toolbar-title>{{ propsdata }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-text-field v-show="isSearchBarOpen" v-model="message" outlined clearable dense rounded style="padding-top:30; padding-right:30; width:80px;"></v-text-field>

        <v-btn icon="icon" @click="isSearchBarOpen = !isSearchBarOpen">
            <v-icon dense="dense">mdi-magnify</v-icon>
        </v-btn>
        <v-btn icon="icon" @click="goToSrReg">
            <v-icon>mdi-plus</v-icon>
        </v-btn>

        <DropDown :userData="userData"></DropDown>
    </v-toolbar>
</template>

<script>
import DropDown from "./DropDown";

export default {
    props: ["propsdata", "userData"],
    components: {
        DropDown: DropDown,
    },
    data: () => ({
        isSearchBarOpen: false,
        message: "",
    }),
    methods: {
        goToSrReg: function() {
            if (confirm("SR을 등록 하시겠습니까??") == true) {
                //확인
                this.$router.push("/sr/main");
            } else {
                //취소
                return;
            }
            this.$emit("goRoute", "/srs/reg");
        },
        emitevent: function() {
            this.$emit("toggleSideMenu");
        },
    },
};
</script>

<style scoped>
/* .v-card.v-sheet.theme--light{
    position: fixed;
    bottom: 0;
} */
</style>
