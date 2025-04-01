use `retailplaygame`;

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

-- In order to give different role access to your users:
-- add a user through the register page in the application
-- and then assign them a role through the SQL scipt below
-- Following roles can be added ("ROLE_CUSTOMER"), ("ROLE_OWNER"), ("ROLE_ADMIN")
-- check your userID first in the users table, of the user you want to assign a role to
-- you can assign multiple roles to a user, make sure their hierarchy is correct
-- (e.g. ROLE_ADMIN > ROLE_OWNER > ROLE_CUSTOMER)
-- so your roles should be like this:
-- (1, 'ROLE_CUSTOMER'),
-- (1, 'ROLE_ADMIN'),
-- make sure your authorityID for customer is lower than the owner or admin
-- below is the insert command template that can be used to add roles to users
-- authorityID is auto incremented so you don't need to worry about it
--INSERT INTO authorities (userID, authority) VALUES
