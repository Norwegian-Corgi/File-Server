import profileIcon from "../assets/profileIcon.svg";
import logoutIcon from "../assets/logoutIcon.svg";
import "../styles/navigation-bar.css";
import {store} from "../store";
import {bindActionCreators} from "redux";
import {clearUser} from "../store/actionCreators/UserActionCreator";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";

interface NavigationBarProps {
    showRegistrationDownload?: Function
}

function NavigationBar({showRegistrationDownload}: NavigationBarProps) {

    const history = useNavigate();
    const dispatch = useDispatch();
    const logout = bindActionCreators(clearUser, dispatch);

    const [isMobile, setIsMobile] = useState<boolean>(false);

    useEffect(() => {
        const mediaQuery = window.matchMedia('(max-width: 768px)');
        setIsMobile(mediaQuery.matches);

        const handleMediaChange = (e: MediaQueryListEvent) => setIsMobile(e.matches);
        mediaQuery.addEventListener('change', handleMediaChange);

        return () => {
            mediaQuery.removeEventListener('change', handleMediaChange);
        };
    }, []);

    function handleLogout() {
        logout();
        history("/");
    }
    return (
        <div className="nav-bar">
            <span className="nav-bar-title">File Server</span>
            { store.getState().userUuid &&
                <div className="nav-bar-icons">
                    {/*
                    No user management is available on mobile devices
                    */}
                    {!isMobile && <img className="mr-1 icon" alt="Profile icon" src={profileIcon} onClick={() => (showRegistrationDownload as Function)()}/>}
                    <img className="icon" alt="Logout icon" src={logoutIcon} onClick={handleLogout}/>
                </div>
            }
        </div>
    );
}

export default NavigationBar;
