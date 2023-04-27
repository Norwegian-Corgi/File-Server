import {IUserDetails} from "../../types/IUserDetails";
import {UserActions} from "../actions/UserActions";
import {UserActionTypes} from "../actionTypes/UserActionTypes";

const initialState: IUserDetails = {
    userUuid: "",
    token: ""
}

export const userReducer = (state: IUserDetails = initialState, action: UserActions) => {
    switch (action.type) {
        case UserActionTypes.SET_USER:
            return action.payload;
        case UserActionTypes.CLEAR_USER:
            return initialState;
        default:
            return state;
    }
}
