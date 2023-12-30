INSERT INTO "USER_ENTITY" (id, username, full_name, password, email, validated, role, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED, ENABLED) VALUES ('3f0190ac-ebef-4fc2-99c9-5d44016da63a', 'cristian1', 'Cristian Garcia', '{bcrypt}$2a$10$BW8AgWp5mtCOKsXObq.EAuBuM6IJM0YPZVokYGtau/IRxyL8oK36S', 'cristian@example.com', false, 'USER', true, true, true, true);
INSERT INTO "USER_ENTITY" (id, username, full_name, password, email, validated, role, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED, ENABLED) VALUES ('8b6932a7-aeed-4b4c-bc95-98b9a6817851', 'paco2', 'Paco Martinez', '{bcrypt}$2a$10$BW8AgWp5mtCOKsXObq.EAuBuM6IJM0YPZVokYGtau/IRxyL8oK36S', 'paco@example.com', false, 'USER', true, true, true, true);
INSERT INTO "USER_ENTITY" (id, username, full_name, password, email, validated, role, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED, ENABLED) VALUES ('70a150a3-0a1f-48b3-9fc7-67fba2885c01', 'luismi3', 'Luismi Gonzalez', '{bcrypt}$2a$10$BW8AgWp5mtCOKsXObq.EAuBuM6IJM0YPZVokYGtau/IRxyL8oK36S', 'luismi@example.com', false, 'USER', true, true, true, true);
INSERT INTO "USER_ENTITY" (id, username, full_name, password, email, validated, role, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED, ENABLED) VALUES ('9a98b20f-8f24-4647-af45-45b0bfe63a11', 'lucia4', 'Lucia Sanchez', '{bcrypt}$2a$10$BW8AgWp5mtCOKsXObq.EAuBuM6IJM0YPZVokYGtau/IRxyL8oK36S', 'lucia@example.com', false, 'USER', true, true, true, true);
INSERT INTO "USER_ENTITY" (id, username, full_name, password, email, validated, role, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED, ENABLED) VALUES ('c7d3d0ac-3e68-4cd6-8233-df25d5ef95f6', 'manuel5', 'Manuel Perez', '{bcrypt}$2a$10$BW8AgWp5mtCOKsXObq.EAuBuM6IJM0YPZVokYGtau/IRxyL8oK36S', 'manuel@example.com', false, 'USER', true, true, true, true);
INSERT INTO "USER_ENTITY" (id, username, full_name, password, email, validated, role, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED, ENABLED) VALUES ('f5a2edc4-06d6-4f2d-a9c0-fb72c4a07c7c', 'antonio6', 'Antonio Hernandez', '{bcrypt}$2a$10$BW8AgWp5mtCOKsXObq.EAuBuM6IJM0YPZVokYGtau/IRxyL8oK36S', 'antonio@example.com', false, 'USER', true, true, true, true);
--Users Account
INSERT INTO "USER_WORKER" (id,jefe) VALUES ('3f0190ac-ebef-4fc2-99c9-5d44016da63a',false);
INSERT INTO "USER_WORKER" (id,jefe) VALUES ('8b6932a7-aeed-4b4c-bc95-98b9a6817851',false);
INSERT INTO "USER_WORKER" (id,jefe) VALUES ('70a150a3-0a1f-48b3-9fc7-67fba2885c01',false);
INSERT INTO "USER_WORKER" (id,jefe) VALUES ('9a98b20f-8f24-4647-af45-45b0bfe63a11',false);
INSERT INTO "USER_WORKER" (id,jefe) VALUES ('c7d3d0ac-3e68-4cd6-8233-df25d5ef95f6',false);
INSERT INTO "USER_WORKER" (id,jefe) VALUES ('f5a2edc4-06d6-4f2d-a9c0-fb72c4a07c7c',false);

--Admin Account
INSERT INTO "USER_ENTITY" (id, username, full_name, password, email, validated, role, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED, ENABLED) VALUES ('839e2b39-361e-4cc1-866f-f52bd9d812c3', 'admin1', 'El Administrador', '{bcrypt}$2a$10$O9gBlJqfCKUMVkDYxYs/puwiXpuVfWTMTBjql6x07T8bl8yXXBSi.', 'admin@gmail.com', true, 'ADMIN', true, true, true, true);
INSERT INTO "USER_WORKER" (id,jefe) VALUES ('839e2b39-361e-4cc1-866f-f52bd9d812c3',true);
