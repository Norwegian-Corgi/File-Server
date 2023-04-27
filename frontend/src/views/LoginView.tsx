import LoginForm from "../components/LoginForm";
import NavigationBar from "../components/NavigationBar";
import React from "react";
import "../styles/login.css";

function LoginPage() {

    return (
        <>
            <NavigationBar />
            <div className="login-container">
                <LoginForm></LoginForm>
            </div>
        </>
    );
}

export default LoginPage;
