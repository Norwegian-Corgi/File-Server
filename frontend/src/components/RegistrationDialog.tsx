import Modal from "./Modal";
import React from "react";
import LabeledTextInput from "./LabeledTextInput";
import Dropdown from "./DropDown";
import {useUser} from "../hooks/useUser";
import UserService from "../services/UserService";
import {store} from "../store";

interface RegistrationDialogProps {
    handleClose: () => void;
    handleError: (message: string) => void;
    handleSuccess: () => void;
    isOpen: boolean;
}

function RegistrationDialog({handleClose, handleSuccess, handleError, isOpen}: RegistrationDialogProps) {

    const userService = new UserService();
    const roles = [
        {
            label: "User",
            value: "USER"
        },
        {
            label: "Admin",
            value: "ADMIN"
        }
    ];
    const [user, setUser] = useUser();

    function handleRegistration() {
        userService.registerUser({
            name: user.name,
            email: user.email,
            password: user.password,
            role: user.role,
            requester: store.getState().userUuid
        })
            .then(handleSuccess)
            .catch((e) => {
                handleError(e.message);
            });
    }

    return (
        <>
            <Modal isOpen={isOpen}
                   heading="Register user"
                   children={
                       <>
                           <LabeledTextInput id="name" label="Name" type="text"
                                             handleChange={setUser.name}/>
                           <LabeledTextInput id="email" label="Email" type="email"
                                             handleChange={setUser.email}/>
                           <LabeledTextInput id="password" label="Password" type="password"
                                             handleChange={setUser.password}/>
                           <div className="input-container">
                               <label>Role</label>
                               <Dropdown defaultSelection={roles.find(r => r.value === user.role)} items={roles}
                                         onSelect={(value) => setUser.role(value)}/>
                           </div>
                           <button className="button" onClick={handleRegistration}>Register</button>
                       </>
                   }
                   onClose={handleClose}/>
        </>
    );
}

export default RegistrationDialog;
