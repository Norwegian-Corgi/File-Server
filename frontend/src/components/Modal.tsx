import closeIcon from "../assets/closeIcon.svg";
import "../styles/modal.css";
import {ReactNode} from "react";
interface ModalProps {
    isOpen: boolean;
    heading: string;
    children?: ReactNode;
    onClose: () => void;
}

function Modal({isOpen, heading, children, onClose}: ModalProps) {
    return (
        <>
            {isOpen &&
                <div className="modal">
                    <div className="modal-content">
                        <div id="header" className="modal-header">
                            <span className="heading">{heading}</span>
                            <img alt="Close icon" className="icon mr-1" src={closeIcon} onClick={onClose}/>
                        </div>
                        <div id="body" className="modal-body">
                            {children}
                        </div>
                    </div>
                </div>
            }
        </>
    );
}

export default Modal;
