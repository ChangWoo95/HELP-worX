<template>
    <div>
        <Editor ref="toastuiEditor" initialEditType="wysiwyg"/>
        <br>
        <v-card
            class="d-flex flex-row-reverse"
            elevation="0">
              <v-btn @click="createAction">저장하기</v-btn>
              <v-btn @click="cancel">취소하기</v-btn>
        </v-card>
    </div>
</template>

<script>
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";

export default {
  components: {
    Editor
  },
  methods: {
    cancel(){
      this.$emit('cancel');
    },
    createAction() {
      var content = this.$refs.toastuiEditor.invoke("getMarkdown");
      this.$emit('submitEditor',content);
    }
  }
};
</script>
<style scoped>
 .d-flex.flex-row-reverse{
   background-color: rgba(216, 216, 216, 0);
 }
</style>