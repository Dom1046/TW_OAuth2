import React from 'react';

const OAuthPage = () => {
    const redirectToGoogle = () => {
        window.location.href = 'http://localhost:8080/api/oauth2/google';
    };

    const redirectToFacebook = () => {
        window.location.href = 'http://localhost:8080/api/oauth2/facebook';
    };

    const redirectToKakao = () => {
        window.location.href = 'http://localhost:8080/api/oauth2/kakao';
    };

    return (
        <div style={{ maxWidth: '400px', margin: '0 auto', padding: '20px', textAlign: 'center' }}>
            <h2>소셜 로그인</h2>
            <p>아래 버튼을 클릭하여 소셜 로그인을 진행하세요.</p>

            <button
                onClick={redirectToGoogle}
                style={{
                    padding: '10px 20px',
                    backgroundColor: '#4285F4',
                    color: '#fff',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    fontSize: '16px',
                    margin: '10px 0',
                    width: '100%',
                }}
            >
                Google로 로그인
            </button>

            <button
                onClick={redirectToFacebook}
                style={{
                    padding: '10px 20px',
                    backgroundColor: '#4267B2',
                    color: '#fff',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    fontSize: '16px',
                    margin: '10px 0',
                    width: '100%',
                }}
            >
                Facebook으로 로그인
            </button>

            <button
                onClick={redirectToKakao}
                style={{
                    padding: '10px 20px',
                    backgroundColor: '#FEE500',
                    color: '#000',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    fontSize: '16px',
                    margin: '10px 0',
                    width: '100%',
                }}
            >
                Kakao로 로그인
            </button>
        </div>
    );
};

export default OAuthPage;
