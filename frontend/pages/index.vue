<template>
  <div>
    <NavigationBar />
    <div class="button-container">
      <el-button @click="openUploadDialog">Upload</el-button>
    </div>
    <el-table
      class="m-1"
      :data="files"
      stripe
      style="width: 98dvw"
      empty-text="Wow, such empty!"
    >
      <el-table-column prop="name" label="Name" />
      <el-table-column prop="numberOfDownloads" label="Downloads" />
      <el-table-column width="180">
        <template #default="scope">
          <el-icon class="clickable mr-1" @click="download(scope.$index)"
            ><Download
          /></el-icon>
          <el-icon class="clickable" @click="deleteFile(scope.$index)"
            ><Delete
          /></el-icon>
        </template>
      </el-table-column>
    </el-table>
    <UploadDialog
      v-if="openDialog"
      @upload="upload"
      @close="closeUploadDialog"
    />
  </div>
</template>

<script setup lang="ts">
import { Delete, Download } from "@element-plus/icons-vue";
import type { UploadRawFile } from "element-plus";
import type { UserFile } from "~/models/file";

const { deleteFileById, getResourceByFileId, getUserFiles, uploadFile } =
  useFileService();
const files = ref<Array<UserFile>>([]);
const openDialog = ref<boolean>(false);

const getFiles = async () => {
  files.value = await getUserFiles();
};

const openUploadDialog = () => {
  openDialog.value = true;
};
const closeUploadDialog = () => {
  openDialog.value = false;
};
const upload = async (files: Array<UploadRawFile>) => {
  closeUploadDialog();
  for (const file of files) {
    await uploadFile(file);
  }
  await getFiles();
};

const download = async (index: number) => {
  const { id, name } = files.value[index];
  const res = await getResourceByFileId(id);
  const url = window.URL.createObjectURL(new Blob([res]));
  const link = document.createElement("a");
  link.href = url;
  link.setAttribute("download", name);
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

const deleteFile = async (index: number) => {
  const { id } = files.value[index];
  await deleteFileById(id);
  await getFiles();
};

onMounted(async () => await getFiles());
</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: end;
  margin: 2rem;
}
</style>
