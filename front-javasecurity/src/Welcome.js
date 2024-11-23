import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Welcome() {
    const navigate = useNavigate();
    const accessToken = localStorage.getItem('accessToken');

    useEffect(() => {
        if (!accessToken) {
            alert('로그인이 필요합니다.');
            navigate('/');
        }
    }, [accessToken, navigate]);

    const logout = () => {
        fetch('http://localhost:8080/api/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${accessToken}`,
            },
            credentials: 'include',
        })
            .then((res) => {
                if (res.ok) {
                    alert('로그아웃 성공!');
                    localStorage.removeItem('accessToken');
                    navigate('/');
                } else {
                    alert('로그아웃 실패');
                }
            })
            .catch((error) => alert(`에러 발생: ${error}`));
    };

    return (
        <div>
            <h1>환영합니다!</h1>
            <button onClick={logout}>LOGOUT</button>
        </div>
    );
}

export default Welcome;
