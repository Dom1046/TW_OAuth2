import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Login = () => {
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault(); // 페이지 새로고침 방지
        setError('');
        setSuccess('');

        if (!userId || !password) {
            setError('모든 필드를 입력해주세요.');
            return;
        }

        try {
            const response = await axios.post('http://localhost:8080/api/login', {
                userId,
                password,
            });

            if (response.status === 200) {
                setSuccess('로그인 성공!');
                console.log('로그인 성공:', response.data); // 성공 데이터 출력
            }
        } catch (err) {
            setError('로그인 실패. 다시 시도해주세요.');
            console.error(err);
        }
    };

    const goToOAuth = () => {
        navigate('/oauth'); // 소셜 로그인 페이지로 이동
    };

    return (
        <div style={{ maxWidth: '400px', margin: '0 auto', padding: '20px', border: '1px solid #ccc', borderRadius: '8px' }}>
            <h2>로그인</h2>
            <form onSubmit={handleLogin}>
                <div style={{ marginBottom: '15px' }}>
                    <label htmlFor="userId">아이디:</label>
                    <input
                        type="text"
                        id="userId"
                        value={userId}
                        onChange={(e) => setUserId(e.target.value)}
                        style={{ width: '100%', padding: '8px', marginTop: '5px' }}
                    />
                </div>
                <div style={{ marginBottom: '15px' }}>
                    <label htmlFor="password">비밀번호:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={{ width: '100%', padding: '8px', marginTop: '5px' }}
                    />
                </div>
                {error && <p style={{ color: 'red' }}>{error}</p>}
                {success && <p style={{ color: 'green' }}>{success}</p>}
                <button type="submit" style={{ padding: '10px 20px', backgroundColor: '#007BFF', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
                    로그인
                </button>
            </form>
            <div style={{ marginTop: '20px', textAlign: 'center' }}>
                <p>또는</p>
                <button
                    onClick={goToOAuth}
                    style={{
                        padding: '10px 20px',
                        backgroundColor: '#4267B2',
                        color: '#fff',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer',
                        fontSize: '16px',
                    }}
                >
                    소셜 로그인으로 이동
                </button>
            </div>
        </div>
    );
};

export default Login;
