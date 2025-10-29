use gamestore;

INSERT INTO user (fullname, account, password, email, role) VALUES
('Nguyen Van A', 'vana', 'pass123', 'vana@email.com', 'customer'),
('Tran Thi B', 'thib', 'pass456', 'thib@email.com', 'customer'),
('Le Van C', 'vanc', 'pass789', 'vanc@email.com', 'admin'),
('Pham Thi D', 'thid', 'pass101', 'thid@email.com', 'customer'),
('Hoang Van E', 'vane', 'pass112', 'vane@email.com', 'customer');

INSERT INTO game (gameName, poster, genre, gameDescription, platform, publisher, releaseDate, stockQuantity, price) VALUES
('Elden Ring', 'poster_elden_ring.jpg', 'Action RPG', 'An action RPG from FromSoftware.', 'PC', 'FromSoftware', '2022-02-25', 100, 59.99),
('Baldur\'s Gate 3', 'poster_bg3.jpg', 'RPG', 'A story-rich, party-based RPG set in the Dungeons & Dragons universe.', 'PC', 'Larian Studios', '2023-08-03', 150, 59.99),
('Starcraft II', 'poster_sc2.jpg', 'RTS', 'A real-time strategy game about interstellar warfare.', 'PC', 'Blizzard', '2010-07-27', 50, 19.99),
('The Witcher 3: Wild Hunt', 'poster_witcher3.jpg', 'Action RPG', 'Play as a monster slayer for hire, Geralt of Rivia.', 'PlayStation', 'CD Projekt Red', '2015-05-19', 80, 39.99),
('Cyberpunk 2077', 'poster_cp2077.jpg', 'Action RPG', 'An open-world, action-adventure story set in Night City.', 'Xbox', 'CD Projekt Red', '2020-12-10', 120, 49.99);

INSERT INTO gamekey (keyValue, status, gameID) VALUES
('ER-ABCD-1234-EFGH', 'available', 1),
('BG3-IJKL-5678-MNOP', 'sold', 2),
('SC2-QRST-9101-UVWX', 'available', 3),
('W3-YZAB-1121-CDEF', 'available', 4),
('CP77-GHIJ-3141-KLMN', 'sold', 5);

INSERT INTO review (reviewContent, reviewDate, userID, gameID) VALUES
('Game hay tuyệt vời!', '2023-09-10', 1, 1),
('Cốt truyện rất sâu sắc.', '2023-09-12', 2, 2),
('Game chiến thuật đỉnh cao.', '2023-09-15', 1, 3),
('Đồ họa đẹp và thế giới rộng lớn.', '2023-09-18', 4, 4),
('Một trải nghiệm không thể quên.', '2023-09-20', 5, 2);

INSERT INTO cart (updateDate, userID) VALUES
('2025-10-16', 1),
('2025-10-15', 2),
('2025-10-17', 3),
('2025-10-14', 4),
('2025-10-13', 5);

INSERT INTO cartdetail (cartID, gameID, priceAtPurchase, quantity) VALUES
(1, 1, 59.99, 1),
(1, 3, 19.99, 1),
(2, 2, 59.99, 1),
(3, 4, 39.99, 1),
(4, 5, 49.99, 2);

INSERT INTO orders (totalAmount, orderDate, deliveryDate, state, userID) VALUES
(79.98, '2025-10-10', '2025-10-10', 'completed', 1),
(59.99, '2025-10-11', '2025-10-11', 'completed', 2),
(39.99, '2025-10-12', '2025-10-12', 'pending', 4),
(49.99, '2025-10-13', '2025-10-13', 'completed', 5),
(19.99, '2025-10-14', '2025-10-14', 'cancelled', 1);

-- Lưu ý: Bạn cần bỏ comment và thêm cột primary key (orderID, gameID) cho bảng này
-- ALTER TABLE orderdetail ADD PRIMARY KEY(orderID, gameID);

INSERT INTO orderdetail (orderID, gameID, priceAtPurchase, quantity) VALUES
(1, 1, 59.99, 1),
(1, 3, 19.99, 1),
(2, 2, 59.99, 1),
(3, 4, 39.99, 1),
(4, 5, 49.99, 1);