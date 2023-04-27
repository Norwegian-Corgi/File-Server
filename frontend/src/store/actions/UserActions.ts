import {UserActionTypes} from "../actionTypes/UserActionTypes";
import {IUserDetails} from "../../types/IUserDetails";

interface SetUser {
    type: UserActionTypes.SET_USER;
    payload: IUserDetails;
}

interface ClearUser {
    type: UserActionTypes.CLEAR_USER
}

export type UserActions = SetUser | ClearUser;
