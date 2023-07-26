import React, { createContext, useState, useEffect } from 'react';
import { decodeToken, getStoredToken, removeSortedToken, storeToken } from '../utils/auth';
const AuthContext = createContext();

function AuthProvider({ children }) {
    const storedToken=getStoredToken();
    let storedRole=null;
    if(storedToken != null){
        storedRole=decodeToken(storedToken)?.role[0];
    }
    console.log("after  "+storedRole+"   "+storedToken);
    const [role, setRole] = useState(storedRole);
    const [token, setToken] = useState(storedToken);

    const updateRole = (newRole) => {
        setRole(newRole);
    };

    const updateToken = (newToken) => {
        setToken(newToken);
        storeToken(newToken);
    };

    useEffect(() => {
        checkTokenExpiry();
        return () => {};
    }, []);

    const checkTokenExpiry = () => {
        if (token) {
            const decodedToken = decodeToken(token);
            const currentTime = Date.now() / 1000;

            if (decodedToken?.exp < currentTime) {
                setToken(null);
                setRole(null);
                removeSortedToken();
            }
        }
    };

    const authContextValue = {
        role,
        updateRole,
        token,
        updateToken,
    };

    return (
        <AuthContext.Provider value={authContextValue}>
            {children}
        </AuthContext.Provider>
    );
};

export { AuthProvider, AuthContext }