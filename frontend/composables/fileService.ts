import type { UserFile } from "~/models/file";

export const useFileService = () => {
  const getBaseUrl = () => {
    const runtimeConfig = useRuntimeConfig();
    return "http://" + runtimeConfig.public.serverIp + ":8081/api/v1/file";
  };

  const getToken = async () => {
    const { getAccessToken } = useKeycloak();
    return "Bearer " + (await getAccessToken());
  };

  const deleteFileById = async (id: string) => {
    await fetch(getBaseUrl() + `/${id}`, {
      headers: {
        Authorization: await getToken(),
      },
      method: "DELETE",
    });
  };

  const getResourceByFileId = async (id: string) => {
    const response = await fetch(getBaseUrl() + `/download/${id}`, {
      headers: {
        Authorization: await getToken(),
      },
    });
    return await response.arrayBuffer();
  };

  const getUserFiles = async (): Promise<Array<UserFile>> => {
    const response = await fetch(getBaseUrl(), {
      headers: {
        Authorization: await getToken(),
      },
    });
    return response.json();
  };

  const uploadFile = async (file: File) => {
    const formData = new FormData();
    formData.append("file", file);
    await fetch(getBaseUrl(), {
      headers: {
        Authorization: await getToken(),
      },
      method: "POST",
      body: formData,
    });
  };

  return { deleteFileById, getUserFiles, getResourceByFileId, uploadFile };
};
