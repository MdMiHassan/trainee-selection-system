import jwt_decode from "jwt-decode";

function getStoredToken() {
    return localStorage.getItem('tssaccesstoken');
}

function storeToken(token) {
    localStorage.setItem('tssaccesstoken', token);
}
function removeSortedToken() {
    localStorage.removeItem('tssaccesstoken');
}

function decodeToken(token) {
    try {
        return jwt_decode(token);
    } catch (error) {
        console.error('Error decoding token:', error);
        return null;
    }
}

export { storeToken, getStoredToken, removeSortedToken, decodeToken };
