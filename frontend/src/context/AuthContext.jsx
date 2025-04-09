import {jwtDecode} from "jwt-decode";
import {createContext, useEffect, useState} from 'react';

const AuthContext = createContext({});
const ACCESS_TOKEN = "tss-access-token";

function AuthProvider(props) {
    const [initializing, setInitializing] = useState(true);
    const [accessToken, setAccessToken] = useState(window.localStorage.getItem(ACCESS_TOKEN));
    const [userRoles, setUserRoles] = useState([]);
    const [loginToken, setLoginTokenState] = useState(null);

    const getLoginToken = () => {
        return loginToken;
    }

    const setLoginToken = (token) => {
        setLoginTokenState(token);
    }

    const removeLoginToken = () => {
        setLoginToken(null);
    }

    const getAccessRoles = () => {
        return userRoles;
    }


    const getAccessToken = () => {
        return accessToken;
    }

    const persistAccessToken = (newToken) => {
        window.localStorage.setItem(ACCESS_TOKEN, newToken);
    };


    const isJWTTokenExpired = (token) => {
        const currentTime = Date.now() / 1000;
        if (!token) {
            return true;
        }
        const jwtPayload = jwtDecode(token);
        return (jwtPayload?.exp < currentTime);
    }

    const isAccessTokenExpired = () => {
        return isJWTTokenExpired(accessToken);
    }

    const logout = () => {
        window.localStorage.removeItem(ACCESS_TOKEN);
        setAccessToken('');
        setUserRoles([]);
        removeLoginToken();
    }

    const login = (accessToken) => {
        if (isJWTTokenExpired(accessToken)) {
            throw new Error("Authentication failed");
        }
        setAccessToken(accessToken);
        persistAccessToken(accessToken);
        validateAccessRoles(accessToken);
        setAccessToken(accessToken);
    }

    const validateAccessRoles = (accessToken) => {
        const jwtPayload = jwtDecode(accessToken);
        const userRoles = jwtPayload.roles;
        if (Array.isArray(userRoles)) {
            console.log(userRoles)
            setUserRoles(userRoles);
        } else {
            logout();
        }
    }

    const isAuthenticated = () => {
        return !!(accessToken && !isAccessTokenExpired());
    }

    const hasAuthority = (allowedRole) => {
        return getAccessRoles().includes(allowedRole);
    };

    useEffect(() => {
        if (accessToken && !isJWTTokenExpired(accessToken)) {
            validateAccessRoles(accessToken)
        }
        setInitializing(false);
    }, [accessToken]);

    return (
        <AuthContext.Provider
            value={{
                setLoginToken,
                getLoginToken,
                login,
                logout,
                removeLoginToken,
                getAccessRoles,
                getAccessToken,
                token: getAccessToken(),
                isAccessTokenExpired,
                isAuthenticated,
                hasAuthority
            }}
        >
            {!initializing ? props.children : <></>}
        </AuthContext.Provider>
    );
}

export {AuthProvider, AuthContext}