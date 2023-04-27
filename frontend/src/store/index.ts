import {applyMiddleware, createStore} from "redux";
import {userReducer} from "./reducers/UserReducer";
import thunk from "redux-thunk";
import { persistStore, persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'

const persistConfig = {
    key: 'root',
    storage,
};

const persistedReducer = persistReducer(persistConfig, userReducer);
const store = createStore(
    persistedReducer,
    // @ts-ignore
    {},
    applyMiddleware(thunk)
);
const persistor = persistStore(store);

export { store, persistor };
