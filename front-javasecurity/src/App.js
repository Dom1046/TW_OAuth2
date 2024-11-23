import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import OAuthPage from './components/OAuthPage';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/oauth" element={<OAuthPage />} />
            </Routes>
        </Router>
    );
}

export default App;
