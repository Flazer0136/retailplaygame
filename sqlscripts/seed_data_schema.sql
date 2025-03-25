use `retailplaygame`;

-- Insert sample users
INSERT INTO users (username, password, enabled, email) VALUES
('customer', '$2a$10$/TymWt5S1OUWbR.VNk/s5eUjp54CktnpNJ6D6L5Z7dIQ6AVQymngm', 1), -- password is "test123"
('owner', '$2a$10$/TymWt5S1OUWbR.VNk/s5eUjp54CktnpNJ6D6L5Z7dIQ6AVQymngm', 1), -- password is "test123"
('admin', '$2a$10$/TymWt5S1OUWbR.VNk/s5eUjp54CktnpNJ6D6L5Z7dIQ6AVQymngm', 1); -- password is "test123"

-- Insert sample products (video games)
INSERT INTO products (product_name, quantity, price, info, console, genre) VALUES
('The Legend of Zelda: Breath of the Wild', 10, 59.99, 'An open-world action-adventure game.', 'Nintendo Switch', 'Adventure'),
('God of War: Ragnarok', 8, 69.99, 'An action-packed Norse mythology game.', 'PlayStation', 'Action'),
('Halo Infinite', 12, 49.99, 'A first-person shooter by 343 Industries.', 'Xbox', 'Shooter'),
('Elden Ring', 15, 59.99, 'A challenging open-world RPG by FromSoftware.', 'PlayStation', 'RPG'),
('Super Mario Odyssey', 20, 49.99, 'A 3D platformer featuring Mario.', 'Nintendo Switch', 'Platformer'),
('Forza Horizon 5', 14, 54.99, 'An open-world racing game.', 'Xbox', 'Racing'),
('Cyberpunk 2077', 7, 39.99, 'A futuristic open-world RPG by CD Projekt Red.', 'PlayStation', 'RPG'),
('Resident Evil Village', 9, 44.99, 'A survival horror game by Capcom.', 'PlayStation', 'Horror'),
('Horizon Forbidden West', 6, 69.99, 'An action RPG with stunning visuals.', 'PlayStation', 'RPG'),
('Metroid Dread', 10, 49.99, 'A 2D action-platformer with a sci-fi theme.', 'Nintendo Switch', 'Action'),
('Mortal Kombat 11', 11, 29.99, 'A brutal fighting game.', 'PlayStation', 'Fighting'),
('The Witcher 3: Wild Hunt', 18, 29.99, 'An open-world RPG masterpiece.', 'Xbox', 'RPG'),
('Grand Theft Auto V', 25, 19.99, 'A crime open-world game by Rockstar.', 'PlayStation', 'Action'),
('Splatoon 3', 13, 59.99, 'A fast-paced ink shooter.', 'Nintendo Switch', 'Shooter'),
('Call of Duty: Modern Warfare II', 9, 69.99, 'A realistic military shooter.', 'PlayStation', 'Shooter');

-- Insert sample coupons
INSERT INTO coupons (coupon_code, discount_type, discount, expiry_date, usage_limit) VALUES
('DEFAULT', 'PERCENTAGE', 0, '9999-12-31', 999999),
('WINTER25', 'PERCENTAGE', 20, '2025-12-31', 5),
('SUMMER25', 'PERCENTAGE', 10, '2025-07-30', 100);

-- Create sample carts
INSERT INTO carts (userid, coupon_id) VALUES
(1, 1),  -- customer with DEFAULT coupon
(2, NULL); -- owner with no coupon

-- Add items to carts
INSERT INTO cart_items (quantity, cartid, productid) VALUES
(1, 1, 1),  -- customer has Zelda
(2, 1, 3),  -- customer has 2x Halo
(1, 2, 2),  -- owner has GoW
(1, 2, 5);  -- owner has Mario

-- Insert sample authorities
INSERT INTO authorities (userID, authority) VALUES
(1, 'ROLE_CUSTOMER'),
(2, 'ROLE_CUSTOMER'),
(2, 'ROLE_OWNER'),
(3, 'ROLE_CUSTOMER'),
(3, 'ROLE_OWNER'),
(3, 'ROLE_ADMIN');