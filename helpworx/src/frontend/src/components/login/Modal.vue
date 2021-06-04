<template>
  <div class="text-center">
    <v-dialog dense
      persistent
      v-model="dialog"
      width="500"
      @input="close"
      
    >

      <v-card>
        <v-card-text  dense class="headline red--text">
          
        </v-card-text>
        <v-card-text dense class="headline red--text">
          {{propsdata}}
        </v-card-text>
          <v-btn
            color="primary"
            text
            @click="close"
          >
            닫기
          </v-btn>


      </v-card>
    </v-dialog>
  </div>
</template>
<script>
  export default {
    props:['propsdata'],
    watch:{
        propsdata: function(){
            if( this.propsdata!=""){
            this.dialog= true
            }     
            else{
                this.$emit('closeModal')
            }
        },
    },
    
    methods:{
        close: function(){
            this.dialog=false;
            this.propsdata=""
        }
    },
    data () {
      return {
        dialog: false,
      }
    },
  }
</script>
