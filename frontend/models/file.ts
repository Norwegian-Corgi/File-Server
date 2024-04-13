export type UserFile = {
  id: string;
  name: string;
  contentType: string;
  numberOfDownloads: number;
  path: string;
  userEmail: string;
};

export type UserFileWithClass = UserFile & { class: string };
