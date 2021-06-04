import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router/router";
import store from "./store/store";
import "chart.js";
import "hchs-vue-charts";
import moment from "moment";
import VueMomentJS from "vue-momentjs";
import Datetime from "vue-datetime";

Vue.config.productionTip = false;
Vue.use(window.VueCharts);
Vue.use(VueMomentJS, moment);
Vue.use(Datetime);
Vue.use(router);

new Vue({
    vuetify,
    router,
    render: (h) => h(App),
    store,
}).$mount("#app");
