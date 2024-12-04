# Zoom Integration Application  

This project is a full-stack application designed to integrate with the Zoom API, with a primary focus on implementing OAuth 2.0 authentication for secure third-party access to user Zoom data. The backend is built with Spring Boot, and the frontend is developed using React. The application enables users to authenticate with Zoom, retrieve meeting details, and display the information in a structured tabular format.

---

## Features  
- OAuth-based Zoom integration for secure user authentication.  
- Display a list of scheduled Zoom meetings.  
- Details include Meeting ID, Topic, Start Time, Duration, and Join URL in a tabular format.  

---

## Prerequisites  
Before running the application, ensure you have the following installed:  
- **Java 17 or later**  
- **Node.js (v14 or later)** and **npm**  
- **Spring Boot**  
- **Zoom Developer Account**  
- **.env Configuration for Zoom API**

---

## Backend Setup (Spring Boot)
1. Clone the repository:
    ```bash
    git clone https://github.com/rohitkarhadkar/Zoom-Integration-App.git
    cd zoom-integration/demo
    ```

2. Create a `.env` file in the backend folder with the following content:
    ```plaintext
    ZOOM_CLIENT_ID=your-client-id
    ZOOM_CLIENT_SECRET=your-client-secret
    ```

3. Update the `application.properties` file to use environment variables:
    ```properties
    spring.application.name=Zoom Integration App
    zoom.client-id=${ZOOM_CLIENT_ID}
    zoom.client-secret=${ZOOM_CLIENT_SECRET}
    zoom.redirect-uri=http://localhost:8080/zoom/callback
    zoom.base-url=https://zoom.us
    ```

4. Build and run the backend:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```

   The backend server will start at [http://localhost:8080](http://localhost:8080).

---

## Frontend Setup (React)
1. Navigate to the frontend directory:
    ```bash
    cd zoom-integration/frontend
    ```

2. Install dependencies:
    ```bash
    npm install
    ```

3. Start the frontend development server:
    ```bash
    npm start
    ```

   The React app will run at [http://localhost:3000](http://localhost:3000).

---

## Running the Application
1. Start the backend server:
    ```bash
    cd zoom-integration/demo
    ./mvnw spring-boot:run
    ```

2. Start the frontend server:
    ```bash
    cd zoom-integration/frontend
    npm start
    ```

3. Open your browser and navigate to [http://localhost:3000](http://localhost:3000).

---

## API Endpoints  
### Backend (Spring Boot)
- **OAuth Authorization:** `/zoom/oauth`
- **Callback Endpoint:** `/zoom/callback`
- **Fetch Meetings:** `/zoom/meetings`

---

## Built With  
- **Frontend:** React, Axios  
- **Backend:** Spring Boot, Java, Maven  
- **API:** Zoom REST API

---

## Contributing  
Feel free to submit issues or pull requests to improve the project.

---

## Author  
**Rohit Karhadkar**  
Email: rohitkarhadkar.rk@gmail.com
