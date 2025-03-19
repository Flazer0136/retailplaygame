use `retailplaygame`;

INSERT INTO coupons (coupon_code, discount_type, discount, expiry_date, usage_limit)
VALUES ('DEFAULT', 'PERCENTAGE', 0, '9999-12-31', 999999);
INSERT INTO coupons (coupon_code, discount_type, discount, expiry_date, usage_limit)
VALUES ('WINTER25', 'PERCENTAGE', 20, '2025-12-31', 5);
INSERT INTO coupons (coupon_code, discount_type, discount, expiry_date, usage_limit)
VALUES ('SUMMER25', 'PERCENTAGE', 10, '2025-07-30', 100);
