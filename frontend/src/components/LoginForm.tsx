import LabeledTextInput from "./LabeledTextInput";
import React, {useEffect, useState} from "react";
import "../styles/login.css";
import UserService from "../services/UserService";
import {clearUser, setUser} from "../store/actionCreators/UserActionCreator";
import {bindActionCreators} from "redux";
import {useDispatch} from "react-redux";
import { useNavigate } from "react-router-dom";
import Notification from "./Notification";
import {useNotification} from "../hooks/useNotification";

function LoginForm() {

    const userService = new UserService();
    const history = useNavigate();

    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [notification, setNotification] = useNotification();
    const dispatch = useDispatch();
    const login = bindActionCreators(setUser, dispatch);
    const logout = bindActionCreators(clearUser, dispatch);

    useEffect(() => {
        logout();
    }, []);

    function updateEmail(email: string) {
        setEmail(email);
    }

    function updatePassword(password: string) {
        setPassword(password);
    }

    function handleLogin() {
        userService.authenticateUser({email: email, password: password})
            .then((response) => {
                login(response);
                history("/files");
            })
            .catch(() => {
                setNotification.type("error");
                setNotification.message("Login failed!");
                setNotification.show();
            });
    }

    return  (
        <div className="login-form">
            <LabeledTextInput id="email" label="Email" type="email" handleChange={updateEmail}/>
            <LabeledTextInput id="password" label="Password" type="password" handleChange={updatePassword}/>
            <button className="button" onClick={handleLogin}>Log in</button>
            <Notification show={notification.show} message={notification.message} type={notification.type} handleClose={setNotification.hide}/>
        </div>
    );
}

export default LoginForm;

