# 15 Puzzle Game Backend

This project is a backend service for the 15 Puzzle game, built with Spring Boot. It provides RESTful APIs for creating games, retrieving game states, making moves, and listing all games.

## API Endpoints

### 1. Create a New Game
- **Endpoint**: `POST /api/v1/games`
- **Description**: Creates a new 15 Puzzle game.
- **Response**: Game id.

### 2. Get Game by ID
- **Endpoint**: `GET /api/v1/games/{id}`
- **Description**: Retrieves the state of a specific game by its ID.
- **Response**: Current game state.

### 3. Make a Move
- **Endpoint**: `POST /api/v1/games/{id}`
- **Description**: Makes a move in the specified game.
- **Request Body**: Move direction.
- **Response**: Updated game state.

### 4. List All Games
- **Endpoint**: `GET /api/v1/games`
- **Description**: Retrieves a list of all user's games.
- **Response**: List of games.
