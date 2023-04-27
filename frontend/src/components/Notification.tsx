import closeIcon from "../assets/closeIcon.svg";
interface NotificationProps {
    show: boolean;
    message: string;
    type: string;
    handleClose: () => void;
}

function Notification({show, message, type, handleClose}: NotificationProps) {

    return (
        <>
            <div className={type.concat(" notification").concat(show ? " active" : "")}>
                <span className="ml-05">{message}</span>
                <img alt="Close icon" className="small-icon icon" src={closeIcon} onClick={handleClose}/>
            </div>
        </>
    );
}

export default Notification;
