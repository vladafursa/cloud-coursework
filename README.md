
# Cloud Coursework – Restful Dormitory Website

---

## Overview
International students often struggle to understand where their accommodation is located, how far it is from the city campus, and whether the area is safe.  
To have this information in one place, I built a **Restful Dormitory Website**.

---

##  Architecture & Tech Stack
- **Frontend Client:** React.js  
- **Backend Orchestrator:** Java (javax.ws.rs)  
- **Communication:** JSON over RESTful APIs with required serialization and deserialization  
- **External APIs Integrated:**  
  - 7timer (weather)  
  - UK Police (crime statistics)  
  - Distance calculator (travel distance/time)  
- **Containerisation & Deployment:** Docker for orchestrator, deployed to Azure Cloud  
- **Testing:** JMeter for performance and load testing  

---

##  Strengths & Features
- **Responsive GUI** – built with React.js for accessibility and usability.  
-  **Loosely Coupled Architecture** – microservices design for scalability and fault tolerance.  
-  **User Authentication** – secure login.  
-  **Error Handling** – robust handling of failures to ensure smooth user experience.  
-  **Caching Strategy:**  
   - Weather data updated **daily**.  
   - Crime data updated **monthly**.  
   - System makes every effort to handle and overcome external service downtime without the user noticing.  
    - Deletes outdated weather data and shows remaining cached data until updates finish.  
-  **Resilient External API Calls:**  
   - Timeout handling with retry mechanism (3 attempts, 500ms sleep).  
   - Ensures reliable retrieval of data from external services.  
-  **Dockerised Orchestrator:**  
   - Dockerfile created for orchestrator.  
   - Can be built and started easily.  
- ️ **Cloud Deployment:**  
   - Orchestrator deployed to Azure for scalability and fault tolerance.  


## Impact
- Delivered a **functional prototype** addressing a real need for international students.  
- Demonstrated **cloud-native deployment skills** with Docker + Azure.  
- Built a resilient system capable of handling external API downtime gracefully.  
---

## Demo-video

https://drive.google.com/file/d/1ipy0VjC6-9Biin0qVCNM3JXpZmkWFiJe/view?usp=share_link
