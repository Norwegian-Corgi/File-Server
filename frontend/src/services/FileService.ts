import axios, {AxiosInstance} from "axios";
import {IFile} from "../types/IFile";

export default class FileService {
    axiosInstance: AxiosInstance;

    constructor(token: string) {
        this.axiosInstance = axios.create({
            baseURL: "http://".concat(process.env.REACT_APP_SERVER_IP as string).concat(":8081/filez/"),
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
    }

    getFiles(id: string): Promise<void | IFile[]> {
        return this.axiosInstance.get<IFile[]>(id)
            .then(res => res.data)
            .catch(error => console.error(error));
    }

    upload (id: string, dataFile: FormData) {
        return this.axiosInstance.post("", dataFile, {
            params: {
                uuid: id
            },
            headers: {
                "Content-Type": "multipart/form-data",
            }
        })
            .then((response) => {
                return response
            })
            .catch((error) => {
                Promise.reject(error)
            })
    }

    downloadFile(id: string, filename: string) {
        return this.axiosInstance.get(id, {
            responseType: "blob"
        })
            .then((res) => {
                const url = window.URL.createObjectURL(new Blob([res.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', filename);
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            })
    }

    deleteFile(id: string): Promise<string> {
        return this.axiosInstance.delete(id)
            .then((res) => {
                console.log(res);
                return res.data;
            })
            .catch(error => console.error(error));
    }
}
