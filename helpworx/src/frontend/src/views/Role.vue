<template>
    <v-container>
        <v-layout>
            <v-flex>
                <v-card style="border: solid black 1px; margin: 10px">
                    <v-card-text style="font-weight: bold; text-align: left" class="blue lighten-5">
                        권한 선택
                    </v-card-text>
                    <Spinner v-if="!isLoading"></Spinner>

                    <v-card-text v-else style="font-weight: bold; text-align: left">
                        <v-row v-for="item in authList" :key="item">
                            <v-checkbox v-model="selectedRole" :label="item" :value="item" color="grey lighten-1" style="font-weight: bold"></v-checkbox>
                        </v-row>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>

import Spinner from "@/components/util/Spinner";

export default {
    components: {
        Spinner: Spinner,
    },
    data() {
        return {
            authList: [],
            selectedRole: "",
            isLoading: false,
        };
    },
    watch: {
        selectedRole: function() {
            this.$store.commit("SELECTEDROLE", this.selectedRole);
            if (localStorage.getItem("selectedRole") === "ROLE_USER") this.$router.push("/user");
            else if (localStorage.getItem("selectedRole") === "ROLE_ADMIN") this.$router.push("/admin");
            else if (localStorage.getItem("selectedRole") === "ROLE_OPERATION") this.$router.push("/oper");
        },
        getRoles(data) {
            this.authList = data;
            this.isLoading = true;
        },
    },

    computed: {
        getRoles() {
            return this.$store.getters.getRoles;
        },
    },
    created() {
        this.$store.dispatch("GETMYROLE");
    },
};
</script>

<style></style>
