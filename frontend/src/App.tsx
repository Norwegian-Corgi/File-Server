import React from 'react';
import './App.css';
import LoginView from "./views/LoginView";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Provider} from "react-redux";
import {store, persistor} from "./store";
import FilesView from "./views/FilesView";
import {PersistGate} from "redux-persist/integration/react";

function App() {
    return (
        <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<LoginView />} />
                        <Route path="/files" element={<FilesView />} />
                    </Routes>
                </BrowserRouter>
            </PersistGate>
        </Provider>
  );
}

export default App;
