import FileTable from "../components/FileTable";
import {IFile} from "../types/IFile";
import React, {ChangeEvent, useEffect, useState} from "react";
import FileService from "../services/FileService";
import {IFileInfo} from "../types/IFileInfo";
import {store} from "../store";
import NavigationBar from "../components/NavigationBar";
import {useNavigate} from "react-router-dom";
import {useNotification} from "../hooks/useNotification";
import Notification from "../components/Notification";
import RegistrationDialog from "../components/RegistrationDialog";

function FilesView() {
    const history = useNavigate();
    const fileService = new FileService(store.getState().token);
    const headers: string[] = ['Name', 'Downloads', ''];

    const [files, setFiles] = useState<IFileInfo[]>([]);
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [notification, setNotification] = useNotification();

    function getFiles() {
        const uuid = store.getState().userUuid;
        if (uuid && uuid.length > 0) {
            fileService.getFiles(store.getState().userUuid).then(response => {
                if (response) {
                    const fileInfo = response.map((file: IFile) => {
                        return {
                            id: file.id,
                            name: file.name,
                            downloads: file.numberOfDownloads
                        }
                    })
                    setFiles(fileInfo)
                }
            });
        } else {
            history("/");
        }
    }

    function showError(message: string) {
        setNotification.message(message);
        setNotification.type("error");
        setNotification.show();
    }

    function showSuccess() {
        setNotification.message("User registered successfully");
        setNotification.type("success");
        setNotification.show();
        setIsOpen(false);
    }

    const selectFile = (event: ChangeEvent<HTMLInputElement>) => {
        const {files} = event.target;
        const selectedFiles = files as FileList;
        const formData = new FormData();
        formData.append("file", selectedFiles[0]);
        fileService.upload(store.getState().userUuid, formData)
            .then(() => getFiles());
    };

    function upload() {
        (document.getElementById("fileSelector") as HTMLInputElement).click();
    }

    // eslint-disable-next-line
    useEffect(() => getFiles(), []);

    return (
        <div>
            <NavigationBar showRegistrationDownload={() => setIsOpen(true)}/>
            <RegistrationDialog isOpen={isOpen} handleClose={() => setIsOpen(false)} handleSuccess={showSuccess} handleError={showError} />
            <div className="flex-end m-1">
                <div style={{display: "none"}}>
                    <input id="fileSelector" type="file" onChange={selectFile}/>
                </div>
                <button className="button" onClick={upload}>Upload</button>
            </div>
            <FileTable headers={headers} files={files} updateView={getFiles}/>
            <Notification show={notification.show} message={notification.message} type={notification.type} handleClose={setNotification.hide} />
        </div>
    );
}

export default FilesView;
