# PuzzleSolving-Game
Project Description (Mystic Maze)
Mystic Maze is a 2D team-based multiplayer puzzle game developed using JavaFX
and MySQL. The game is designed for 2–4 players, where each player must solve
their own unique puzzle while cooperating with teammates to complete each level. The
game encourages speed, strategy, and teamwork, offering 10–12 progressively
difficult levels filled with riddles, hunts, and hidden clues.
The game includes real-time interaction features, rewards, hint sharing, and a
dynamic leaderboard that promotes healthy competition and collaboration.
Key Features
Multiplayer Collaboration
● Supports 2 to 4 players in a team.
○ Each player must create an account
○ After creating an account the player could invite others to play together or
can join with others by using a code which will be generated while creating
a room.
● Each player gets a unique puzzle in each level.
○ There will be some puzzles/riddles in each level which will be assigned to
the player.
● The first player to solve a puzzle gets special powers
○ Whoever solves a puzzle first can help others by giving hints or taking
over.
○ he/she can see the other progress
● Database Driven Progress Tracking
○ To visible one progress to others
Puzzle Diversity
● Each level features different types of challenges:
■ Logic puzzles
■ Visual riddles
■ Pattern matching etc.
Rewards & Powers
● First solver of each level earns:
○ Ability to send hints
○ Share power-ups
○ Reveal parts of other puzzles
○ Freeze the timer for a certain amount of time.
Level-Based Difficulty
● Game includes 10–12 levels (tentative), increasing in difficulty and
complexity.
● Final level requires team coordination to unlock a combined exit gate.
Leaderboard
● Tracks top teams based on:
○ Completion time
○ Number of hints used
○ Accuracy (fewer mistakes = bonus)
2D Game Interface
● Built using JavaFX
● Interactive visuals with animations
● Puzzle UI per player with a sidebar for team updates
Implementation Plan
Tech Stack
Component Tools
UI and Game Engine JavaFX and Scene builder
Backend Java
Database MySQL
Multiplayer Sync/Communication Java Sockets
Team Collaboration and Project
Management
GitHub
Module Breakdown
a) User Authentication & Room System
● Users register and create/join a room.
● Each room holds 2–4 players with unique IDs.
b) Puzzle Engine
● Each level loads 2–4 different puzzles per session.
● Puzzle logic handled through JavaFX components.
c) Multiplayer Sync
● Implement Java Sockets for real-time help and coordination.
d) Reward System
● When a player completes a puzzle:
○ Options: send hint, power boost, unlock door
e) Leaderboard System
● Store results in DB:
○ Time taken
○ Hints used
○ Total score
● Display top teams with filters (weekly/all-time)
N.B. :- This is a tentative description for our project based on our current vision about
the game. During the implementation phase, certain aspects may change depending on
technical feasibility, or gameplay improvements. We remain flexible and open to
discuss with our respected course teacher and to refine the plan to ensure the best
outcome for the final product.
