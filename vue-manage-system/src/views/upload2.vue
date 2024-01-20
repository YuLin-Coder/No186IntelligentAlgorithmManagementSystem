<template>
  <div>
    选择多个文件：<input @change="getFiles($event)" name="files" type="file" multiple="multiple" /><br />
    选择文件夹，遍历该文件夹下所有文件：<input @change="getFiles($event)" name="files" type="file" webkitdirectory mozdirectory /><br />
    <button @click="upload" type="submit">上传</button>
  </div>
</template>

<script>
import request from '../utils/request';
const fetchupload = formData => {
  return request({
    url: 'http://127.0.0.1:8080/upload',
    method: 'post',
    params: formData,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  });
};
export default {
  name: 'UploadFileVue',
  data() {
    return {
      files: []
    }
  },
  methods: {
    getFiles: function(event) {
      var files = event.target.files;
      for (var i = 0; i < files.length; i++) {
        this.files.push(files[i]);
      }
    },
    upload: function() {
      var formData = new FormData();
      for (var i = 0; i < this.files.length; i++) {
        formData.append('files', this.files[i]);
      }
      fetchupload(formData);
      // this.$axios.post("http://127.0.0.1:8080/upload", formData, {
      //   headers: {
      //     'Content-Type': 'multipart/form-data'
      //   }
      // }).then(function(response) {
      //   // 请求成功
      // }, function(err) {
      //
      // })
    }
  }
};
</script>