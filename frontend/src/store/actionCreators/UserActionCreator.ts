import {IUserDetails} from "../../types/IUserDetails";
import {Dispatch} from "redux";
import {UserActions} from "../actions/UserActions";
import {UserActionTypes} from "../actionTypes/UserActionTypes";

export const setUser = (userDetails: IUserDetails) => {
    return (dispatch: Dispatch<UserActions>) => dispatch({
       type: UserActionTypes.SET_USER,
       payload: userDetails
    });
}

export const clearUser = () => {
    return (dispatch: Dispatch<UserActions>) => dispatch({
        type: UserActionTypes.CLEAR_USER
    });
}
