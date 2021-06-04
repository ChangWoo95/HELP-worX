<template>
    <Viewer v-if="content != null" :initialValue="content" style=" padding:10px" />
</template>

<script>
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";

export default {
  components: {
    Viewer
  },
  props:['content'],
  data() {
    return {
    }
  },
  mounted() {
    // 어디선가 저장된 데이터를 호출
    fetch("주소").then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw response;
      }
    }).then(data => {
    this.content = data;
    });
  }
};
</script>
