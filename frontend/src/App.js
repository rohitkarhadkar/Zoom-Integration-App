import logo from './logo.svg';
import './App.css';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
    const [isAuthorized, setIsAuthorized] = useState(false);
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [meetings, setMeetings] = useState([]);

    // Check for query parameter success in URL
    useEffect(() => {
        const queryParams = new URLSearchParams(window.location.search);
        const success = queryParams.get('success');
        if (success === 'true') {
            fetchMeetings();
        } else if (success === 'false') {
            setIsAuthorized(false);
            setErrorMessage("Zoom connection failed. Please try again.");
            window.location.href = "http://localhost:3000";
        }
    }, []);

    // Connect with Zoom - starts OAuth flow
    const connectZoom = () => {
        window.location.href = "http://localhost:8080/zoom/connect";
    };

    // Fetch meetings from Zoom API
    const fetchMeetings = async () => {
        setLoading(true);
        setErrorMessage('');
        setSuccessMessage('');
        setMeetings([]);

        try {
            const response = await axios.get("http://localhost:8080/zoom/meetings");

            if (response.status === 401) {
                window.location.href = "http://localhost:3000";
                return;
            }

            const meetingsData = response.data;

            // Log meetings data to the console
            console.log("Fetched Meetings Data:", meetingsData);
            setIsAuthorized(true);
            setMeetings(meetingsData);
            setSuccessMessage("Meeting data collected!");
        } catch (err) {
            setIsAuthorized(false);
            setErrorMessage("Failed to fetch meetings.");
            window.location.href = "http://localhost:3000";
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="App">
            <header className="App-header">
                <h1>Zoom Integration</h1>

                {!isAuthorized && <button onClick={connectZoom}>Connect with Zoom</button>}

                {isAuthorized && !loading && !errorMessage && <p>Zoom connection successful!</p>}
                {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}

                {/* Fetch meetings button */}
                {isAuthorized && (
                    <button onClick={fetchMeetings}>
                        Fetch Meetings
                    </button>
                )}

                {/* Show loading state */}
                {loading && <p>Loading...</p>}

                {/* Show success or error message for fetchMeetings */}
                {successMessage && <p>{successMessage}</p>}
                {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}

                {/* Display meetings if available */}
                {meetings.length > 0 && (
                    <div>
                        <h3>Meetings:</h3>
                        <table border="1" style={{ width: '80%', margin: '0 auto', borderCollapse: 'collapse' }}>
                            <thead>
                                <tr>
                                    <th style={{ padding: '10px' }}>Meeting ID</th>
                                    <th style={{ padding: '10px' }}>Topic</th>
                                    <th style={{ padding: '10px' }}>Start Time</th>
                                    <th style={{ padding: '10px' }}>Duration (minutes)</th>
                                    <th style={{ padding: '10px' }}>Join URL</th>
                                </tr>
                            </thead>
                            <tbody>
                                {meetings.map((meeting, index) => (
                                    <tr key={index}>
                                        <td style={{ padding: '10px', textAlign: 'center' }}>{meeting.id}</td>
                                        <td style={{ padding: '10px', textAlign: 'center' }}>{meeting.topic}</td>
                                        <td style={{ padding: '10px', textAlign: 'center' }}>
                                            {new Date(meeting.start_time).toLocaleString()}
                                        </td>
                                        <td style={{ padding: '10px', textAlign: 'center' }}>{meeting.duration}</td>
                                        <td style={{ padding: '10px', textAlign: 'center' }}>
                                            <a href={meeting.join_url} target="_blank" rel="noopener noreferrer">
                                                Join Link
                                            </a>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </header>
        </div>
    );
}

export default App;