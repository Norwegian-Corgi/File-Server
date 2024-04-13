<template>
  <div>
    <NavigationBar />
    <div class="flex justify-end m-8">
      <UInput
        v-model="filename"
        class="mr-4"
        type="file"
        size="sm"
        @change="setFile"
      />
      <UButton
        color="primary"
        variant="solid"
        @click="upload"
        :disabled="disableUpload"
        >Upload</UButton
      >
    </div>
    <div class="flex px-3 py-3.5 border-b border-gray-200 dark:border-gray-700">
      <UInput
        v-model="query"
        placeholder="Filter files..."
        trailing-icon="i-heroicons-magnifying-glass-20-solid"
      />
    </div>
    <UTable
      :columns="columns"
      :rows="filteredFiles"
      :empty-state="{
        icon: 'i-heroicons-face-frown',
        label: 'Wow, such empty!',
      }"
    >
      <template #actions-data="{ row }">
        <UDropdown :items="actionItems(row)">
          <UButton
            color="gray"
            variant="ghost"
            icon="i-heroicons-ellipsis-vertical-20-solid"
            :ui="{
              td: {
                base: 'text-center',
              },
            }"
          />
        </UDropdown> </template
    ></UTable>
  </div>
</template>

<script setup lang="ts">
import type { UserFile, UserFileWithClass } from "~/models";

const { deleteFileById, getResourceByFileId, getUserFiles, uploadFile } =
  useFileService();
const files = ref<Array<UserFileWithClass>>([]);
const query = ref<string>("");
const fileRef = ref<File>();
const filename = ref<string>("");
const disableUpload = ref<boolean>(true);
const columns = [
  {
    key: "name",
    label: "Name",
    sortable: true,
    class: "text-center w-1/2",
  },
  {
    key: "numberOfDownloads",
    label: "Downloads",
    class: "text-center w-1/4",
  },
  {
    key: "actions",
    class: "text-center w-1/4",
  },
];
const actionItems = (row: UserFile) => [
  [
    {
      label: "Download",
      icon: "i-heroicons-arrow-down-tray-20-solid",
      click: () => download(row.id),
    },
    {
      label: "Delete",
      icon: "i-heroicons-trash-20-solid",
      click: () => deleteFile(row.id),
    },
  ],
];

const getFiles = async () => {
  const filez = await getUserFiles();
  files.value = [];
  mapClass(filez);
};

const mapClass = (filez: Array<UserFile>) => {
  for (const file of filez) {
    const temporaryFile: UserFileWithClass = { ...file, class: "text-center" };
    files.value.push(temporaryFile);
  }
};

const filteredFiles = computed(() => {
  if (!query.value) {
    return files.value;
  }
  return files.value.filter((file) =>
    file.name.toLowerCase().includes(query.value.toLowerCase())
  );
});

const setFile = (fileList: FileList) => {
  fileRef.value = fileList[0];
  disableUpload.value = false;
};
const upload = async () => {
  if (fileRef.value) {
    await uploadFile(fileRef.value);
    fileRef.value = undefined;
    filename.value = "";
    disableUpload.value = true;
    await getFiles();
  }
};

const download = async (id: string) => {
  const name = files.value.filter((file) => file.id === id)[0].name;
  const res = await getResourceByFileId(id);
  const url = window.URL.createObjectURL(new Blob([res]));
  const downloadElement = createDownloadElement(url, name);
  downloadFile(downloadElement);
  await getFiles();
};

const createDownloadElement = (url: string, name: string) => {
  const downloadElement = document.createElement("a");
  downloadElement.href = url;
  downloadElement.setAttribute("download", name);
  return downloadElement;
};

const downloadFile = (downloadElement: HTMLAnchorElement) => {
  document.body.appendChild(downloadElement);
  downloadElement.click();
  document.body.removeChild(downloadElement);
};

const deleteFile = async (id: string) => {
  await deleteFileById(id);
  await getFiles();
};

onMounted(async () => await getFiles());
</script>
