use `retailplaygame`;

INSERT INTO carts (username)
VALUES ('customer'); -- cart id 1
INSERT INTO carts (username)
VALUES ('owner');	-- cart id 2

INSERT INTO cart_items (cartid, productid, quantity)
VALUES
(1, 5, 1),  -- 1 item of "The Legend of Zelda: Breath of the Wild" (product_id 5)
(2, 6, 2),  -- 2 items of "God of War: Ragnarok" (product_id 6)
(2, 7, 1);  -- 1 item of "Halo Infinite" (product_id 7)