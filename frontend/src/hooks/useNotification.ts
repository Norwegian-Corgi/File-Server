import {useState} from "react";

export function useNotification (): [any, any] {
    const [show, setShow] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");
    const [type, setType] = useState<string>("");
    const handleShow = () => {
        setShow(true);
        setTimeout(handleHide, 5000);
    }
    const handleHide = () => {
        setShow(false);
    }
    return [{show, message, type}, {show: handleShow, hide: handleHide, message: setMessage, type: setType}];
}
