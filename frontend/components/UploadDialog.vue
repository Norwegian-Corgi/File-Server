<template>
  <div>
    <el-dialog v-model="alwaysOpen" @close="close">
      <el-upload
        v-model:file-list="uploadRef"
        drag
        :auto-upload="false"
        :multiple="false"
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          Drop file here or <em>click to upload</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">File with a size less than 1000MB</div>
        </template>
        <el-button @click="submitUpload">Upload</el-button>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { UploadFilled } from "@element-plus/icons-vue";
import type { UploadRawFile, UploadUserFile } from "element-plus";

const alwaysOpen = ref<boolean>(true);
const emits = defineEmits(["close", "upload"]);
const close = () => {
  emits("close");
};
const upload = (files: Array<UploadRawFile>) => {
  emits("upload", files);
};

const uploadRef = ref<UploadUserFile[]>();

const submitUpload = () => {
  if (uploadRef.value) {
    const files: Array<UploadRawFile> = [];
    for (const file of uploadRef.value) {
      if (file.raw) files.push(file.raw);
    }
    uploadRef.value = undefined;
    upload(files);
  }
};
</script>

<style scoped></style>
