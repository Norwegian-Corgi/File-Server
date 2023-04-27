import {IFileInfo} from "../types/IFileInfo";
import "../styles/table.css";
import FileService from "../services/FileService";
import {store} from "../store";
import trashCanIcon from "../assets/trashCanIcon.svg";
import downloadIcon from "../assets/downloadIcon.svg";

interface TableProps {
    headers: string[];
    files: IFileInfo[];
    updateView: Function;
}

function FileTable(props: TableProps) {

    const fileService = new FileService(store.getState().token);

    function downloadFile(id: string, filename: string) {
        fileService.downloadFile('download/'.concat(id), filename).then(() => props.updateView());
    }

    function deleteFile(id: string) {
        fileService.deleteFile(id).then(() => props.updateView());
    }

    return (
        <table className="table">
            <thead className="table-header">
            <tr className="table-row">
                {props.headers.map((header: string) => (<th key={header} className={header === 'Name' ? "name-column" : ""}>{header}</th>))}
            </tr>
            </thead>
            <tbody>
            {props.files.map((file: IFileInfo) =>
                (<tr className="table-row" key={file.id}>
                    <td className="name-column">{file.name}</td>
                    <td>{file.downloads}</td>
                    <td>
                        <img className="icon mr-1" alt="Download icon" src={downloadIcon} onClick={() => downloadFile(file.id, file.name)}/>
                        <img className="icon" alt="Trash can icon" src={trashCanIcon} onClick={() => deleteFile(file.id)}/>
                    </td>
                </tr>)
            )}
            </tbody>
        </table>
    );
}

export default FileTable;
