# Vinyl Lending Library System

A client-server application for managing a vinyl lending library system. This project is built using Java, JavaFX, and follows the MVVM architecture pattern.

## Features

- Multi-client support using Sockets
- Real-time updates using Observer pattern
- JSON-based communication protocol
- Comprehensive logging system using Multiton pattern
- MVVM architecture with multiple views

## Requirements

- Java 17 or higher
- Maven 3.6 or higher
- JavaFX 17

## Project Structure

```
vinyl-lending-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── client/           # Client-side code
│   │   │   │   ├── model/        # Client model classes
│   │   │   │   ├── viewmodel/    # MVVM ViewModels
│   │   │   │   ├── view/         # JavaFX views
│   │   │   │   └── network/      # Client network handling
│   │   │   ├── server/           # Server-side code
│   │   │   │   ├── model/        # Server model classes
│   │   │   │   └── network/      # Server network handling
│   │   │   ├── shared/           # Shared classes
│   │   │   └── util/             # Utility classes
│   │   └── resources/
│   │       ├── fxml/             # FXML view files
│   │       └── styles/           # CSS files
│   └── test/                     # Test classes
├── logs/                         # Server logs directory
├── pom.xml                       # Maven configuration
└── README.md                     # This file
```

## Setup and Running

1. Clone the repository
2. Build the project:

   ```bash
   mvn clean install
   ```

3. Run the server:

   ```bash
   mvn exec:java -Dexec.mainClass="com.vinyl.server.RunServer"
   ```

4. Run the client:
   ```bash
   mvn exec:java -Dexec.mainClass="com.vinyl.client.RunClient"
   ```

## Communication Protocol

All messages between client and server use JSON format. Example messages:

```json
// Request to borrow a vinyl
{
    "type": "borrowVinyl",
    "content": {
        "vinylId": "123",
        "userId": "456"
    }
}

// Server response
{
    "type": "response",
    "status": "success",
    "message": "Vinyl borrowed successfully"
}
```

## Logging

The system uses a Multiton pattern for logging, creating separate log files for each day. Logs include:

- Timestamp
- IP address
- Message content
- Action type

Log files are stored in the `logs` directory with the format: `server_YYYY_MM_DD.log`

## Design Patterns

- **MVVM**: Separates the UI from business logic
- **Observer**: Used for real-time updates between server and clients
- **Multiton**: Implemented in the logging system
- **Factory**: Used for creating network messages

## Class Diagram

The class diagram (created in Astah) can be found in the `docs` directory.
