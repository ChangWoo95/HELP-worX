<template>
    <v-app>
        <router-view></router-view>
    </v-app>
</template>

<script>
export default {
    name: "App",
    components: {},
    data: function() {
        return {
            isLogined: false,
        };
    },
    created: function() {
        if (this.$store.state.login.accessToken == null) {
            localStorage.removeItem("token");
        }
    },
};
</script>
