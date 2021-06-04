<template>
  <v-row justify="center">
    
        <SockConnectSendSubScribe :userData="this.userData"></SockConnectSendSubScribe>
  </v-row>
</template>

<script>
import SockConnectSendSubScribe from '../components/chat/SockConnectSendSubScribe'
  export default {
    props: {
      userData: {
          type: Object,
          required: true
      }

    },
    data () {
      return {
        dialogm1: '',
        dialog: false,
        me:'me',
        send:'system',
      }
    },
    components: {
     SockConnectSendSubScribe:SockConnectSendSubScribe,
  },
  }
</script>
<style scoped>

  div.v-card.v-sheet.theme--light.elevation-0{
    position: sticky;
  }
</style>