import { useContext, useEffect } from 'react';
import { AuthContext } from '../context/AuthContext';
import { getStoredToken,decodeToken } from '../utils/auth';

function Initialization() {
  const { updateRole, updateToken } = useContext(AuthContext);
  useEffect(() => {
    const storedToken = getStoredToken();
    if (storedToken) {
      const role = decodeToken(storedToken).role[0];
      updateRole(role);
      updateToken(storedToken);
    }
  }, [updateRole, updateToken]);

  return null;
}

export default Initialization;
