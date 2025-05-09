-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 09, 2025 at 03:52 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mysticmaze`
--

-- --------------------------------------------------------

--
-- Table structure for table `game_sessions`
--

CREATE TABLE `game_sessions` (
  `session_id` int(11) NOT NULL,
  `room_id` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` enum('in_progress','completed','cencelled','paused') DEFAULT 'in_progress'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `leaderboard`
--

CREATE TABLE `leaderboard` (
  `session_id` int(11) NOT NULL,
  `room_id` int(11) DEFAULT NULL,
  `total_time` int(11) DEFAULT NULL,
  `total_hints_used` int(11) DEFAULT NULL,
  `accuracy_score` decimal(5,2) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `player_progress`
--

CREATE TABLE `player_progress` (
  `session_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `puzzle_id` int(11) NOT NULL,
  `level` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` enum('pending','solved','skipped') DEFAULT 'pending',
  `is_first_solver` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `puzzles`
--

CREATE TABLE `puzzles` (
  `puzzle_id` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `type` enum('logic','visual','pattern','text','riddle') NOT NULL,
  `puzzle_data` text NOT NULL,
  `solution` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `puzzle_hints`
--

CREATE TABLE `puzzle_hints` (
  `hint_id` int(11) NOT NULL,
  `puzzle_id` int(11) NOT NULL,
  `hint_text` text NOT NULL,
  `level` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rewards`
--

CREATE TABLE `rewards` (
  `session_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `puzzle_id` int(11) NOT NULL,
  `can_send_hint` tinyint(1) DEFAULT 0,
  `shared_power_ups` tinyint(1) DEFAULT 0,
  `revealed_other` tinyint(1) DEFAULT 0,
  `freeze_timer` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `room_id` int(11) NOT NULL,
  `room_code` varchar(10) NOT NULL,
  `host_user_id` int(11) DEFAULT NULL,
  `status` enum('waiting','active','completed') DEFAULT 'waiting',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `room_members`
--

CREATE TABLE `room_members` (
  `room_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `joined_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `email`, `password_hash`, `created_at`) VALUES
(4, 'monser', 'monser@gamil.com', '9b68dd9857f84ecdb9dda92e3e39fa06172035d0c5ef01f9eab2da10fe096c80', '2025-05-08 20:15:39'),
(5, 'afia', 'afia@gmail.com', '8828095a933c30f9fabf6d60b2b548faa1b490f153e94a1b9a5f05535eef2534', '2025-05-08 20:37:27');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `game_sessions`
--
ALTER TABLE `game_sessions`
  ADD PRIMARY KEY (`session_id`),
  ADD KEY `room_id` (`room_id`);

--
-- Indexes for table `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD PRIMARY KEY (`session_id`),
  ADD KEY `room_id` (`room_id`);

--
-- Indexes for table `player_progress`
--
ALTER TABLE `player_progress`
  ADD PRIMARY KEY (`session_id`,`user_id`,`puzzle_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `puzzle_id` (`puzzle_id`);

--
-- Indexes for table `puzzles`
--
ALTER TABLE `puzzles`
  ADD PRIMARY KEY (`puzzle_id`);

--
-- Indexes for table `puzzle_hints`
--
ALTER TABLE `puzzle_hints`
  ADD PRIMARY KEY (`hint_id`),
  ADD KEY `puzzle_id` (`puzzle_id`);

--
-- Indexes for table `rewards`
--
ALTER TABLE `rewards`
  ADD PRIMARY KEY (`session_id`,`user_id`,`puzzle_id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`room_id`),
  ADD UNIQUE KEY `room_code` (`room_code`),
  ADD KEY `host_user_id` (`host_user_id`);

--
-- Indexes for table `room_members`
--
ALTER TABLE `room_members`
  ADD PRIMARY KEY (`room_id`,`user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `game_sessions`
--
ALTER TABLE `game_sessions`
  MODIFY `session_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `puzzles`
--
ALTER TABLE `puzzles`
  MODIFY `puzzle_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `puzzle_hints`
--
ALTER TABLE `puzzle_hints`
  MODIFY `hint_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `room_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `game_sessions`
--
ALTER TABLE `game_sessions`
  ADD CONSTRAINT `game_sessions_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`);

--
-- Constraints for table `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD CONSTRAINT `leaderboard_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `game_sessions` (`session_id`),
  ADD CONSTRAINT `leaderboard_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`);

--
-- Constraints for table `player_progress`
--
ALTER TABLE `player_progress`
  ADD CONSTRAINT `player_progress_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `game_sessions` (`session_id`),
  ADD CONSTRAINT `player_progress_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `player_progress_ibfk_3` FOREIGN KEY (`puzzle_id`) REFERENCES `puzzles` (`puzzle_id`);

--
-- Constraints for table `puzzle_hints`
--
ALTER TABLE `puzzle_hints`
  ADD CONSTRAINT `puzzle_hints_ibfk_1` FOREIGN KEY (`puzzle_id`) REFERENCES `puzzles` (`puzzle_id`);

--
-- Constraints for table `rewards`
--
ALTER TABLE `rewards`
  ADD CONSTRAINT `rewards_ibfk_1` FOREIGN KEY (`session_id`,`user_id`,`puzzle_id`) REFERENCES `player_progress` (`session_id`, `user_id`, `puzzle_id`);

--
-- Constraints for table `rooms`
--
ALTER TABLE `rooms`
  ADD CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`host_user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `room_members`
--
ALTER TABLE `room_members`
  ADD CONSTRAINT `room_members_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`),
  ADD CONSTRAINT `room_members_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
